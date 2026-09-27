package com.probestack.forgestudio.design.security.oauth2;

import com.probestack.forgestudio.design.config.security.OAuth2SecurityProperties;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Provides the RSA key used to sign locally issued JWT access tokens.
 */
@Component
public class OAuth2KeyService {

    private static final Logger log = LoggerFactory.getLogger(OAuth2KeyService.class);

    private final OAuth2SecurityProperties properties;
    private RSAKey signingKey;

    public OAuth2KeyService(OAuth2SecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * Initializes the signing key from the configured strategy.
     */
    @PostConstruct
    public void initialize() {
        try {
            signingKey = switch (properties.getKeyStrategy()) {
                case GENERATED_ON_STARTUP -> generateStartupKey();
                case STATIC_CONFIG -> loadStaticConfigKey();
                case EXTERNAL -> throw new IllegalStateException(
                        "EXTERNAL key strategy is reserved for a later enterprise key source");
            };

            log.info("event=oauth2_key_initialized keyStrategy={} keyId={} securityMode={}",
                    properties.getKeyStrategy(), signingKey.getKeyID(), properties.getMode());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize OAuth2 signing key", e);
        }
    }

    public RSAKey getSigningKey() {
        return signingKey;
    }

    public RSAPublicKey getPublicKey() {
        try {
            return signingKey.toRSAPublicKey();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read OAuth2 public key", e);
        }
    }

    private RSAKey generateStartupKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        return toRsaKey(
                (RSAPublicKey) keyPair.getPublic(),
                (RSAPrivateKey) keyPair.getPrivate(),
                UUID.randomUUID().toString()
        );
    }

    private RSAKey loadStaticConfigKey() throws Exception {
        OAuth2SecurityProperties.Rsa rsa = properties.getRsa();
        if (!StringUtils.hasText(rsa.getPrivateKeyPem()) || !StringUtils.hasText(rsa.getPublicKeyPem())) {
            throw new IllegalStateException(
                    "STATIC_CONFIG requires app.security.oauth2.rsa.private-key-pem and public-key-pem");
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] privateBytes = decodePem(rsa.getPrivateKeyPem());
        byte[] publicBytes = decodePem(rsa.getPublicKeyPem());
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));

        String keyId = StringUtils.hasText(rsa.getKeyId()) ? rsa.getKeyId() : "static-config-key";
        return toRsaKey(publicKey, privateKey, keyId);
    }

    private RSAKey toRsaKey(RSAPublicKey publicKey, RSAPrivateKey privateKey, String keyId) {
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(keyId)
                .algorithm(JWSAlgorithm.RS256)
                .build();
    }

    private byte[] decodePem(String pem) {
        String normalized = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(normalized);
    }
}

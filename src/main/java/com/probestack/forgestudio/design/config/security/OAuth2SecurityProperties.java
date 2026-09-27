package com.probestack.forgestudio.design.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for generated OAuth2 and JWT security.
 */
@Validated
@ConfigurationProperties(prefix = "app.security.oauth2")
public class OAuth2SecurityProperties {

    private Mode mode = Mode.SELF_CONTAINED;
    private String issuerUri = "http://localhost:8080";
    private String audience = "generated-service";
    private String clientId = "generated-service-client";
    private String clientSecret = "change-me";
    private String defaultScopes = "api.read,api.write";
    private long tokenTtlSeconds = 3600;
    private KeyStrategy keyStrategy = KeyStrategy.GENERATED_ON_STARTUP;
    private final Rsa rsa = new Rsa();

    public enum Mode {
        SELF_CONTAINED,
        ENTERPRISE
    }

    public enum KeyStrategy {
        GENERATED_ON_STARTUP,
        STATIC_CONFIG,
        EXTERNAL
    }

    public boolean isSelfContainedMode() {
        return mode == Mode.SELF_CONTAINED;
    }

    public boolean isEnterpriseMode() {
        return mode == Mode.ENTERPRISE;
    }

    public List<String> getDefaultScopeList() {
        return Arrays.stream(defaultScopes.split("[,\\s]+"))
                .map(String::trim)
                .filter(scope -> !scope.isEmpty())
                .toList();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getIssuerUri() {
        return issuerUri;
    }

    public void setIssuerUri(String issuerUri) {
        this.issuerUri = issuerUri;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDefaultScopes() {
        return defaultScopes;
    }

    public void setDefaultScopes(String defaultScopes) {
        this.defaultScopes = defaultScopes;
    }

    public long getTokenTtlSeconds() {
        return tokenTtlSeconds;
    }

    public void setTokenTtlSeconds(long tokenTtlSeconds) {
        this.tokenTtlSeconds = tokenTtlSeconds;
    }

    public KeyStrategy getKeyStrategy() {
        return keyStrategy;
    }

    public void setKeyStrategy(KeyStrategy keyStrategy) {
        this.keyStrategy = keyStrategy;
    }

    public Rsa getRsa() {
        return rsa;
    }

    public static class Rsa {
        private String privateKeyPem;
        private String publicKeyPem;
        private String keyId = "generated-dev-key";

        public String getPrivateKeyPem() {
            return privateKeyPem;
        }

        public void setPrivateKeyPem(String privateKeyPem) {
            this.privateKeyPem = privateKeyPem;
        }

        public String getPublicKeyPem() {
            return publicKeyPem;
        }

        public void setPublicKeyPem(String publicKeyPem) {
            this.publicKeyPem = publicKeyPem;
        }

        public String getKeyId() {
            return keyId;
        }

        public void setKeyId(String keyId) {
            this.keyId = keyId;
        }
    }
}

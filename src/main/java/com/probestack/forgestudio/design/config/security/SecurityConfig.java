package com.probestack.forgestudio.design.config.security;

import com.probestack.forgestudio.design.security.oauth2.AudienceValidator;
import com.probestack.forgestudio.design.security.oauth2.OAuth2KeyService;

import java.time.Duration;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Security configuration for generated OAuth2 token issuing and JWT validation.
 */
@Configuration
@EnableWebSecurity
@org.springframework.boot.context.properties.EnableConfigurationProperties(OAuth2SecurityProperties.class)
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String[] PUBLIC_ENDPOINTS = {
            "/",
            "/error",
            "/actuator/health",
            "/actuator/health/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    /**
     * Configures token issuing endpoints in self-contained mode and denies them in enterprise mode.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            OAuth2SecurityProperties properties,
            ObjectMapper objectMapper
    ) throws Exception {
        if (!properties.isSelfContainedMode()) {
            return http
                    .securityMatcher("/oauth2/**", "/.well-known/**")
                    .csrf(AbstractHttpConfigurer::disable)
                    .exceptionHandling(exceptions -> exceptions
                            .authenticationEntryPoint(authenticationEntryPoint(objectMapper))
                            .accessDeniedHandler(accessDeniedHandler(objectMapper)))
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().denyAll())
                    .build();
        }

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        return http
                .securityMatcher(endpointsMatcher)
                .with(authorizationServerConfigurer, Customizer.withDefaults())
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint(objectMapper))
                        .accessDeniedHandler(accessDeniedHandler(objectMapper)))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .build();
    }

    /**
     * Configures generated APIs as JWT-protected resource-server endpoints.
     */
    @Bean
    SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            ObjectMapper objectMapper
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint(objectMapper))
                        .accessDeniedHandler(accessDeniedHandler(objectMapper)))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(authenticationEntryPoint(objectMapper))
                        .jwt(Customizer.withDefaults()))
                .build();
    }

    /**
     * Registers the default client used by the generated service token endpoint.
     */
    @Bean
    RegisteredClientRepository registeredClientRepository(
            OAuth2SecurityProperties properties,
            PasswordEncoder passwordEncoder
    ) {
        RegisteredClient.Builder builder = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(properties.getClientId())
                .clientSecret(passwordEncoder.encode(properties.getClientSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(properties.getTokenTtlSeconds()))
                        .build());

        properties.getDefaultScopeList().forEach(builder::scope);
        return new InMemoryRegisteredClientRepository(builder.build());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings(OAuth2SecurityProperties properties) {
        return AuthorizationServerSettings.builder()
                .issuer(properties.getIssuerUri())
                .build();
    }

    @Bean
    JWKSource<SecurityContext> jwkSource(OAuth2KeyService keyService) {
        return new ImmutableJWKSet<>(new JWKSet(keyService.getSigningKey()));
    }

    /**
     * Validates JWTs locally in self-contained mode and through issuer discovery in enterprise mode.
     */
    @Bean
    JwtDecoder jwtDecoder(OAuth2SecurityProperties properties, OAuth2KeyService keyService) {
        AudienceValidator audienceValidator = new AudienceValidator(properties.getAudience());

        if (properties.isEnterpriseMode()) {
            JwtDecoder delegate = JwtDecoders.fromIssuerLocation(properties.getIssuerUri());
            return token -> {
                try {
                    Jwt jwt = delegate.decode(token);
                    var result = audienceValidator.validate(jwt);
                    if (result.hasErrors()) {
                        throw new JwtValidationException("Invalid token audience", result.getErrors());
                    }
                    logTokenValidated(jwt, properties);
                    return jwt;
                } catch (RuntimeException exception) {
                    logTokenValidationFailed(exception, properties);
                    throw exception;
                }
            };
        }

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(keyService.getPublicKey()).build();
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer(properties.getIssuerUri()),
                audienceValidator
        ));
        return token -> {
            try {
                Jwt jwt = decoder.decode(token);
                logTokenValidated(jwt, properties);
                return jwt;
            } catch (RuntimeException exception) {
                logTokenValidationFailed(exception, properties);
                throw exception;
            }
        };
    }

    /**
     * Adds audience and client metadata to access tokens created by the local token endpoint.
     */
    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer(OAuth2SecurityProperties properties) {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().audience(java.util.List.of(properties.getAudience()));
                context.getClaims().claim("client_id", context.getRegisteredClient().getClientId());
                log.info("Security token issued. eventType=SECURITY_TOKEN_ISSUED, clientId={}, audience={}, issuer={}, expiresInSeconds={}, keyStrategy={}",
                        context.getRegisteredClient().getClientId(),
                        properties.getAudience(),
                        properties.getIssuerUri(),
                        properties.getTokenTtlSeconds(),
                        properties.getKeyStrategy());
            }
        };
    }

    /**
     * Returns a consistent JSON response when a protected API is called without a valid access token.
     */
    private AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return (request, response, exception) -> writeSecurityError(
                objectMapper,
                request,
                response,
                HttpServletResponse.SC_UNAUTHORIZED,
                "invalid_access_token",
                "Access Token is invalid",
                securitySystemMessage("Access Token is invalid", exception),
                exception
        );
    }

    /**
     * Returns a consistent JSON response when an authenticated token lacks permission.
     */
    private AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return (request, response, exception) -> writeSecurityError(
                objectMapper,
                request,
                response,
                HttpServletResponse.SC_FORBIDDEN,
                "access_denied",
                "Access is denied",
                securitySystemMessage("The authenticated principal is not allowed to access this resource", exception),
                exception
        );
    }

    private void writeSecurityError(
            ObjectMapper objectMapper,
            HttpServletRequest request,
            HttpServletResponse response,
            int status,
            String code,
            String userMessage,
            String systemMessage,
            Exception exception
    ) throws IOException {
        log.warn("Security request rejected. eventType=SECURITY_REQUEST_REJECTED, code={}, status={}, method={}, path={}, reason={}, exceptionType={}",
                code,
                status,
                request.getMethod(),
                request.getRequestURI(),
                safeFailureReason(exception),
                exceptionType(exception),
                exception);
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), Map.of(
                "code", code,
                "userMessage", userMessage,
                "systemMessage", systemMessage
        ));
    }

    private void logTokenValidated(Jwt jwt, OAuth2SecurityProperties properties) {
        log.info("Security token validated. eventType=SECURITY_TOKEN_VALIDATED, subject={}, issuer={}, audience={}, expiresAt={}, mode={}, keyStrategy={}",
                jwt.getSubject(),
                jwt.getIssuer(),
                jwt.getAudience(),
                jwt.getExpiresAt(),
                properties.getMode(),
                properties.getKeyStrategy());
    }

    private void logTokenValidationFailed(RuntimeException exception, OAuth2SecurityProperties properties) {
        log.warn("Security token validation failed. eventType=SECURITY_TOKEN_VALIDATION_FAILED, reason={}, exceptionType={}, mode={}, issuerUri={}, expectedAudience={}, keyStrategy={}",
                safeFailureReason(exception),
                exceptionType(exception),
                properties.getMode(),
                properties.getIssuerUri(),
                properties.getAudience(),
                properties.getKeyStrategy(),
                exception);
    }

    private String securitySystemMessage(String fallback, Exception exception) {
        String reason = safeFailureReason(exception);
        return reason == null || reason.isBlank() ? fallback : reason;
    }

    private String safeFailureReason(Exception exception) {
        if (exception == null || exception.getMessage() == null || exception.getMessage().isBlank()) {
            return "No exception detail available";
        }
        return exception.getMessage()
                .replaceAll("(?i)Bearer\\s+[A-Za-z0-9._~+/=-]+", "Bearer ***")
                .replaceAll("(?i)(access_token=)[^&\\s]+", "$1***")
                .replaceAll("(?i)(client_secret=)[^&\\s]+", "$1***");
    }

    private String exceptionType(Exception exception) {
        return exception == null ? "unknown" : exception.getClass().getName();
    }
}

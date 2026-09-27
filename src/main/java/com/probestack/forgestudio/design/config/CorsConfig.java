package com.probestack.forgestudio.design.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CR_SEC_007: CORS allow-list must be explicit, never a wildcard. Registered as a plain servlet
 * filter (not Spring Security's {@code .cors()}), so it applies the same way regardless of which
 * security type (if any) is selected, and never needs the security filter chain to opt in.
 *
 * <p>No origins are allowed until {@code CORS_ALLOWED_ORIGINS} is set for the deployment
 * (comma-separated, e.g. {@code https://app.example.com,https://admin.example.com}) — safer than
 * a default wildcard, which this rule explicitly forbids.
 */
@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(
            @Value("${cors.allowed-origins:}") String allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isBlank())
                .toList();
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(!origins.isEmpty());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>(new CorsFilter(source));
        registration.setOrder(0);
        return registration;
    }
}

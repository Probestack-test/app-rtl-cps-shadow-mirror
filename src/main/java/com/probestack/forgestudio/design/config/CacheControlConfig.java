package com.probestack.forgestudio.design.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * PERF_005: GET responses must declare a Cache-Control header. Dynamic API responses default to
 * {@code no-store} — the correct, safe default for JSON APIs where responses can change per
 * request; a handler that genuinely wants to allow caching can still overwrite this header itself.
 */
@Configuration
public class CacheControlConfig {

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> cacheControlFilter() {
        OncePerRequestFilter filter = new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(
                    HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {
                if ("GET".equalsIgnoreCase(request.getMethod()) && !response.containsHeader("Cache-Control")) {
                    response.setHeader("Cache-Control", "no-store");
                }
                chain.doFilter(request, response);
            }
        };
        FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(1);
        return registration;
    }
}

package com.probestack.forgestudio.design.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Logs HTTP request, response, and failure metadata for generated services.
 *
 * <p>This filter is intentionally configuration-driven. Keep it enabled for
 * platform observability, or turn off request, response, header, or body logging
 * independently through {@code app.logging.*} properties.</p>
 */
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);
    private static final String MDC_CORRELATION_ID = "correlationId";

    private final LoggingProperties properties;
    private final SensitiveDataMasker masker;
    private final ObjectMapper objectMapper;

    public HttpLoggingFilter(
            LoggingProperties properties,
            SensitiveDataMasker masker,
            ObjectMapper objectMapper
    ) {
        this.properties = properties;
        this.masker = masker;
        this.objectMapper = objectMapper;
    }

    /**
     * Wraps the request/response when body logging is enabled, writes configured
     * structured events, and always restores the response body back to the client.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (!properties.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        String correlationId = resolveCorrelationId(request);
        response.setHeader(properties.getCorrelationHeader(), correlationId);
        MDC.put(MDC_CORRELATION_ID, correlationId);

        long startedAt = System.nanoTime();
        HttpServletRequest requestToUse = wrapRequestIfNeeded(request);
        HttpServletResponse responseToUse = wrapResponseIfNeeded(response);

        try {
            filterChain.doFilter(requestToUse, responseToUse);

            if (properties.getRequest().isEnabled()) {
                writeEvent("HTTP_REQUEST", requestToUse, responseToUse, startedAt, null);
            }
            if (properties.getResponse().isEnabled()) {
                writeEvent("HTTP_RESPONSE", requestToUse, responseToUse, startedAt, null);
            }
        } catch (ServletException | IOException | RuntimeException exception) {
            writeEvent("HTTP_ERROR", requestToUse, responseToUse, startedAt, exception);
            throw exception;
        } finally {
            if (responseToUse instanceof ContentCachingResponseWrapper wrapper) {
                wrapper.copyBodyToResponse();
            }
            MDC.remove(MDC_CORRELATION_ID);
        }
    }

    /**
     * Creates and writes a structured log event. Provider-specific differences
     * are limited to field names so stdout remains the only transport.
     */
    private void writeEvent(
            String eventType,
            HttpServletRequest request,
            HttpServletResponse response,
            long startedAt,
            Exception exception
    ) {
        Map<String, Object> event = new LinkedHashMap<>();
        event.put("timestamp", Instant.now().toString());
        event.put(severityField(), exception == null ? "INFO" : "ERROR");
        event.put("eventType", eventType);
        event.put("provider", properties.getProvider().name());
        event.put("correlationId", MDC.get(MDC_CORRELATION_ID));
        event.put("method", request.getMethod());
        event.put("path", requestPath(request));
        event.put("status", response.getStatus());
        event.put("durationMs", elapsedMillis(startedAt));

        addOptionalRequestFields(event, request);
        addOptionalResponseFields(event, response);

        if (exception != null) {
            event.put("exceptionType", exception.getClass().getSimpleName());
            event.put("message", safe(exception.getMessage()));
        }

        if (properties.isStructured()) {
            log.info(toJson(event));
        } else {
            log.info(event.toString());
        }
    }

    /**
     * Adds request metadata based on configuration switches.
     */
    private void addOptionalRequestFields(Map<String, Object> event, HttpServletRequest request) {
        if (properties.getRequest().isQueryEnabled() && request.getQueryString() != null) {
            event.put("query", masker.maskQueryString(request.getQueryString()));
        }
        if (properties.getRequest().isClientIpEnabled()) {
            event.put("clientIp", clientIp(request));
        }
        if (properties.getRequest().isUserAgentEnabled()) {
            event.put("userAgent", safe(request.getHeader("User-Agent")));
        }
        if (properties.getHeaders().isEnabled()) {
            event.put("headers", requestHeaders(request));
        }
        if (properties.getRequest().isBodyEnabled() && request instanceof ContentCachingRequestWrapper wrapper) {
            event.put("requestBody", bodyAsText(wrapper.getContentAsByteArray(), request.getContentType()));
        }
    }

    /**
     * Adds response metadata and optional response body based on configuration.
     */
    private void addOptionalResponseFields(Map<String, Object> event, HttpServletResponse response) {
        if (properties.getResponse().isBodyEnabled() && response instanceof ContentCachingResponseWrapper wrapper) {
            event.put("responseBody", bodyAsText(wrapper.getContentAsByteArray(), response.getContentType()));
        }
    }

    /**
     * Resolves or creates a correlation ID for every request.
     */
    private String resolveCorrelationId(HttpServletRequest request) {
        String headerValue = request.getHeader(properties.getCorrelationHeader());
        if (headerValue == null || headerValue.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return safe(headerValue);
    }

    private HttpServletRequest wrapRequestIfNeeded(HttpServletRequest request) {
        if (properties.getRequest().isBodyEnabled() && !(request instanceof ContentCachingRequestWrapper)) {
            return new ContentCachingRequestWrapper(request);
        }
        return request;
    }

    private HttpServletResponse wrapResponseIfNeeded(HttpServletResponse response) {
        if (properties.getResponse().isBodyEnabled() && !(response instanceof ContentCachingResponseWrapper)) {
            return new ContentCachingResponseWrapper(response);
        }
        return response;
    }

    private String requestPath(HttpServletRequest request) {
        String contextPath = request.getContextPath() == null ? "" : request.getContextPath();
        return contextPath + request.getRequestURI();
    }

    private long elapsedMillis(long startedAt) {
        return (System.nanoTime() - startedAt) / 1_000_000L;
    }

    private String clientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        return forwardedFor == null || forwardedFor.isBlank()
                ? safe(request.getRemoteAddr())
                : safe(forwardedFor.split(",")[0].trim());
    }

    private Map<String, String> requestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return headers;
        }
        for (String headerName : Collections.list(headerNames)) {
            headers.put(headerName.toLowerCase(Locale.ROOT), masker.mask(headerName, request.getHeader(headerName)));
        }
        return headers;
    }

    private String bodyAsText(byte[] body, String contentType) {
        if (body == null || body.length == 0) {
            return "";
        }
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).contains("json")) {
            return "[skipped: non-json body]";
        }
        int maxBytes = Math.max(properties.getMaxBodySizeBytes(), 0);
        int length = Math.min(body.length, maxBytes);
        String raw = new String(body, 0, length, StandardCharsets.UTF_8);
        String masked = masker.maskJsonBody(raw);
        return body.length > maxBytes ? masked + "...[truncated]" : masked;
    }

    private String severityField() {
        return properties.getProvider() == LoggingProperties.Provider.GCP ? "severity" : "level";
    }

    private String toJson(Map<String, Object> event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            return event.toString();
        }
    }

    private String safe(String value) {
        if (value == null || value.isBlank()) {
            return "-";
        }
        return value.replaceAll("[\\r\\n\\t]", "_");
    }
}

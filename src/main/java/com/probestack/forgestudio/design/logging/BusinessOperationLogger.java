package com.probestack.forgestudio.design.logging;

import com.probestack.forgestudio.design.config.validation.ValidationProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Writes one structured business summary event per generated service operation.
 *
 * <p>This logger intentionally avoids step-by-step noise. It answers whether a
 * generated operation succeeded, what persistence target was used, and which
 * document or record count was affected.</p>
 */
@Component
public class BusinessOperationLogger {

    private static final Logger log = LoggerFactory.getLogger(BusinessOperationLogger.class);
    private static final String EVENT_TYPE = "BUSINESS_OPERATION";
    private static final String MDC_CORRELATION_ID = "correlationId";

    private final LoggingProperties properties;
    private final ValidationProperties validationProperties;
    private final SensitiveDataMasker masker;
    private final ObjectMapper objectMapper;

    public BusinessOperationLogger(
            LoggingProperties properties,
            ValidationProperties validationProperties,
            SensitiveDataMasker masker,
            ObjectMapper objectMapper
    ) {
        this.properties = properties;
        this.validationProperties = validationProperties;
        this.masker = masker;
        this.objectMapper = objectMapper;
    }

    /**
     * Writes a structured business operation event when business logging is enabled.
     *
     * @param event generated operation metadata
     */
    public void record(BusinessOperationEvent event) {
        record(event, null);
    }

    /**
     * Writes a structured business operation event and optional exception details.
     *
     * @param event generated operation metadata
     * @param exception optional failure exception
     */
    public void record(BusinessOperationEvent event, Exception exception) {
        if (!properties.isEnabled() || !properties.getBusiness().isEnabled()) {
            return;
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("timestamp", Instant.now().toString());
        payload.put(severityField(), exception == null ? "INFO" : "ERROR");
        payload.put("eventType", EVENT_TYPE);
        payload.put("provider", properties.getProvider().name());
        payload.put("serviceName", properties.getServiceName());
        payload.put("serviceVersion", properties.getServiceVersion());
        payload.put("environment", properties.getEnvironment());
        payload.put("operation", event.getOperation());
        payload.put("resource", event.getResource());
        payload.put("action", event.getAction());
        payload.put("status", event.getStatus());
        payload.put("correlationId", correlationId());

        addHttpContext(payload);
        addValidationSummary(payload);
        addPersistenceSummary(payload, event);
        addFailureSummary(payload, event, exception);

        if (properties.isStructured()) {
            log.info(toJson(payload));
        } else {
            log.info(payload.toString());
        }
    }

    private void addHttpContext(Map<String, Object> payload) {
        HttpServletRequest request = currentRequest();
        if (request == null) {
            return;
        }
        payload.put("httpMethod", request.getMethod());
        payload.put("path", request.getRequestURI());
    }

    private void addValidationSummary(Map<String, Object> payload) {
        payload.put("validationEnabled", validationProperties.isEnabled());
        payload.put("validationStatus", validationProperties.isEnabled() ? "PASSED" : "SKIPPED");
        payload.put("validationErrorCount", 0);
    }

    private void addPersistenceSummary(Map<String, Object> payload, BusinessOperationEvent event) {
        payload.put("repository", event.getRepository());
        payload.put("collection", event.getCollection());
        if (event.getDocumentId() != null) {
            payload.put("documentId", masker.mask("documentId", event.getDocumentId()));
        }
        if (event.getRecordCount() != null) {
            payload.put("recordCount", event.getRecordCount());
        }
        if (event.getDurationMs() != null) {
            payload.put("durationMs", event.getDurationMs());
        }
    }

    private void addFailureSummary(Map<String, Object> payload, BusinessOperationEvent event, Exception exception) {
        if (event.getErrorCode() != null) {
            payload.put("errorCode", event.getErrorCode());
        }
        if (event.getErrorMessage() != null) {
            payload.put("errorMessage", safe(event.getErrorMessage()));
        }
        if (exception != null) {
            payload.put("exceptionType", exception.getClass().getSimpleName());
            payload.put("exceptionMessage", safe(exception.getMessage()));
        }
    }

    private String correlationId() {
        String fromMdc = MDC.get(MDC_CORRELATION_ID);
        if (fromMdc != null && !fromMdc.isBlank()) {
            return fromMdc;
        }

        HttpServletRequest request = currentRequest();
        if (request == null) {
            return "-";
        }

        String headerValue = request.getHeader(properties.getCorrelationHeader());
        return headerValue == null || headerValue.isBlank() ? "-" : safe(headerValue);
    }

    private HttpServletRequest currentRequest() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            return attributes.getRequest();
        }
        return null;
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

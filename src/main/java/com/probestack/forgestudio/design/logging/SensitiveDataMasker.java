package com.probestack.forgestudio.design.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Masks sensitive values before they are written to logs.
 *
 * <p>The field list is controlled by {@code app.logging.masking.fields}. Keep
 * masking enabled in shared, cloud, and production environments. Disable it only
 * for tightly controlled local debugging.</p>
 */
@Component
public class SensitiveDataMasker {

    private static final String MASKED = "***";

    private final LoggingProperties properties;
    private final ObjectMapper objectMapper;

    public SensitiveDataMasker(LoggingProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    /**
     * Masks a single header, query parameter, or field value by name.
     */
    public String mask(String name, String value) {
        if (value == null) {
            return "";
        }
        if (!properties.getMasking().isEnabled()) {
            return safe(value);
        }
        return isSensitiveName(name) ? MASKED : safe(value);
    }

    /**
     * Masks sensitive query parameter values while preserving the query shape.
     */
    public String maskQueryString(String queryString) {
        if (queryString == null || queryString.isBlank()) {
            return "";
        }

        List<String> maskedParts = new ArrayList<>();
        for (String part : queryString.split("&")) {
            int equalsIndex = part.indexOf('=');
            if (equalsIndex < 0) {
                maskedParts.add(safe(part));
                continue;
            }

            String name = decode(part.substring(0, equalsIndex));
            String value = part.substring(equalsIndex + 1);
            maskedParts.add(name + "=" + mask(name, decode(value)));
        }
        return String.join("&", maskedParts);
    }

    /**
     * Masks sensitive fields in a JSON object or array. If parsing fails, the
     * raw body is sanitized and returned instead of failing the request.
     */
    public String maskJsonBody(String body) {
        if (body == null || body.isBlank() || !properties.getMasking().isEnabled()) {
            return safe(body);
        }

        try {
            Object json = objectMapper.readValue(body, new TypeReference<Object>() {
            });
            Object masked = maskJsonValue(json, null);
            return objectMapper.writeValueAsString(masked);
        } catch (JsonProcessingException e) {
            return safe(body);
        }
    }

    @SuppressWarnings("unchecked")
    private Object maskJsonValue(Object value, String fieldName) {
        if (value instanceof Map<?, ?> source) {
            Map<String, Object> masked = new LinkedHashMap<>();
            source.forEach((key, childValue) -> {
                String childName = String.valueOf(key);
                masked.put(childName, maskJsonValue(childValue, childName));
            });
            return masked;
        }
        if (value instanceof List<?> source) {
            return source.stream()
                    .map(item -> maskJsonValue(item, fieldName))
                    .toList();
        }
        if (fieldName != null && isSensitiveName(fieldName)) {
            return MASKED;
        }
        return value;
    }

    private boolean isSensitiveName(String name) {
        if (name == null || properties.getMasking().getFields() == null) {
            return false;
        }
        String normalizedName = normalize(name);
        return properties.getMasking().getFields().stream()
                .map(this::normalize)
                .anyMatch(normalizedName::contains);
    }

    private String normalize(String value) {
        return value == null
                ? ""
                : value.toLowerCase(Locale.ROOT).replace("_", "").replace("-", "");
    }

    private String decode(String value) {
        return UriUtils.decode(value, StandardCharsets.UTF_8);
    }

    private String safe(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("[\\r\\n\\t]", "_");
    }
}

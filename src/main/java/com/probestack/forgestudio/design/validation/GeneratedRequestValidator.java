package com.probestack.forgestudio.design.validation;

import com.probestack.forgestudio.design.config.validation.ValidationProperties;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Performs generated request validation only when app.validation.enabled=true.
 *
 * <p>Controllers call this validator explicitly before service logic. Keeping
 * validation behind this component allows local scaffold testing with validation
 * disabled while still supporting strict validation in enterprise/prod profiles.
 */
@Component
public class GeneratedRequestValidator {

    private static final Logger log = LoggerFactory.getLogger(GeneratedRequestValidator.class);

    private final ValidationProperties properties;
    private final Validator validator;

    public GeneratedRequestValidator(ValidationProperties properties, Validator validator) {
        this.properties = properties;
        this.validator = validator;
    }

    /**
     * Validates a generated request object when validation is enabled.
     *
     * @param operationName generated API operation name, used for logs
     * @param requestBody request body object to validate
     */
    public void validate(String operationName, Object requestBody) {
        if (!properties.isEnabled()) {
            log.debug("Generated request validation skipped. operationName={}", operationName);
            return;
        }
        if (requestBody == null) {
            log.debug("Generated request validation skipped for null body. operationName={}", operationName);
            return;
        }

        Set<ConstraintViolation<Object>> violations = validator.validate(requestBody);
        if (violations.isEmpty()) {
            log.debug("Generated request validation passed. operationName={}", operationName);
            return;
        }

        List<String> errors = violations.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .map(this::toErrorMessage)
                .toList();
        log.warn("Generated request validation failed. operationName={}, errorCount={}",
                operationName, errors.size());
        throw new GeneratedValidationException(errors);
    }

    private String toErrorMessage(ConstraintViolation<Object> violation) {
        return "field " + violation.getPropertyPath() + " " + violation.getMessage();
    }
}

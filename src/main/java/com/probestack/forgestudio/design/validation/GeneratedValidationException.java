package com.probestack.forgestudio.design.validation;

import java.util.List;

/**
 * Exception raised when generated request validation is enabled and the request
 * does not satisfy the generated model constraints.
 */
public class GeneratedValidationException extends RuntimeException {

    private final List<String> errors;

    public GeneratedValidationException(List<String> errors) {
        super(String.join("; ", errors));
        this.errors = List.copyOf(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}

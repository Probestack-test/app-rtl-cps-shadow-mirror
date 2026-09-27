package com.probestack.forgestudio.design.exception;

import org.springframework.http.HttpStatus;

/**
 * Single reusable generated exception type.
 *
 * <p>Use the static factory methods for common cases, or {@link #of} when
 * adding service-specific business errors. This keeps generated services lean
 * while preserving an enterprise error contract.</p>
 */
public class ApiException extends RuntimeException {

    public static final String BAD_REQUEST = "bad_request";
    public static final String BUSINESS_RULE_FAILED = "business_rule_failed";
    public static final String CONFLICT = "conflict";
    public static final String DATABASE_ERROR = "database_error";
    public static final String INTERNAL_ERROR = "internal_server_error";
    public static final String NOT_IMPLEMENTED = "not_implemented";
    public static final String RESOURCE_NOT_FOUND = "resource_not_found";

    private final HttpStatus status;
    private final String code;
    private final String userMessage;
    private final String systemMessage;

    private ApiException(HttpStatus status, String code, String userMessage, String systemMessage) {
        super(systemMessage);
        this.status = status;
        this.code = code;
        this.userMessage = userMessage;
        this.systemMessage = systemMessage;
    }

    public static ApiException of(
            HttpStatus status,
            String code,
            String userMessage,
            String systemMessage
    ) {
        return new ApiException(status, code, userMessage, systemMessage);
    }

    public static ApiException badRequest(String userMessage, String systemMessage) {
        return of(HttpStatus.BAD_REQUEST, BAD_REQUEST, userMessage, systemMessage);
    }

    public static ApiException businessError(String code, String userMessage, String systemMessage) {
        return of(HttpStatus.UNPROCESSABLE_ENTITY, code, userMessage, systemMessage);
    }

    public static ApiException conflict(String userMessage, String systemMessage) {
        return of(HttpStatus.CONFLICT, CONFLICT, userMessage, systemMessage);
    }

    public static ApiException databaseError(String systemMessage) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, DATABASE_ERROR, "Database operation failed", systemMessage);
    }

    public static ApiException internalError(String systemMessage) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR, "Unexpected service error", systemMessage);
    }

    public static ApiException notFound(String resource, Object id) {
        return of(
                HttpStatus.NOT_FOUND,
                RESOURCE_NOT_FOUND,
                "Requested resource was not found",
                resource + " was not found for id " + id
        );
    }

    public static ApiException notImplemented(String systemMessage) {
        return of(
                HttpStatus.NOT_IMPLEMENTED,
                NOT_IMPLEMENTED,
                "Operation is not implemented",
                systemMessage
        );
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getSystemMessage() {
        return systemMessage;
    }
}

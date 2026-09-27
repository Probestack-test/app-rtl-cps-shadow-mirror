package com.probestack.forgestudio.design.exception;

import java.time.Instant;

/**
 * Standard error response returned by generated APIs when exception handling is selected.
 */
public class ApiError {

    private Instant timestamp = Instant.now();
    private int status;
    private String code;
    private String userMessage;
    private String systemMessage;
    private String path;
    private String correlationId;
    private String stackTrace;

    public ApiError() {
    }

    public ApiError(
            int status,
            String code,
            String userMessage,
            String systemMessage,
            String path,
            String correlationId
    ) {
        this.status = status;
        this.code = code;
        this.userMessage = userMessage;
        this.systemMessage = systemMessage;
        this.path = path;
        this.correlationId = correlationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}

package com.probestack.forgestudio.design.logging;

/**
 * Structured summary event for one generated business operation.
 *
 * <p>Generated services create this event once per operation. The logger owns
 * formatting, masking, provider-specific fields, and configuration checks so
 * service methods stay focused on business behavior.</p>
 */
public class BusinessOperationEvent {

    private String operation;
    private String resource;
    private String action;
    private String status;
    private String repository;
    private String collection;
    private String documentId;
    private Integer recordCount;
    private Long durationMs;
    private String errorCode;
    private String errorMessage;

    public static Builder builder() {
        return new Builder();
    }

    public String getOperation() {
        return operation;
    }

    public String getResource() {
        return resource;
    }

    public String getAction() {
        return action;
    }

    public String getStatus() {
        return status;
    }

    public String getRepository() {
        return repository;
    }

    public String getCollection() {
        return collection;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static class Builder {
        private final BusinessOperationEvent event = new BusinessOperationEvent();

        public Builder operation(String operation) {
            event.operation = operation;
            return this;
        }

        public Builder resource(String resource) {
            event.resource = resource;
            return this;
        }

        public Builder action(String action) {
            event.action = action;
            return this;
        }

        public Builder status(String status) {
            event.status = status;
            return this;
        }

        public Builder repository(String repository) {
            event.repository = repository;
            return this;
        }

        public Builder collection(String collection) {
            event.collection = collection;
            return this;
        }

        public Builder documentId(String documentId) {
            event.documentId = documentId;
            return this;
        }

        public Builder recordCount(Integer recordCount) {
            event.recordCount = recordCount;
            return this;
        }

        public Builder durationMs(Long durationMs) {
            event.durationMs = durationMs;
            return this;
        }

        public Builder errorCode(String errorCode) {
            event.errorCode = errorCode;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            event.errorMessage = errorMessage;
            return this;
        }

        public BusinessOperationEvent build() {
            return event;
        }
    }
}

package com.probestack.forgestudio.design.advice;

import com.probestack.forgestudio.design.exception.ApiError;
import com.probestack.forgestudio.design.exception.ApiException;
import com.probestack.forgestudio.design.validation.GeneratedValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Centralized generated API exception mapper.
 *
 * <p>All handled errors use the same {@link ApiError} response shape. Stack
 * traces are disabled by default and should only be enabled for local debugging.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${app.exception.include-system-message:true}")
    private boolean includeSystemMessage;

    @Value("${app.exception.include-stacktrace:false}")
    private boolean includeStacktrace;

    @Value("${app.exception.include-correlation-id:true}")
    private boolean includeCorrelationId;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException exception, HttpServletRequest request) {
        return build(
                exception.getStatus(),
                exception.getCode(),
                exception.getUserMessage(),
                exception.getSystemMessage(),
                exception,
                request
        );
    }

    @ExceptionHandler(GeneratedValidationException.class)
    public ResponseEntity<ApiError> handleGeneratedValidation(
            GeneratedValidationException exception,
            HttpServletRequest request
    ) {
        return build(
                HttpStatus.BAD_REQUEST,
                "validation_error",
                "Request validation failed",
                String.join("; ", exception.getErrors()),
                exception,
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleBeanValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        return build(HttpStatus.BAD_REQUEST, "validation_error", "Request validation failed",
                exception.getMessage(), exception, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        return build(HttpStatus.BAD_REQUEST, "validation_error", "Request validation failed",
                exception.getMessage(), exception, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        return build(HttpStatus.BAD_REQUEST, "invalid_request_parameter",
                "Invalid request parameter",
                "Invalid value for parameter '" + exception.getName() + "': " + exception.getMessage(),
                exception,
                request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request
    ) {
        return build(HttpStatus.BAD_REQUEST, "missing_request_parameter",
                "Missing request parameter",
                "Required parameter '" + exception.getParameterName() + "' is not present",
                exception,
                request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableBody(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return build(HttpStatus.BAD_REQUEST, "invalid_request_body",
                "Request body is invalid", exception.getMessage(), exception, request);
    }

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ApiException.RESOURCE_NOT_FOUND,
                "Requested resource was not found", exception.getMessage(), exception, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(
            ResponseStatusException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        String code = status == HttpStatus.NOT_FOUND ? ApiException.RESOURCE_NOT_FOUND : "request_failed";
        String userMessage = status == HttpStatus.NOT_FOUND ? "Requested resource was not found" : "Request failed";
        return build(status, code, userMessage, exception.getReason(), exception, request);
    }

    // No handler here is specific to a persistence technology (e.g. Spring's DataAccessException,
    // from spring-tx) — this class is now generated unconditionally regardless of whether
    // persistence is enabled (PersistenceType.NONE is a valid, selectable choice), so it must not
    // depend on a dependency that choice might not bring onto the classpath. Persistence failures
    // still get a proper 500 via the generic handler below.

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ApiException.INTERNAL_ERROR,
                "Unexpected service error", exception.getMessage(), exception, request);
    }

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String code,
            String userMessage,
            String systemMessage,
            Exception exception,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                status.value(),
                code,
                userMessage,
                includeSystemMessage ? safe(systemMessage) : null,
                request.getRequestURI(),
                includeCorrelationId ? correlationId(request) : null
        );
        if (includeStacktrace) {
            error.setStackTrace(stacktrace(exception));
        }
        return ResponseEntity.status(status).body(error);
    }

    private String correlationId(HttpServletRequest request) {
        String fromMdc = MDC.get("correlationId");
        if (fromMdc != null && !fromMdc.isBlank()) {
            return safe(fromMdc);
        }
        String headerValue = request.getHeader("X-Correlation-ID");
        return headerValue == null || headerValue.isBlank() ? null : safe(headerValue);
    }

    private String stacktrace(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    private String safe(String value) {
        if (value == null || value.isBlank()) {
            return "-";
        }
        return value.replaceAll("[\\r\\n\\t]", "_");
    }
}

# Exception Handling User Guide

This service was generated with optimized exception handling. The generated bundle keeps the exception model lean while still providing a consistent enterprise response contract.

## Generated Classes

```text
com.probestack.forgestudio.design.exception.ApiError
com.probestack.forgestudio.design.exception.ApiException
com.probestack.forgestudio.design.advice.GlobalExceptionHandler
```

Only three Java files are generated.

## Error Response Shape

```json
{
  "timestamp": "2026-05-23T10:15:30Z",
  "status": 404,
  "code": "resource_not_found",
  "userMessage": "Requested resource was not found",
  "systemMessage": "ComplianceRule was not found for id CR-1001",
  "path": "/governance/v1/compliance-rules/CR-1001",
  "correlationId": "abc-123"
}
```

## Throwing Business Errors

Use `ApiException` factory methods from service code:

```java
throw ApiException.notFound("ComplianceRule", ruleId);
```

```java
throw ApiException.businessError(
        "business_rule_failed",
        "Unable to process request",
        "Asset type is unsupported for this operation"
);
```

```java
throw ApiException.notImplemented(
        "Generated operation calculateForecast requires custom business logic"
);
```

## Configuration

```properties
app.exception.include-system-message=${APP_EXCEPTION_INCLUDE_SYSTEM_MESSAGE:true}
app.exception.include-stacktrace=${APP_EXCEPTION_INCLUDE_STACKTRACE:false}
app.exception.include-correlation-id=${APP_EXCEPTION_INCLUDE_CORRELATION_ID:true}
```

| Property | Purpose | Default |
|----------|---------|---------|
| `app.exception.include-system-message` | Includes technical detail in `systemMessage`. | `true` |
| `app.exception.include-stacktrace` | Includes stack trace in API response. Use only for local debugging. | `false` |
| `app.exception.include-correlation-id` | Includes request correlation id when available. | `true` |

## Handled Failures

The generated handler maps these failures to the standard `ApiError` response:

- `ApiException`
- generated validation failures
- bean validation failures
- invalid request body
- invalid request parameter type
- not found errors
- Spring `ResponseStatusException`
- Spring Data `DataAccessException`
- fallback `Exception`

## Interaction With Other Options

- Security still owns `401` and `403` responses before controller logic.
- Validation throws generated validation exceptions; this handler formats them.
- Logging writes HTTP/business logs; this handler formats API error responses.
- Business logs and error responses share the same correlation id when logging is selected.

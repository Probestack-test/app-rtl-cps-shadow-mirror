# Request Validation User Guide

This generated service includes configuration-driven request validation.

## Default Behavior

Validation is disabled by default so generated scaffold APIs are easy to test from Swagger UI, Postman, curl, or copied request bodies while business logic is still being developed.

```properties
app.validation.enabled=${APP_VALIDATION_ENABLED:false}
```

## Enable Strict Validation

Set this environment variable before running the service:

```bash
APP_VALIDATION_ENABLED=true
```

When validation is enabled, generated controllers validate request bodies before service logic runs. Invalid requests return a structured `400` response:

```json
{
  "code": "validation_error",
  "userMessage": "Request validation failed",
  "systemMessage": "field severity must not be null",
  "path": "/your-api-path"
}
```

## Local Testing

Use the generated Postman collection under `postman/` or Swagger UI at:

```text
http://localhost:8080/swagger-ui.html
```

The generated Postman collection uses OpenAPI examples, defaults, and enum values so required enum fields are populated with valid sample values.

## Production Guidance

For production-like testing, set `APP_VALIDATION_ENABLED=true` in your deployment environment. This keeps the generated API scaffold flexible during early development while allowing stricter contract enforcement when you are ready.

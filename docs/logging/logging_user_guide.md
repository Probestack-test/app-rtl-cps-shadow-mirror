# Logging User Guide

This service was generated with HTTP logging enabled because `frameworkOptions.enterpriseLogging=true` was selected. The generated logging layer is fully configuration-driven, so developers can decide what to log without changing Java code.

## Generated Package

```text
com.probestack.forgestudio.design.logging
```

Generated classes:

- `LoggingConfig`: enables logging configuration properties.
- `LoggingProperties`: maps all `app.logging.*` settings.
- `HttpLoggingFilter`: logs request, response, and failure events.
- `BusinessOperationLogger`: logs one business summary event per generated service operation.
- `BusinessOperationEvent`: carries generated business operation metadata.
- `SensitiveDataMasker`: masks sensitive headers, query parameters, and JSON body fields.

Security classes remain under security packages. Logging is controlled by the framework logging option, not by the security option.

## Default Behavior

```text
request metadata: enabled
request headers: enabled and masked
request body: disabled
response metadata: disabled
response body: disabled
business operation summary: enabled
sensitive data masking: enabled
provider format: CONSOLE
destination: CONSOLE
```

The defaults are intentionally conservative. They provide useful diagnostics while avoiding accidental response-body or token logging.

When the request does not provide a provider or destination, the generated service uses `CONSOLE` provider and `CONSOLE` destination, which writes to the standard output stream. Cloud Run and most container platforms collect stdout automatically. For local Grafana or Splunk testing, set `app.logging.destination=FILE` or `CONSOLE_AND_FILE` and review the generated `logs/application.log` file.

## Metadata, Headers, and Body

Request metadata is the safe summary of the HTTP request. It does not include the request payload. Metadata helps developers answer: which endpoint was called, how long it took, who called it, and what status was returned.

Example request metadata:

```json
{
  "method": "POST",
  "path": "/governance/v1/compliance-rules",
  "query": "projectName=Payment%20Gateway&resourceType=MICROSERVICE",
  "status": 201,
  "durationMs": 42,
  "clientIp": "127.0.0.1",
  "userAgent": "PostmanRuntime/7.51.1",
  "correlationId": "1f2a4d6e-8b2c-4b0d-9b4b-3dc8f8f75c10"
}
```

Request headers are the HTTP headers sent by the caller. Headers are useful for diagnostics, but they often contain secrets such as bearer tokens, cookies, API keys, and client credentials. The generated service logs headers by default, but masks sensitive values.

Example masked headers:

```json
{
  "authorization": "***",
  "content-type": "application/json",
  "accept": "application/json",
  "user-agent": "PostmanRuntime/7.51.1"
}
```

Request body is the actual payload sent to the API. It is disabled by default because it can contain business data, credentials, tokens, personally identifiable information, or large payloads.

Example request body:

```json
{
  "ruleName": "Documentation required for all endpoints",
  "severity": "HIGH",
  "createdBy": "admin@probestack.io"
}
```

Default logging policy:

| Item | Meaning | Default | Reason |
|------|---------|---------|--------|
| Request metadata | Method, path, query, status, duration, client IP, user agent, correlation ID. | Enabled | Useful and generally safe for diagnostics. |
| Request headers | HTTP headers such as `Authorization`, `Content-Type`, and `Accept`. | Enabled and masked | Useful for debugging auth/content-type issues, but secrets must be hidden. |
| Request body | JSON/XML/form payload sent by the caller. | Disabled | Payloads may contain sensitive or high-volume business data. |

## Configuration

```properties
# Master switch
app.logging.enabled=${APP_LOGGING_ENABLED:true}

# Provider format controls the field naming/style of log events.
# Supported values: CONSOLE, GCP, GRAFANA, SPLUNK
app.logging.provider=${APP_LOGGING_PROVIDER:CONSOLE}
app.logging.structured=${APP_LOGGING_STRUCTURED:true}
app.logging.service-name=${APP_LOGGING_SERVICE_NAME:shadow-mirror}
app.logging.service-version=${APP_LOGGING_SERVICE_VERSION:1.0.0}
app.logging.environment=${APP_LOGGING_ENVIRONMENT:local}

# Destination controls where logs are physically written.
# CONSOLE writes to stdout/stderr. FILE writes to logs/application.log.
# CONSOLE_AND_FILE writes to both.
app.logging.destination=${APP_LOGGING_DESTINATION:CONSOLE}

# File logging, used when destination is FILE or CONSOLE_AND_FILE
app.logging.file.path=${APP_LOGGING_FILE_PATH:logs}
app.logging.file.name=${APP_LOGGING_FILE_NAME:application.log}
app.logging.file.max-size=${APP_LOGGING_FILE_MAX_SIZE:10MB}
app.logging.file.max-history=${APP_LOGGING_FILE_MAX_HISTORY:7}

# Correlation ID header. Incoming value is reused; otherwise the service creates one.
app.logging.correlation-header=${APP_LOGGING_CORRELATION_HEADER:X-Correlation-ID}

# Maximum body bytes logged when body logging is explicitly enabled.
app.logging.max-body-size-bytes=${APP_LOGGING_MAX_BODY_SIZE_BYTES:8192}

# Request logging
app.logging.request.enabled=${APP_LOGGING_REQUEST_ENABLED:true}
app.logging.request.query-enabled=${APP_LOGGING_REQUEST_QUERY_ENABLED:true}
app.logging.request.client-ip-enabled=${APP_LOGGING_REQUEST_CLIENT_IP_ENABLED:true}
app.logging.request.user-agent-enabled=${APP_LOGGING_REQUEST_USER_AGENT_ENABLED:true}
app.logging.request.body-enabled=${APP_LOGGING_REQUEST_BODY_ENABLED:false}

# Response logging
app.logging.response.enabled=${APP_LOGGING_RESPONSE_ENABLED:false}
app.logging.response.body-enabled=${APP_LOGGING_RESPONSE_BODY_ENABLED:false}

# Header logging and masking
app.logging.headers.enabled=${APP_LOGGING_HEADERS_ENABLED:true}
app.logging.masking.enabled=${APP_LOGGING_MASKING_ENABLED:true}
app.logging.masking.fields=${APP_LOGGING_MASKING_FIELDS:authorization,cookie,set-cookie,password,token,secret,apikey,api-key,clientsecret,client-secret}

# Business operation summary logging
app.logging.business.enabled=${APP_LOGGING_BUSINESS_ENABLED:true}
```

## Configuration Reference

| Property | Purpose | Default |
|----------|---------|---------|
| `app.logging.enabled` | Turns the generated logging filter on or off. | `true` |
| `app.logging.provider` | Controls log field naming/style for `CONSOLE`, `GCP`, `GRAFANA`, or `SPLUNK`. It does not decide where logs are written. | `CONSOLE` |
| `app.logging.structured` | Writes JSON logs when `true`; writes map-style logs when `false`. | `true` |
| `app.logging.destination` | Controls where logs are physically written: `CONSOLE`, `FILE`, or `CONSOLE_AND_FILE`. | `CONSOLE` |
| `app.logging.file.path` | Directory for local file logs. Used only when destination is `FILE` or `CONSOLE_AND_FILE`. | `logs` |
| `app.logging.file.name` | Active log filename under the configured file path. Used only when file output is enabled. | `application.log` |
| `app.logging.file.max-size` | Maximum size for one rolling log segment. Used only when file output is enabled. | `10MB` |
| `app.logging.file.max-history` | Number of daily rolled files to retain. Used only when file output is enabled. | `7` |
| `app.logging.service-name` | Service name included in structured logs. | generated artifact id |
| `app.logging.service-version` | Service version included in structured logs. | generated project version |
| `app.logging.environment` | Runtime environment label. | `local` |
| `app.logging.correlation-header` | Header used to read/write request correlation IDs. | `X-Correlation-ID` |
| `app.logging.max-body-size-bytes` | Maximum request/response body bytes to log when body logging is enabled. | `8192` |
| `app.logging.request.enabled` | Logs request metadata. | `true` |
| `app.logging.request.query-enabled` | Includes query string, with sensitive values masked. | `true` |
| `app.logging.request.client-ip-enabled` | Includes client IP from `X-Forwarded-For` or remote address. | `true` |
| `app.logging.request.user-agent-enabled` | Includes `User-Agent`. | `true` |
| `app.logging.request.body-enabled` | Includes JSON request body after masking. | `false` |
| `app.logging.response.enabled` | Logs response metadata such as status and duration. | `false` |
| `app.logging.response.body-enabled` | Includes JSON response body after masking. Requires response logging to be useful. | `false` |
| `app.logging.headers.enabled` | Includes request headers after masking. | `true` |
| `app.logging.masking.enabled` | Masks configured sensitive names in headers, query params, and JSON fields. | `true` |
| `app.logging.masking.fields` | Comma-separated sensitive field names or fragments. | common token/secret names |
| `app.logging.business.enabled` | Logs one business summary event per generated service operation. | `true` |

## Provider vs Destination

`provider` and `destination` are different on purpose.

`app.logging.provider` controls the shape of the log event. For example, GCP uses a `severity` field because Google Cloud Logging recognizes it. Other provider formats can use generic field names suitable for local console, Grafana/Loki, or Splunk ingestion.

`app.logging.destination` controls where the log event is written.

| Setting | Controls | Examples |
|---------|----------|----------|
| `app.logging.provider` | Log format and field names. | `CONSOLE`, `GCP`, `GRAFANA`, `SPLUNK` |
| `app.logging.destination` | Physical output stream or file. | `CONSOLE`, `FILE`, `CONSOLE_AND_FILE` |

Default:

```properties
app.logging.provider=CONSOLE
app.logging.destination=CONSOLE
```

This means the generated service writes generic structured logs to the standard output stream.

## Console and File Output

Console output is the default and is best for container platforms.

```properties
app.logging.destination=CONSOLE
```

Behavior:

- Logs are written to the standard output stream.
- Local developers see logs in the terminal.
- Cloud Run, Kubernetes, Docker, and most platforms automatically collect stdout.
- No local log file is created by the generated logging configuration.

File output is enabled only when the destination is `FILE` or `CONSOLE_AND_FILE`.

```properties
app.logging.destination=FILE
```

Behavior:

- Logs are written to the generated repository under `logs/application.log`.
- Console output is disabled by the generated logging configuration.
- Rolling files are created when the active file reaches `app.logging.file.max-size`.
- This is useful for local Grafana/Loki, Splunk forwarder, or file-based testing.

Console and file output can both be enabled:

```properties
app.logging.destination=CONSOLE_AND_FILE
```

Behavior:

- Logs are written to stdout.
- Logs are also written to `logs/application.log`.
- This is useful when developers want terminal visibility and a local file for agent-based collection.

## Log Format

Logs are written as one JSON object per line. With the default `CONSOLE` destination, Cloud Run automatically collects stdout and sends it to Google Cloud Logging. With `FILE`, logs are written to the generated service repository under `logs/application.log`. With `CONSOLE_AND_FILE`, both outputs are active.

Request example:

```json
{
  "timestamp": "2026-05-21T20:15:30.123Z",
  "severity": "INFO",
  "eventType": "HTTP_REQUEST",
  "provider": "GCP",
  "correlationId": "1f2a4d6e-8b2c-4b0d-9b4b-3dc8f8f75c10",
  "method": "POST",
  "path": "/accounts",
  "status": 200,
  "durationMs": 0,
  "headers": {
    "authorization": "***",
    "content-type": "application/json"
  }
}
```

Response example when response logging is enabled:

```json
{
  "timestamp": "2026-05-21T20:15:30.456Z",
  "severity": "INFO",
  "eventType": "HTTP_RESPONSE",
  "provider": "GCP",
  "correlationId": "1f2a4d6e-8b2c-4b0d-9b4b-3dc8f8f75c10",
  "method": "POST",
  "path": "/accounts",
  "status": 201,
  "durationMs": 132
}
```

Error example:

```json
{
  "timestamp": "2026-05-21T20:15:30.500Z",
  "severity": "ERROR",
  "eventType": "HTTP_ERROR",
  "provider": "GCP",
  "correlationId": "1f2a4d6e-8b2c-4b0d-9b4b-3dc8f8f75c10",
  "method": "POST",
  "path": "/accounts",
  "status": 500,
  "durationMs": 144,
  "exceptionType": "RuntimeException",
  "message": "Request failed"
}
```

Business operation example:

```json
{
  "timestamp": "2026-05-21T20:15:30.420Z",
  "severity": "INFO",
  "eventType": "BUSINESS_OPERATION",
  "provider": "GCP",
  "serviceName": "probestack-compliance-service-api",
  "serviceVersion": "1.0.0",
  "environment": "local",
  "operation": "createComplianceRule",
  "resource": "CreateComplianceRuleRequest",
  "action": "CREATE",
  "status": "SUCCESS",
  "correlationId": "1f2a4d6e-8b2c-4b0d-9b4b-3dc8f8f75c10",
  "httpMethod": "POST",
  "path": "/governance/v1/compliance-rules",
  "validationEnabled": true,
  "validationStatus": "PASSED",
  "validationErrorCount": 0,
  "repository": "CreateComplianceRuleRequestRepository",
  "collection": "probestack_compliance_service_api_compliance_rules",
  "documentId": "665f...",
  "recordCount": 1,
  "durationMs": 42
}
```

The business event is the quickest way to confirm generated persistence behavior. If `status=SUCCESS` and the event contains a `collection`, `repository`, and `documentId`, the generated service reached the repository and saved or read data. It is intentionally one summary event, not step-by-step logs.

## Common Runtime Changes

Disable all generated HTTP logging:

```properties
app.logging.enabled=false
```

Enable response metadata logging:

```properties
app.logging.response.enabled=true
```

Disable header logging:

```properties
app.logging.headers.enabled=false
```

Disable business operation summary logging:

```properties
app.logging.business.enabled=false
```

Enable request body logging for local debugging:

```properties
app.logging.request.body-enabled=true
```

Enable response body logging only when you are certain payloads are safe:

```properties
app.logging.response.enabled=true
app.logging.response.body-enabled=true
```

Change provider format:

```properties
app.logging.provider=SPLUNK
```

Write logs only to a local rolling file:

```properties
app.logging.destination=FILE
```

Write logs to stdout and a local rolling file:

```properties
app.logging.destination=CONSOLE_AND_FILE
```

Change the local log file location:

```properties
app.logging.file.path=logs
app.logging.file.name=application.log
app.logging.file.max-size=10MB
app.logging.file.max-history=7
```

## Provider Notes

- `CONSOLE`: generic structured JSON for local and container logs.
- `GCP`: Google Cloud Logging friendly field names such as `severity`; use `CONSOLE` destination for Cloud Run.
- `GRAFANA`: structured logs suitable for Loki or an agent; use `FILE` for local generated-repo testing.
- `SPLUNK`: structured logs suitable for Splunk forwarders or HEC sidecars; use `FILE` for local generated-repo testing.

The generated service does not send logs directly to vendor APIs. It writes to stdout, file, or both based on `app.logging.destination`. This keeps generated services lightweight and lets the runtime platform or a later agent handle vendor delivery.

## Local Testing

Start the service:

```bash
mvn spring-boot:run
```

Call any generated endpoint and review the application logs. Request logs are emitted by default. Response logs appear only when `app.logging.response.enabled=true`.

For file-output testing:

```bash
APP_LOGGING_DESTINATION=FILE mvn spring-boot:run
```

Then review:

```text
logs/application.log
```

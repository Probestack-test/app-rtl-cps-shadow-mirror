# shadow-mirror

Generated Spring Boot application from OpenAPI specification.

## Project Details
- **Group ID**: com.probestack.forgestudio.design
- **Artifact ID**: shadow-mirror
- **Version**: 1.0.0
- **Base Package**: com.probestack.forgestudio.design

## Getting Started

### Building the Project
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

## API Documentation
Once the application is running, access the Swagger UI at:
- http://localhost:8080/swagger-ui.html

## API Docs (OpenAPI)
- Source spec: `src/main/resources/openapi.yaml`
- Runtime docs: http://localhost:8080/v3/api-docs

## Postman Collection
Import the generated collection from:
- `testing/postman/shadow-mirror.postman_collection.json`

Set the `baseUrl` collection variable to:
- Local: `http://localhost:8080`
- Cloud Run: your deployed service URL

## Default API Behavior
Generated APIs are Mongo-backed scaffolds:
- `POST` operations persist request data and return `201`
- list `GET` operations return persisted records
- `GET /{id}` operations return a persisted record or `404`
- `DELETE /{id}` operations delete persisted records and return `204`
- domain-specific business operations return `501 NOT_IMPLEMENTED` until implemented

## MongoDB Persistence
Generated services use the configured MongoDB connection in `spring.data.mongodb.uri`.
Collections are prefixed by application name to avoid collisions, for example:
- `my_service_accounts`
- `my_service_transactions`

## V2 Enterprise Build Stack

- Java: `21`
- Spring Boot: `3.5.7`
- Maven: `3.9.11`

## Persistence

This service was generated with `MONGODB` persistence. Business services depend on domain repository
interfaces and the selected persistence adapter is isolated under `persistence`.

See `docs/persistence/mongodb_user_guide.md` for setup and extension guidance.

## OAuth2 Authorization Flow

This generated service includes OAuth2-compatible authorization flow support because `OAUTH2` was selected. OAuth2 is the authorization model; the access tokens issued by this scaffold are JWTs.

Full documentation:

- [OAuth2 / OAuth2.1 User Guide](docs/security/oauth2_user_guide.md)

### Modes

- `SELF_CONTAINED`: the service exposes `/oauth2/token`, supports client credentials, signs JWT access tokens, and validates those tokens locally. This is intended for local/dev testing.
- `ENTERPRISE`: the service disables local token generation and validates tokens from an external issuer.

### Key Strategies

- `GENERATED_ON_STARTUP`: creates an RSA key pair when the service starts. Tokens become invalid after restart.
- `STATIC_CONFIG`: loads RSA public/private PEM keys from environment variables so tokens remain valid across restarts.
- `EXTERNAL`: reserved for a later enterprise key source such as Secret Manager, KMS, Vault, or a shared IdP.

### Get a Token

```bash
curl -X POST http://localhost:8080/oauth2/token \
  -u generated-service-client:change-me \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=api.read api.write"
```

### Call a Protected API

```bash
curl http://localhost:8080/your-api-path \
  -H "Authorization: Bearer <access_token>"
```

### Token Failure Diagnostics

Missing, expired, invalid-signature, issuer-mismatch, and audience-mismatch tokens return `401` with `code`, `userMessage`, and a sanitized `systemMessage`. The service logs the detailed reason and stack trace with `eventType=SECURITY_TOKEN_VALIDATION_FAILED` or `eventType=SECURITY_REQUEST_REJECTED`; tokens and secrets are masked.

### Enterprise Mode

```properties
APP_SECURITY_OAUTH2_MODE=ENTERPRISE
APP_SECURITY_OAUTH2_ISSUER_URI=https://issuer.example.com
APP_SECURITY_OAUTH2_AUDIENCE=your-service-name
```


## Request Validation

Generated request validation is configuration-driven.

- Default: validation is disabled for easy scaffold testing.
- Enable strict validation with `APP_VALIDATION_ENABLED=true`.
- Full guide: [Request Validation User Guide](docs/validation/validation_user_guide.md)

When enabled, invalid request bodies return structured `400 validation_error` responses. The generated Postman collection still includes valid sample request bodies even when validation is disabled.


## Exception Handling

This generated service includes optimized exception handling because `frameworkOptions.exceptionHandling=true` was selected.

Full documentation:

- [Exception Handling User Guide](docs/exception/exception_handling_user_guide.md)

### Behavior

- Returns one consistent error response shape for generated API failures.
- Uses one reusable `ApiException` class instead of many generated custom exceptions.
- Includes `correlationId` when available.
- Hides stack traces by default.

### Runtime Configuration

```properties
app.exception.include-system-message=true
app.exception.include-stacktrace=false
app.exception.include-correlation-id=true
```


## Observability

This generated service includes observability assets because `frameworkOptions.observability=true` was selected.

The generated README summarizes the exact observability options selected for this service and lists only the actuator endpoints that were enabled.

Selected options:

- Health Checks
- Metrics
- Prometheus Endpoint
- Distributed Tracing

Full documentation:

- [Observability User Guide](docs/observability/observability_user_guide.md)

Enabled actuator endpoints:

- `/actuator/health`
- `/actuator/health/liveness`
- `/actuator/health/readiness`
- `/actuator/metrics`
- `/actuator/prometheus`


## Resilience

The generated downstream client includes:

- Timeout
- Retry
- Circuit Breaker

Runtime tuning is available in `application.properties` through environment-backed defaults.

- [Resilience User Guide](docs/resilience/resilience_user_guide.md)

## Enterprise Logging

This generated service includes configuration-driven HTTP logging because `frameworkOptions.enterpriseLogging=true` was selected.

Full documentation:

- [Logging User Guide](docs/logging/logging_user_guide.md)

### Behavior

- Logs request metadata by default.
- Logs one business operation summary per generated service method by default.
- Can log response metadata, request bodies, response bodies, and headers when enabled by configuration.
- Masks sensitive headers, query parameters, and JSON fields by default.
- Keeps logging outside the security package under `com.probestack.forgestudio.design.logging`.
- Writes structured JSON logs to stdout by default.
- Can write logs to `logs/application.log` for local Grafana/Loki or Splunk-forwarder testing.

### Runtime Configuration

All logging behavior is controlled with `app.logging.*` properties or matching environment variables. The generated defaults are:

```properties
app.logging.provider=CONSOLE
app.logging.destination=CONSOLE
```

Disable all generated HTTP logging with:

```properties
app.logging.enabled=false
```


## Cloud Run Deployment
This generated project includes GitHub Actions CI/CD for Google Cloud Run.

- Workflow: `.github/workflows/ci-cd.yml`
- Service name: `shadow-mirror`
- GCP project: `probestack-prod`
- Region: `us-central1`
- Artifact Registry repository: `us-central1-docker.pkg.dev/probestack-prod/probestack-prod-apps`

On every GitHub push, the workflow builds the application, publishes a Docker image, deploys to Cloud Run, and verifies `/actuator/health`.

See `DEPLOYMENT.md` for setup instructions.

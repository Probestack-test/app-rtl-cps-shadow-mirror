# OAuth2 / OAuth2.1 Authorization Flow User Guide

This service was generated with OAuth2/OAuth2.1 authorization-flow security enabled. OAuth2 is the authorization model; the generated access tokens are JWTs.

## What Is Included

- OAuth2 client credentials token generation at `/oauth2/token` in self-contained mode
- JWT bearer token validation for generated APIs
- RSA `RS256` token signing and verification
- Config-based switching between self-contained and enterprise mode
- Config-based key strategy selection
- Security-focused configuration, token issuing, and token validation classes

## Modes

| Mode | Purpose | Token endpoint |
|------|---------|----------------|
| `SELF_CONTAINED` | Local development and generated-service testing | Exposed by this service at `/oauth2/token` |
| `ENTERPRISE` | Production-style validation against an external issuer | Not exposed by this service |

Default:

```properties
app.security.oauth2.mode=${APP_SECURITY_OAUTH2_MODE:SELF_CONTAINED}
```

Use `SELF_CONTAINED` while testing generated APIs locally. Use `ENTERPRISE` when a central IdP or authorization service issues tokens.

## Key Strategy

| Strategy | Purpose |
|----------|---------|
| `GENERATED_ON_STARTUP` | Generates one RSA key pair when the service starts. Tokens become invalid after restart. |
| `STATIC_CONFIG` | Loads RSA public/private PEM keys from config or environment variables. Tokens survive restarts. |
| `EXTERNAL` | Reserved for a later enterprise key source such as Secret Manager, KMS, Vault, or a shared IdP. |

Default:

```properties
app.security.oauth2.key-strategy=${APP_SECURITY_OAUTH2_KEY_STRATEGY:GENERATED_ON_STARTUP}
```

The generated service creates the key pair once during startup, not for every token request. Per-request key generation would make tokens difficult to validate reliably.

## Start Locally

```bash
mvn spring-boot:run
```

Health check:

```bash
curl http://localhost:8080/actuator/health
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Generate A Token

Request an access token using the client credentials flow:

```bash
curl -X POST http://localhost:8080/oauth2/token \
  -u generated-service-client:change-me \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&scope=api.read api.write"
```

Successful response:

```json
{
  "access_token": "<jwt-access-token>",
  "scope": "api.read api.write",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

## Call A Protected API

Call a generated API with the token:

```bash
curl http://localhost:8080/your-api-path \
  -H "Authorization: Bearer <jwt-access-token>"
```

Expected behavior:

| Request | Result |
|---------|--------|
| No token | `401 Unauthorized` |
| Invalid token | `401 Unauthorized` |
| Valid token | API response |

## Invalid Token And Access Errors

Generated business APIs are protected by Spring Security before controller logic runs. If a request does not include a valid Bearer token, the request is rejected before it reaches the generated controller or service.

Missing token:

```bash
curl http://localhost:8080/your-api-path
```

Invalid token:

```bash
curl http://localhost:8080/your-api-path \
  -H "Authorization: Bearer invalid-token"
```

Expected `401 Unauthorized` response:

```json
{
  "code": "invalid_access_token",
  "userMessage": "Access Token is invalid",
  "systemMessage": "Bearer token is missing or invalid"
}
```

The `systemMessage` contains a sanitized failure reason such as missing Bearer header, expired token, invalid signature, issuer mismatch, or audience mismatch. The service logs the full exception stack trace with `eventType=SECURITY_TOKEN_VALIDATION_FAILED` or `eventType=SECURITY_REQUEST_REJECTED`. Tokens, client secrets, and authorization headers are masked and are not written to logs.

If a token is valid but the caller is not allowed to access a future scope-protected endpoint, the service returns `403 Forbidden`:

```json
{
  "code": "access_denied",
  "userMessage": "Access is denied",
  "systemMessage": "The authenticated principal is not allowed to access this resource"
}
```

## Local Testing Checklist

1. Start the service with `mvn spring-boot:run`.
2. Call one generated API without a token and confirm `401`.
3. Generate a token from `/oauth2/token`.
4. Call the same API with `Authorization: Bearer <access_token>`.
5. Restart the service and confirm old tokens fail when using `GENERATED_ON_STARTUP`.
6. Switch to `STATIC_CONFIG` if restart-stable dev tokens are needed.

## What The JWT Contains

The generated token is an RSA-signed JWT using `RS256`.

## OAuth2 vs JWT

OAuth2 defines how clients obtain and use access tokens. JWT defines one possible token format. This option generates OAuth-compatible token flow behavior. Choose `JWT Bearer Token Security` instead when you only need lightweight JWT validation and a local/dev token helper without OAuth2 authorization-server semantics.

Header:

```json
{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "<key-id>"
}
```

Claims:

```json
{
  "iss": "http://localhost:8080",
  "sub": "generated-service-client",
  "aud": ["shadow-mirror"],
  "iat": 1710000000,
  "nbf": 1710000000,
  "exp": 1710003600,
  "jti": "<token-id>",
  "client_id": "generated-service-client",
  "scope": "api.read api.write"
}
```

The exact timestamps and token ID are generated at runtime.

## Validation Rules

The service validates:

- JWT signature
- issuer
- expiration time
- not-before time
- audience
- bearer token format

Tokens and secrets are never written to logs.

## Configuration Reference

| Property | Environment Variable | Default |
|----------|----------------------|---------|
| `app.security.oauth2.mode` | `APP_SECURITY_OAUTH2_MODE` | `SELF_CONTAINED` |
| `app.security.oauth2.issuer-uri` | `APP_SECURITY_OAUTH2_ISSUER_URI` | `http://localhost:8080` |
| `app.security.oauth2.audience` | `APP_SECURITY_OAUTH2_AUDIENCE` | `shadow-mirror` |
| `app.security.oauth2.client-id` | `APP_SECURITY_OAUTH2_CLIENT_ID` | `generated-service-client` |
| `app.security.oauth2.client-secret` | `APP_SECURITY_OAUTH2_CLIENT_SECRET` | `change-me` |
| `app.security.oauth2.default-scopes` | `APP_SECURITY_OAUTH2_DEFAULT_SCOPES` | `api.read,api.write` |
| `app.security.oauth2.token-ttl-seconds` | `APP_SECURITY_OAUTH2_TOKEN_TTL_SECONDS` | `3600` |
| `app.security.oauth2.key-strategy` | `APP_SECURITY_OAUTH2_KEY_STRATEGY` | `GENERATED_ON_STARTUP` |
| `app.security.oauth2.rsa.private-key-pem` | `APP_SECURITY_OAUTH2_RSA_PRIVATE_KEY_PEM` | empty |
| `app.security.oauth2.rsa.public-key-pem` | `APP_SECURITY_OAUTH2_RSA_PUBLIC_KEY_PEM` | empty |
| `app.security.oauth2.rsa.key-id` | `APP_SECURITY_OAUTH2_RSA_KEY_ID` | `generated-dev-key` |

## Static Key Configuration

Use `STATIC_CONFIG` when you want tokens to remain valid after restart.

```properties
APP_SECURITY_OAUTH2_KEY_STRATEGY=STATIC_CONFIG
APP_SECURITY_OAUTH2_RSA_PRIVATE_KEY_PEM=-----BEGIN PRIVATE KEY-----...
APP_SECURITY_OAUTH2_RSA_PUBLIC_KEY_PEM=-----BEGIN PUBLIC KEY-----...
APP_SECURITY_OAUTH2_RSA_KEY_ID=dev-static-key-1
```

The private key signs tokens. The public key validates tokens.

## Enterprise Mode

In enterprise mode, this service does not issue tokens. It validates tokens from an external issuer.

```properties
APP_SECURITY_OAUTH2_MODE=ENTERPRISE
APP_SECURITY_OAUTH2_ISSUER_URI=https://issuer.example.com
APP_SECURITY_OAUTH2_AUDIENCE=your-service-name
```

Recommended enterprise flow:

1. Configure the generated service with `APP_SECURITY_OAUTH2_MODE=ENTERPRISE`.
2. Set `APP_SECURITY_OAUTH2_ISSUER_URI` to the IdP issuer URL.
3. Set `APP_SECURITY_OAUTH2_AUDIENCE` to the expected API audience.
4. Get tokens from the enterprise IdP.
5. Call generated APIs with `Authorization: Bearer <enterprise_access_token>`.

## OAuth2 vs OAuth2.1

For this generated service, both options use the same secure implementation pattern:

- client credentials flow for service-to-service token generation in self-contained mode
- JWT bearer token validation for protected APIs
- no password grant
- no implicit grant

OAuth2.1 tightens OAuth2 best practices by removing older insecure flows. This generated implementation already follows that direction.

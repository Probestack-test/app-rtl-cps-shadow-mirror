# Resilience User Guide

The generated service contains a reusable `ResilientDownstreamClient` for safe downstream `GET` calls.
The selected capabilities are:

- Timeout
- Retry
- Circuit Breaker

## Generated assets

- `resilience/DownstreamClientConfig.java`
- `resilience/ResilientDownstreamClient.java`
- Resilience settings in `application.properties`

## Timeout

Connection and response timeouts default to `2s` and `5s`. Override them with `APP_RESILIENCE_TIMEOUT_CONNECT` and `APP_RESILIENCE_TIMEOUT_RESPONSE`.

## Retry

The generated safe `GET` operation uses the `downstreamApi` retry instance. It makes at most three attempts with exponential backoff starting at `200ms`. Do not copy this retry policy to non-idempotent write operations without an application-specific idempotency strategy.

## Circuit Breaker

The `downstreamApi` circuit breaker opens when at least half of the calls in its configured count window fail. Calls rejected while open propagate a `CallNotPermittedException`; no synthetic fallback response is generated. After the open wait period, limited half-open calls determine whether the dependency has recovered.

## Using the downstream client

Inject `ResilientDownstreamClient` and call `get(URI, Class<T>)`. Terminal HTTP and connectivity failures are propagated; the client does not fabricate fallback data.

## Local testing and troubleshooting

Point the client at a controlled test endpoint and simulate latency or failures. Override the documented environment variables to shorten test windows. If an operation continues to fail, inspect the final exception and confirm that the selected properties are present in `application.properties`.

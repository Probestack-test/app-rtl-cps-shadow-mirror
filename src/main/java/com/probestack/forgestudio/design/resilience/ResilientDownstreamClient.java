package com.probestack.forgestudio.design.resilience;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

/** Technical adapter for safe, resilient downstream HTTP GET operations. */
@Component
public class ResilientDownstreamClient {

    private final RestClient restClient;

    /** Creates the adapter with the generated downstream RestClient. */
    public ResilientDownstreamClient(RestClient downstreamRestClient) {
        this.restClient = downstreamRestClient;
    }

    @Retry(name = "downstreamApi")
    @CircuitBreaker(name = "downstreamApi")
    /**
     * Executes a safe downstream GET and propagates the final failure after selected policies run.
     */
    public <T> T get(URI uri, Class<T> responseType) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(responseType);
    }
}

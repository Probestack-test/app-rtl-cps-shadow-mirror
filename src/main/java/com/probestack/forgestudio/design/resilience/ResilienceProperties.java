package com.probestack.forgestudio.design.resilience;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/** Runtime-tunable connection and response timeout settings for downstream calls. */
@ConfigurationProperties(prefix = "app.resilience.timeout")
public record ResilienceProperties(Duration connect, Duration response) {
}

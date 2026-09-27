package com.probestack.forgestudio.design.resilience;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/** Creates the shared downstream HTTP client used by the resilience adapter. */
@Configuration
@EnableConfigurationProperties(ResilienceProperties.class)
public class DownstreamClientConfig {

    /** Builds the downstream client with selected timeout behavior. */
    @Bean
    RestClient downstreamRestClient(RestClient.Builder builder, ResilienceProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(properties.connect());
        requestFactory.setReadTimeout(properties.response());
        builder.requestFactory(requestFactory);
        return builder.build();
    }
}

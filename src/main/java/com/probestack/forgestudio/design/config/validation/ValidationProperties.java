package com.probestack.forgestudio.design.config.validation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties that control generated request validation.
 *
 * <p>Validation is disabled by default for generated scaffold services so
 * developers can quickly test copied requests. Enable it through
 * APP_VALIDATION_ENABLED=true when strict OpenAPI-style validation is needed.
 */
@Component
@ConfigurationProperties(prefix = "app.validation")
public class ValidationProperties {

    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

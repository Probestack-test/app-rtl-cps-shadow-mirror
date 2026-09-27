package com.probestack.forgestudio.design.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration properties for generated HTTP logging.
 *
 * <p>Every field has a conservative default so the generated application can run
 * safely in local development and cloud environments. Override values in
 * {@code application.properties}, profile-specific properties, or environment
 * variables to change what is logged.</p>
 */
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {

    private boolean enabled = true;
    private Provider provider = Provider.GCP;
    private boolean structured = true;
    private Destination destination = Destination.CONSOLE;
    private String serviceName = "shadow-mirror";
    private String serviceVersion = "1.0.0";
    private String environment = "local";
    private String correlationHeader = "X-Correlation-ID";
    private int maxBodySizeBytes = 8192;
    private Request request = new Request();
    private Response response = new Response();
    private Headers headers = new Headers();
    private Masking masking = new Masking();
    private Business business = new Business();
    private File file = new File();

    public enum Provider {
        CONSOLE,
        GCP,
        GRAFANA,
        SPLUNK
    }

    public enum Destination {
        CONSOLE,
        FILE,
        CONSOLE_AND_FILE
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public boolean isStructured() {
        return structured;
    }

    public void setStructured(boolean structured) {
        this.structured = structured;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getCorrelationHeader() {
        return correlationHeader;
    }

    public void setCorrelationHeader(String correlationHeader) {
        this.correlationHeader = correlationHeader;
    }

    public int getMaxBodySizeBytes() {
        return maxBodySizeBytes;
    }

    public void setMaxBodySizeBytes(int maxBodySizeBytes) {
        this.maxBodySizeBytes = maxBodySizeBytes;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Masking getMasking() {
        return masking;
    }

    public void setMasking(Masking masking) {
        this.masking = masking;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isFileLoggingEnabled() {
        return destination == Destination.FILE || destination == Destination.CONSOLE_AND_FILE;
    }

    public boolean isConsoleLoggingEnabled() {
        return destination == Destination.CONSOLE || destination == Destination.CONSOLE_AND_FILE;
    }

    /**
     * Request logging switches.
     */
    public static class Request {
        private boolean enabled = true;
        private boolean bodyEnabled = false;
        private boolean queryEnabled = true;
        private boolean clientIpEnabled = true;
        private boolean userAgentEnabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isBodyEnabled() {
            return bodyEnabled;
        }

        public void setBodyEnabled(boolean bodyEnabled) {
            this.bodyEnabled = bodyEnabled;
        }

        public boolean isQueryEnabled() {
            return queryEnabled;
        }

        public void setQueryEnabled(boolean queryEnabled) {
            this.queryEnabled = queryEnabled;
        }

        public boolean isClientIpEnabled() {
            return clientIpEnabled;
        }

        public void setClientIpEnabled(boolean clientIpEnabled) {
            this.clientIpEnabled = clientIpEnabled;
        }

        public boolean isUserAgentEnabled() {
            return userAgentEnabled;
        }

        public void setUserAgentEnabled(boolean userAgentEnabled) {
            this.userAgentEnabled = userAgentEnabled;
        }
    }

    /**
     * Response logging switches. Response logging is disabled by default because
     * generated services may return sensitive or high-volume payloads.
     */
    public static class Response {
        private boolean enabled = false;
        private boolean bodyEnabled = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isBodyEnabled() {
            return bodyEnabled;
        }

        public void setBodyEnabled(boolean bodyEnabled) {
            this.bodyEnabled = bodyEnabled;
        }
    }

    /**
     * Header logging switches. Header logging is useful for debugging but all
     * sensitive headers are masked by default.
     */
    public static class Headers {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    /**
     * Sensitive data masking settings.
     */
    public static class Masking {
        private boolean enabled = true;
        private List<String> fields = new ArrayList<>(List.of(
                "authorization",
                "cookie",
                "set-cookie",
                "password",
                "token",
                "secret",
                "apikey",
                "api-key",
                "clientsecret",
                "client-secret"
        ));

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }
    }

    /**
     * One-event-per-operation business summary logging switches.
     */
    public static class Business {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    /**
     * Local rolling-file output settings. These are used when destination is
     * {@code FILE} or {@code CONSOLE_AND_FILE}.
     */
    public static class File {
        private String path = "logs";
        private String name = "application.log";
        private String maxSize = "10MB";
        private int maxHistory = 7;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(String maxSize) {
            this.maxSize = maxSize;
        }

        public int getMaxHistory() {
            return maxHistory;
        }

        public void setMaxHistory(int maxHistory) {
            this.maxHistory = maxHistory;
        }
    }
}

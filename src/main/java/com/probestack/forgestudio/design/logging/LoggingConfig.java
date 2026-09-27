package com.probestack.forgestudio.design.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Enables generated HTTP logging configuration.
 *
 * <p>The generated service keeps logging behavior configuration-driven through
 * {@code app.logging.*} properties. Developers can turn logging on or off, choose
 * a provider format, and decide whether request/response details should be
 * written without changing Java code. File output is configured here so the
 * generated service does not need an extra writer class.</p>
 */
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingConfig {

    private static final String GENERATED_FILE_APPENDER = "GENERATED_FILE";

    private final LoggingProperties properties;

    public LoggingConfig(LoggingProperties properties) {
        this.properties = properties;
    }

    /**
     * Configures local rolling-file logging when the generated service is set to
     * {@code FILE} or {@code CONSOLE_AND_FILE}. Console logging remains the
     * default because Cloud Run and most container platforms collect stdout.
     */
    @PostConstruct
    public void configureDestination() throws Exception {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        if (!properties.isConsoleLoggingEnabled()) {
            rootLogger.detachAppender("CONSOLE");
            rootLogger.detachAppender("STDOUT");
        }

        if (!properties.isFileLoggingEnabled() || rootLogger.getAppender(GENERATED_FILE_APPENDER) != null) {
            return;
        }

        Path logFile = resolveLogFile();
        Path logDirectory = logFile.getParent();
        if (logDirectory != null) {
            Files.createDirectories(logDirectory);
        }

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %-5level [%thread] %logger{36} - %msg%n");
        encoder.start();

        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setName(GENERATED_FILE_APPENDER);
        fileAppender.setFile(logFile.toString());
        fileAppender.setEncoder(encoder);

        SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new SizeAndTimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(rollingPattern(logFile));
        rollingPolicy.setMaxFileSize(FileSize.valueOf(properties.getFile().getMaxSize()));
        rollingPolicy.setMaxHistory(properties.getFile().getMaxHistory());
        rollingPolicy.start();

        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.start();
        rootLogger.addAppender(fileAppender);
    }

    private Path resolveLogFile() {
        return Path.of(properties.getFile().getPath(), properties.getFile().getName()).normalize();
    }

    private String rollingPattern(Path logFile) {
        Path parent = logFile.getParent() == null ? Path.of(".") : logFile.getParent();
        String fileName = logFile.getFileName().toString();
        String baseName = fileName.endsWith(".log")
                ? fileName.substring(0, fileName.length() - ".log".length())
                : fileName;
        return parent.resolve(baseName + ".%d{yyyy-MM-dd}.%i.log.gz").toString();
    }
}

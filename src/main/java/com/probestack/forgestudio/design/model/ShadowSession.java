package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.probestack.forgestudio.design.model.ShadowSessionLiveMetrics;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ShadowSession
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T02:19:23.993884578Z[GMT]")public class ShadowSession {

  private UUID shadowId;

  private String serviceName;

  /**
   * Gets or Sets environment
   */
  public enum EnvironmentEnum {
    STAGING("STAGING"),
    
    PRODUCTION("PRODUCTION");

    private String value;

    EnvironmentEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EnvironmentEnum fromValue(String value) {
      for (EnvironmentEnum b : EnvironmentEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private EnvironmentEnum environment;

  private String shadowVersion;

  private String baselineVersion;

  private BigDecimal mirrorPercent;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    QUEUED("QUEUED"),
    
    ATTACHING("ATTACHING"),
    
    MIRRORING("MIRRORING"),
    
    ANALYZING("ANALYZING"),
    
    COMPLETED("COMPLETED"),
    
    STOPPED("STOPPED"),
    
    FAILED("FAILED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private StatusEnum status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startedAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledEndAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime endedAt;

  private Integer progressPercent;

  private ShadowSessionLiveMetrics liveMetrics;

  /**
   * Current recommendation based on live data.
   */
  public enum PreliminaryVerdictEnum {
    SAFE_TO_PROCEED("SAFE_TO_PROCEED"),
    
    REVIEW_NEEDED("REVIEW_NEEDED"),
    
    DO_NOT_PROCEED("DO_NOT_PROCEED"),
    
    PENDING("PENDING");

    private String value;

    PreliminaryVerdictEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PreliminaryVerdictEnum fromValue(String value) {
      for (PreliminaryVerdictEnum b : PreliminaryVerdictEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private PreliminaryVerdictEnum preliminaryVerdict;

  private String errorMessage;

  public ShadowSession() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ShadowSession(UUID shadowId, String serviceName, String shadowVersion, String baselineVersion, BigDecimal mirrorPercent, StatusEnum status, OffsetDateTime startedAt) {
    this.shadowId = shadowId;
    this.serviceName = serviceName;
    this.shadowVersion = shadowVersion;
    this.baselineVersion = baselineVersion;
    this.mirrorPercent = mirrorPercent;
    this.status = status;
    this.startedAt = startedAt;
  }

  public ShadowSession shadowId(UUID shadowId) {
    this.shadowId = shadowId;
    return this;
  }

  /**
   * Get shadowId
   * @return shadowId
  */
  @NotNull @Valid   @Schema(name = "shadowId", example = "9c8f1a3b-6d7e-4a12-8f5e-123456789abc", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shadowId")
  public UUID getShadowId() {
    return shadowId;
  }

  public void setShadowId(UUID shadowId) {
    this.shadowId = shadowId;
  }

  public ShadowSession serviceName(String serviceName) {
    this.serviceName = serviceName;
    return this;
  }

  /**
   * Get serviceName
   * @return serviceName
  */
  @NotNull   @Schema(name = "serviceName", example = "payment-processor", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("serviceName")
  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public ShadowSession environment(EnvironmentEnum environment) {
    this.environment = environment;
    return this;
  }

  /**
   * Get environment
   * @return environment
  */
    @Schema(name = "environment", example = "PRODUCTION", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("environment")
  public EnvironmentEnum getEnvironment() {
    return environment;
  }

  public void setEnvironment(EnvironmentEnum environment) {
    this.environment = environment;
  }

  public ShadowSession shadowVersion(String shadowVersion) {
    this.shadowVersion = shadowVersion;
    return this;
  }

  /**
   * Get shadowVersion
   * @return shadowVersion
  */
  @NotNull   @Schema(name = "shadowVersion", example = "v2.5.0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shadowVersion")
  public String getShadowVersion() {
    return shadowVersion;
  }

  public void setShadowVersion(String shadowVersion) {
    this.shadowVersion = shadowVersion;
  }

  public ShadowSession baselineVersion(String baselineVersion) {
    this.baselineVersion = baselineVersion;
    return this;
  }

  /**
   * Get baselineVersion
   * @return baselineVersion
  */
  @NotNull   @Schema(name = "baselineVersion", example = "v2.4.1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("baselineVersion")
  public String getBaselineVersion() {
    return baselineVersion;
  }

  public void setBaselineVersion(String baselineVersion) {
    this.baselineVersion = baselineVersion;
  }

  public ShadowSession mirrorPercent(BigDecimal mirrorPercent) {
    this.mirrorPercent = mirrorPercent;
    return this;
  }

  /**
   * Get mirrorPercent
   * @return mirrorPercent
  */
  @NotNull @Valid   @Schema(name = "mirrorPercent", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mirrorPercent")
  public BigDecimal getMirrorPercent() {
    return mirrorPercent;
  }

  public void setMirrorPercent(BigDecimal mirrorPercent) {
    this.mirrorPercent = mirrorPercent;
  }

  public ShadowSession status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull   @Schema(name = "status", example = "MIRRORING", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ShadowSession startedAt(OffsetDateTime startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  /**
   * Get startedAt
   * @return startedAt
  */
  @NotNull @Valid   @Schema(name = "startedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("startedAt")
  public OffsetDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(OffsetDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public ShadowSession scheduledEndAt(OffsetDateTime scheduledEndAt) {
    this.scheduledEndAt = scheduledEndAt;
    return this;
  }

  /**
   * Get scheduledEndAt
   * @return scheduledEndAt
  */
  @Valid   @Schema(name = "scheduledEndAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scheduledEndAt")
  public OffsetDateTime getScheduledEndAt() {
    return scheduledEndAt;
  }

  public void setScheduledEndAt(OffsetDateTime scheduledEndAt) {
    this.scheduledEndAt = scheduledEndAt;
  }

  public ShadowSession endedAt(OffsetDateTime endedAt) {
    this.endedAt = endedAt;
    return this;
  }

  /**
   * Get endedAt
   * @return endedAt
  */
  @Valid   @Schema(name = "endedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endedAt")
  public OffsetDateTime getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(OffsetDateTime endedAt) {
    this.endedAt = endedAt;
  }

  public ShadowSession progressPercent(Integer progressPercent) {
    this.progressPercent = progressPercent;
    return this;
  }

  /**
   * Get progressPercent
   * minimum: 0
   * maximum: 100
   * @return progressPercent
  */
  @Min(0) @Max(100)   @Schema(name = "progressPercent", example = "45", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("progressPercent")
  public Integer getProgressPercent() {
    return progressPercent;
  }

  public void setProgressPercent(Integer progressPercent) {
    this.progressPercent = progressPercent;
  }

  public ShadowSession liveMetrics(ShadowSessionLiveMetrics liveMetrics) {
    this.liveMetrics = liveMetrics;
    return this;
  }

  /**
   * Get liveMetrics
   * @return liveMetrics
  */
  @Valid   @Schema(name = "liveMetrics", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("liveMetrics")
  public ShadowSessionLiveMetrics getLiveMetrics() {
    return liveMetrics;
  }

  public void setLiveMetrics(ShadowSessionLiveMetrics liveMetrics) {
    this.liveMetrics = liveMetrics;
  }

  public ShadowSession preliminaryVerdict(PreliminaryVerdictEnum preliminaryVerdict) {
    this.preliminaryVerdict = preliminaryVerdict;
    return this;
  }

  /**
   * Current recommendation based on live data.
   * @return preliminaryVerdict
  */
    @Schema(name = "preliminaryVerdict", example = "SAFE_TO_PROCEED", description = "Current recommendation based on live data.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("preliminaryVerdict")
  public PreliminaryVerdictEnum getPreliminaryVerdict() {
    return preliminaryVerdict;
  }

  public void setPreliminaryVerdict(PreliminaryVerdictEnum preliminaryVerdict) {
    this.preliminaryVerdict = preliminaryVerdict;
  }

  public ShadowSession errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Populated only if status = FAILED.
   * @return errorMessage
  */
    @Schema(name = "errorMessage", description = "Populated only if status = FAILED.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShadowSession shadowSession = (ShadowSession) o;
    return Objects.equals(this.shadowId, shadowSession.shadowId) &&
        Objects.equals(this.serviceName, shadowSession.serviceName) &&
        Objects.equals(this.environment, shadowSession.environment) &&
        Objects.equals(this.shadowVersion, shadowSession.shadowVersion) &&
        Objects.equals(this.baselineVersion, shadowSession.baselineVersion) &&
        Objects.equals(this.mirrorPercent, shadowSession.mirrorPercent) &&
        Objects.equals(this.status, shadowSession.status) &&
        Objects.equals(this.startedAt, shadowSession.startedAt) &&
        Objects.equals(this.scheduledEndAt, shadowSession.scheduledEndAt) &&
        Objects.equals(this.endedAt, shadowSession.endedAt) &&
        Objects.equals(this.progressPercent, shadowSession.progressPercent) &&
        Objects.equals(this.liveMetrics, shadowSession.liveMetrics) &&
        Objects.equals(this.preliminaryVerdict, shadowSession.preliminaryVerdict) &&
        Objects.equals(this.errorMessage, shadowSession.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shadowId, serviceName, environment, shadowVersion, baselineVersion, mirrorPercent, status, startedAt, scheduledEndAt, endedAt, progressPercent, liveMetrics, preliminaryVerdict, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShadowSession {\n");
    sb.append("    shadowId: ").append(toIndentedString(shadowId)).append("\n");
    sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
    sb.append("    environment: ").append(toIndentedString(environment)).append("\n");
    sb.append("    shadowVersion: ").append(toIndentedString(shadowVersion)).append("\n");
    sb.append("    baselineVersion: ").append(toIndentedString(baselineVersion)).append("\n");
    sb.append("    mirrorPercent: ").append(toIndentedString(mirrorPercent)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    startedAt: ").append(toIndentedString(startedAt)).append("\n");
    sb.append("    scheduledEndAt: ").append(toIndentedString(scheduledEndAt)).append("\n");
    sb.append("    endedAt: ").append(toIndentedString(endedAt)).append("\n");
    sb.append("    progressPercent: ").append(toIndentedString(progressPercent)).append("\n");
    sb.append("    liveMetrics: ").append(toIndentedString(liveMetrics)).append("\n");
    sb.append("    preliminaryVerdict: ").append(toIndentedString(preliminaryVerdict)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


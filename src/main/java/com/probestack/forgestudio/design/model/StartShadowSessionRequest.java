package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StartShadowSessionRequest
 */
@JsonTypeName("startShadowSession_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T02:19:23.993884578Z[GMT]")public class StartShadowSessionRequest {

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

  private Integer durationMinutes = 30;

  @Valid
  private List<String> samplePaths;

  @Valid
  private List<String> excludePaths;

  /**
   * How strictly to compare shadow vs baseline responses.
   */
  public enum CompareModeEnum {
    STATUS_CODE_ONLY("STATUS_CODE_ONLY"),
    
    STATUS_AND_BODY("STATUS_AND_BODY"),
    
    FULL_STRUCTURAL("FULL_STRUCTURAL"),
    
    JSON_DEEP_EQUAL("JSON_DEEP_EQUAL");

    private String value;

    CompareModeEnum(String value) {
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
    public static CompareModeEnum fromValue(String value) {
      for (CompareModeEnum b : CompareModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private CompareModeEnum compareMode = CompareModeEnum.STATUS_AND_BODY;

  private Boolean redactPii = true;

  public StartShadowSessionRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StartShadowSessionRequest(String serviceName, EnvironmentEnum environment, String shadowVersion, String baselineVersion, BigDecimal mirrorPercent) {
    this.serviceName = serviceName;
    this.environment = environment;
    this.shadowVersion = shadowVersion;
    this.baselineVersion = baselineVersion;
    this.mirrorPercent = mirrorPercent;
  }

  public StartShadowSessionRequest serviceName(String serviceName) {
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

  public StartShadowSessionRequest environment(EnvironmentEnum environment) {
    this.environment = environment;
    return this;
  }

  /**
   * Get environment
   * @return environment
  */
  @NotNull   @Schema(name = "environment", example = "PRODUCTION", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("environment")
  public EnvironmentEnum getEnvironment() {
    return environment;
  }

  public void setEnvironment(EnvironmentEnum environment) {
    this.environment = environment;
  }

  public StartShadowSessionRequest shadowVersion(String shadowVersion) {
    this.shadowVersion = shadowVersion;
    return this;
  }

  /**
   * Version to receive mirrored traffic.
   * @return shadowVersion
  */
  @NotNull   @Schema(name = "shadowVersion", example = "v2.5.0", description = "Version to receive mirrored traffic.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shadowVersion")
  public String getShadowVersion() {
    return shadowVersion;
  }

  public void setShadowVersion(String shadowVersion) {
    this.shadowVersion = shadowVersion;
  }

  public StartShadowSessionRequest baselineVersion(String baselineVersion) {
    this.baselineVersion = baselineVersion;
    return this;
  }

  /**
   * Version serving real users.
   * @return baselineVersion
  */
  @NotNull   @Schema(name = "baselineVersion", example = "v2.4.1", description = "Version serving real users.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("baselineVersion")
  public String getBaselineVersion() {
    return baselineVersion;
  }

  public void setBaselineVersion(String baselineVersion) {
    this.baselineVersion = baselineVersion;
  }

  public StartShadowSessionRequest mirrorPercent(BigDecimal mirrorPercent) {
    this.mirrorPercent = mirrorPercent;
    return this;
  }

  /**
   * Percentage of traffic to mirror to shadow.
   * minimum: 1
   * maximum: 100
   * @return mirrorPercent
  */
  @NotNull @Valid @DecimalMin("1") @DecimalMax("100")   @Schema(name = "mirrorPercent", example = "10", description = "Percentage of traffic to mirror to shadow.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mirrorPercent")
  public BigDecimal getMirrorPercent() {
    return mirrorPercent;
  }

  public void setMirrorPercent(BigDecimal mirrorPercent) {
    this.mirrorPercent = mirrorPercent;
  }

  public StartShadowSessionRequest durationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
    return this;
  }

  /**
   * Get durationMinutes
   * minimum: 5
   * maximum: 240
   * @return durationMinutes
  */
  @Min(5) @Max(240)   @Schema(name = "durationMinutes", example = "30", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("durationMinutes")
  public Integer getDurationMinutes() {
    return durationMinutes;
  }

  public void setDurationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
  }

  public StartShadowSessionRequest samplePaths(List<String> samplePaths) {
    this.samplePaths = samplePaths;
    return this;
  }

  public StartShadowSessionRequest addSamplePathsItem(String samplePathsItem) {
    if (this.samplePaths == null) {
      this.samplePaths = new ArrayList<>();
    }
    this.samplePaths.add(samplePathsItem);
    return this;
  }

  /**
   * Optional allowlist of URL patterns to mirror. If empty, mirrors all.
   * @return samplePaths
  */
    @Schema(name = "samplePaths", example = "[\"/api/v1/charge\",\"/api/v1/refund\"]", description = "Optional allowlist of URL patterns to mirror. If empty, mirrors all.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("samplePaths")
  public List<String> getSamplePaths() {
    return samplePaths;
  }

  public void setSamplePaths(List<String> samplePaths) {
    this.samplePaths = samplePaths;
  }

  public StartShadowSessionRequest excludePaths(List<String> excludePaths) {
    this.excludePaths = excludePaths;
    return this;
  }

  public StartShadowSessionRequest addExcludePathsItem(String excludePathsItem) {
    if (this.excludePaths == null) {
      this.excludePaths = new ArrayList<>();
    }
    this.excludePaths.add(excludePathsItem);
    return this;
  }

  /**
   * URL patterns to never mirror (e.g., /login).
   * @return excludePaths
  */
    @Schema(name = "excludePaths", example = "[\"/api/v1/admin/_*\",\"/actuator/_*\"]", description = "URL patterns to never mirror (e.g., /login).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("excludePaths")
  public List<String> getExcludePaths() {
    return excludePaths;
  }

  public void setExcludePaths(List<String> excludePaths) {
    this.excludePaths = excludePaths;
  }

  public StartShadowSessionRequest compareMode(CompareModeEnum compareMode) {
    this.compareMode = compareMode;
    return this;
  }

  /**
   * How strictly to compare shadow vs baseline responses.
   * @return compareMode
  */
    @Schema(name = "compareMode", example = "JSON_DEEP_EQUAL", description = "How strictly to compare shadow vs baseline responses.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("compareMode")
  public CompareModeEnum getCompareMode() {
    return compareMode;
  }

  public void setCompareMode(CompareModeEnum compareMode) {
    this.compareMode = compareMode;
  }

  public StartShadowSessionRequest redactPii(Boolean redactPii) {
    this.redactPii = redactPii;
    return this;
  }

  /**
   * Whether to redact PII from captured traffic.
   * @return redactPii
  */
    @Schema(name = "redactPii", example = "true", description = "Whether to redact PII from captured traffic.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("redactPii")
  public Boolean getRedactPii() {
    return redactPii;
  }

  public void setRedactPii(Boolean redactPii) {
    this.redactPii = redactPii;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StartShadowSessionRequest startShadowSessionRequest = (StartShadowSessionRequest) o;
    return Objects.equals(this.serviceName, startShadowSessionRequest.serviceName) &&
        Objects.equals(this.environment, startShadowSessionRequest.environment) &&
        Objects.equals(this.shadowVersion, startShadowSessionRequest.shadowVersion) &&
        Objects.equals(this.baselineVersion, startShadowSessionRequest.baselineVersion) &&
        Objects.equals(this.mirrorPercent, startShadowSessionRequest.mirrorPercent) &&
        Objects.equals(this.durationMinutes, startShadowSessionRequest.durationMinutes) &&
        Objects.equals(this.samplePaths, startShadowSessionRequest.samplePaths) &&
        Objects.equals(this.excludePaths, startShadowSessionRequest.excludePaths) &&
        Objects.equals(this.compareMode, startShadowSessionRequest.compareMode) &&
        Objects.equals(this.redactPii, startShadowSessionRequest.redactPii);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceName, environment, shadowVersion, baselineVersion, mirrorPercent, durationMinutes, samplePaths, excludePaths, compareMode, redactPii);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StartShadowSessionRequest {\n");
    sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
    sb.append("    environment: ").append(toIndentedString(environment)).append("\n");
    sb.append("    shadowVersion: ").append(toIndentedString(shadowVersion)).append("\n");
    sb.append("    baselineVersion: ").append(toIndentedString(baselineVersion)).append("\n");
    sb.append("    mirrorPercent: ").append(toIndentedString(mirrorPercent)).append("\n");
    sb.append("    durationMinutes: ").append(toIndentedString(durationMinutes)).append("\n");
    sb.append("    samplePaths: ").append(toIndentedString(samplePaths)).append("\n");
    sb.append("    excludePaths: ").append(toIndentedString(excludePaths)).append("\n");
    sb.append("    compareMode: ").append(toIndentedString(compareMode)).append("\n");
    sb.append("    redactPii: ").append(toIndentedString(redactPii)).append("\n");
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


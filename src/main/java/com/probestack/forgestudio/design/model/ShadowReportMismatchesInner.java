package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.probestack.forgestudio.design.model.ShadowReportMismatchesInnerBaselineResponse;
import com.probestack.forgestudio.design.model.ShadowReportMismatchesInnerShadowResponse;
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
 * ShadowReportMismatchesInner
 */
@JsonTypeName("ShadowReport_mismatches_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T01:32:49.939550536Z[GMT]")public class ShadowReportMismatchesInner {

  private UUID mismatchId;

  /**
   * Gets or Sets method
   */
  public enum MethodEnum {
    GET("GET"),
    
    POST("POST"),
    
    PUT("PUT"),
    
    DELETE("DELETE"),
    
    PATCH("PATCH");

    private String value;

    MethodEnum(String value) {
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
    public static MethodEnum fromValue(String value) {
      for (MethodEnum b : MethodEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private MethodEnum method;

  private String path;

  /**
   * Gets or Sets severity
   */
  public enum SeverityEnum {
    INFO("INFO"),
    
    WARNING("WARNING"),
    
    CRITICAL("CRITICAL");

    private String value;

    SeverityEnum(String value) {
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
    public static SeverityEnum fromValue(String value) {
      for (SeverityEnum b : SeverityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private SeverityEnum severity;

  /**
   * Gets or Sets differenceType
   */
  public enum DifferenceTypeEnum {
    STATUS_CODE_CHANGED("STATUS_CODE_CHANGED"),
    
    RESPONSE_BODY_DIFF("RESPONSE_BODY_DIFF"),
    
    FIELD_MISSING("FIELD_MISSING"),
    
    FIELD_EXTRA("FIELD_EXTRA"),
    
    TYPE_CHANGED("TYPE_CHANGED"),
    
    HEADER_CHANGED("HEADER_CHANGED"),
    
    TIMING_ONLY("TIMING_ONLY");

    private String value;

    DifferenceTypeEnum(String value) {
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
    public static DifferenceTypeEnum fromValue(String value) {
      for (DifferenceTypeEnum b : DifferenceTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private DifferenceTypeEnum differenceType;

  private ShadowReportMismatchesInnerBaselineResponse baselineResponse;

  private ShadowReportMismatchesInnerShadowResponse shadowResponse;

  private Integer sampleCount;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime firstSeenAt;

  private String probableCause;

  private String recommendedAction;

  public ShadowReportMismatchesInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ShadowReportMismatchesInner(UUID mismatchId, MethodEnum method, String path, SeverityEnum severity, DifferenceTypeEnum differenceType) {
    this.mismatchId = mismatchId;
    this.method = method;
    this.path = path;
    this.severity = severity;
    this.differenceType = differenceType;
  }

  public ShadowReportMismatchesInner mismatchId(UUID mismatchId) {
    this.mismatchId = mismatchId;
    return this;
  }

  /**
   * Get mismatchId
   * @return mismatchId
  */
  @NotNull @Valid   @Schema(name = "mismatchId", example = "b1a2c3d4-e5f6-7890-1234-567890abcdef", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mismatchId")
  public UUID getMismatchId() {
    return mismatchId;
  }

  public void setMismatchId(UUID mismatchId) {
    this.mismatchId = mismatchId;
  }

  public ShadowReportMismatchesInner method(MethodEnum method) {
    this.method = method;
    return this;
  }

  /**
   * Get method
   * @return method
  */
  @NotNull   @Schema(name = "method", example = "POST", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("method")
  public MethodEnum getMethod() {
    return method;
  }

  public void setMethod(MethodEnum method) {
    this.method = method;
  }

  public ShadowReportMismatchesInner path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  */
  @NotNull   @Schema(name = "path", example = "/api/v1/charge", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ShadowReportMismatchesInner severity(SeverityEnum severity) {
    this.severity = severity;
    return this;
  }

  /**
   * Get severity
   * @return severity
  */
  @NotNull   @Schema(name = "severity", example = "CRITICAL", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("severity")
  public SeverityEnum getSeverity() {
    return severity;
  }

  public void setSeverity(SeverityEnum severity) {
    this.severity = severity;
  }

  public ShadowReportMismatchesInner differenceType(DifferenceTypeEnum differenceType) {
    this.differenceType = differenceType;
    return this;
  }

  /**
   * Get differenceType
   * @return differenceType
  */
  @NotNull   @Schema(name = "differenceType", example = "STATUS_CODE_CHANGED", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("differenceType")
  public DifferenceTypeEnum getDifferenceType() {
    return differenceType;
  }

  public void setDifferenceType(DifferenceTypeEnum differenceType) {
    this.differenceType = differenceType;
  }

  public ShadowReportMismatchesInner baselineResponse(ShadowReportMismatchesInnerBaselineResponse baselineResponse) {
    this.baselineResponse = baselineResponse;
    return this;
  }

  /**
   * Get baselineResponse
   * @return baselineResponse
  */
  @Valid   @Schema(name = "baselineResponse", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("baselineResponse")
  public ShadowReportMismatchesInnerBaselineResponse getBaselineResponse() {
    return baselineResponse;
  }

  public void setBaselineResponse(ShadowReportMismatchesInnerBaselineResponse baselineResponse) {
    this.baselineResponse = baselineResponse;
  }

  public ShadowReportMismatchesInner shadowResponse(ShadowReportMismatchesInnerShadowResponse shadowResponse) {
    this.shadowResponse = shadowResponse;
    return this;
  }

  /**
   * Get shadowResponse
   * @return shadowResponse
  */
  @Valid   @Schema(name = "shadowResponse", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("shadowResponse")
  public ShadowReportMismatchesInnerShadowResponse getShadowResponse() {
    return shadowResponse;
  }

  public void setShadowResponse(ShadowReportMismatchesInnerShadowResponse shadowResponse) {
    this.shadowResponse = shadowResponse;
  }

  public ShadowReportMismatchesInner sampleCount(Integer sampleCount) {
    this.sampleCount = sampleCount;
    return this;
  }

  /**
   * How many requests hit this mismatch.
   * @return sampleCount
  */
    @Schema(name = "sampleCount", example = "42", description = "How many requests hit this mismatch.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sampleCount")
  public Integer getSampleCount() {
    return sampleCount;
  }

  public void setSampleCount(Integer sampleCount) {
    this.sampleCount = sampleCount;
  }

  public ShadowReportMismatchesInner firstSeenAt(OffsetDateTime firstSeenAt) {
    this.firstSeenAt = firstSeenAt;
    return this;
  }

  /**
   * Get firstSeenAt
   * @return firstSeenAt
  */
  @Valid   @Schema(name = "firstSeenAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstSeenAt")
  public OffsetDateTime getFirstSeenAt() {
    return firstSeenAt;
  }

  public void setFirstSeenAt(OffsetDateTime firstSeenAt) {
    this.firstSeenAt = firstSeenAt;
  }

  public ShadowReportMismatchesInner probableCause(String probableCause) {
    this.probableCause = probableCause;
    return this;
  }

  /**
   * AI-generated hypothesis for the root cause.
   * @return probableCause
  */
    @Schema(name = "probableCause", example = "Null discount_code when request lacks campaignId header; NPE in DiscountService.apply().", description = "AI-generated hypothesis for the root cause.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("probableCause")
  public String getProbableCause() {
    return probableCause;
  }

  public void setProbableCause(String probableCause) {
    this.probableCause = probableCause;
  }

  public ShadowReportMismatchesInner recommendedAction(String recommendedAction) {
    this.recommendedAction = recommendedAction;
    return this;
  }

  /**
   * Get recommendedAction
   * @return recommendedAction
  */
    @Schema(name = "recommendedAction", example = "Add null-safety check in DiscountService OR send campaignId header.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recommendedAction")
  public String getRecommendedAction() {
    return recommendedAction;
  }

  public void setRecommendedAction(String recommendedAction) {
    this.recommendedAction = recommendedAction;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShadowReportMismatchesInner shadowReportMismatchesInner = (ShadowReportMismatchesInner) o;
    return Objects.equals(this.mismatchId, shadowReportMismatchesInner.mismatchId) &&
        Objects.equals(this.method, shadowReportMismatchesInner.method) &&
        Objects.equals(this.path, shadowReportMismatchesInner.path) &&
        Objects.equals(this.severity, shadowReportMismatchesInner.severity) &&
        Objects.equals(this.differenceType, shadowReportMismatchesInner.differenceType) &&
        Objects.equals(this.baselineResponse, shadowReportMismatchesInner.baselineResponse) &&
        Objects.equals(this.shadowResponse, shadowReportMismatchesInner.shadowResponse) &&
        Objects.equals(this.sampleCount, shadowReportMismatchesInner.sampleCount) &&
        Objects.equals(this.firstSeenAt, shadowReportMismatchesInner.firstSeenAt) &&
        Objects.equals(this.probableCause, shadowReportMismatchesInner.probableCause) &&
        Objects.equals(this.recommendedAction, shadowReportMismatchesInner.recommendedAction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mismatchId, method, path, severity, differenceType, baselineResponse, shadowResponse, sampleCount, firstSeenAt, probableCause, recommendedAction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShadowReportMismatchesInner {\n");
    sb.append("    mismatchId: ").append(toIndentedString(mismatchId)).append("\n");
    sb.append("    method: ").append(toIndentedString(method)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    differenceType: ").append(toIndentedString(differenceType)).append("\n");
    sb.append("    baselineResponse: ").append(toIndentedString(baselineResponse)).append("\n");
    sb.append("    shadowResponse: ").append(toIndentedString(shadowResponse)).append("\n");
    sb.append("    sampleCount: ").append(toIndentedString(sampleCount)).append("\n");
    sb.append("    firstSeenAt: ").append(toIndentedString(firstSeenAt)).append("\n");
    sb.append("    probableCause: ").append(toIndentedString(probableCause)).append("\n");
    sb.append("    recommendedAction: ").append(toIndentedString(recommendedAction)).append("\n");
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


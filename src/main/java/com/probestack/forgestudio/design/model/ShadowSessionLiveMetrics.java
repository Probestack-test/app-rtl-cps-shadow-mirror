package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Real-time mirror statistics.
 */
@Schema(name = "ShadowSession_liveMetrics", description = "Real-time mirror statistics.")
@JsonTypeName("ShadowSession_liveMetrics")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T02:19:23.993884578Z[GMT]")public class ShadowSessionLiveMetrics {

  private Integer requestsMirrored;

  private Integer requestsMismatched;

  private Double mismatchRatePct;

  private Double shadowErrorRatePct;

  private Double baselineErrorRatePct;

  private Integer shadowP99LatencyMs;

  private Integer baselineP99LatencyMs;

  private Double latencyDeltaPct;

  public ShadowSessionLiveMetrics requestsMirrored(Integer requestsMirrored) {
    this.requestsMirrored = requestsMirrored;
    return this;
  }

  /**
   * Get requestsMirrored
   * @return requestsMirrored
  */
    @Schema(name = "requestsMirrored", example = "128450", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requestsMirrored")
  public Integer getRequestsMirrored() {
    return requestsMirrored;
  }

  public void setRequestsMirrored(Integer requestsMirrored) {
    this.requestsMirrored = requestsMirrored;
  }

  public ShadowSessionLiveMetrics requestsMismatched(Integer requestsMismatched) {
    this.requestsMismatched = requestsMismatched;
    return this;
  }

  /**
   * Get requestsMismatched
   * @return requestsMismatched
  */
    @Schema(name = "requestsMismatched", example = "342", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requestsMismatched")
  public Integer getRequestsMismatched() {
    return requestsMismatched;
  }

  public void setRequestsMismatched(Integer requestsMismatched) {
    this.requestsMismatched = requestsMismatched;
  }

  public ShadowSessionLiveMetrics mismatchRatePct(Double mismatchRatePct) {
    this.mismatchRatePct = mismatchRatePct;
    return this;
  }

  /**
   * Get mismatchRatePct
   * @return mismatchRatePct
  */
    @Schema(name = "mismatchRatePct", example = "0.27", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mismatchRatePct")
  public Double getMismatchRatePct() {
    return mismatchRatePct;
  }

  public void setMismatchRatePct(Double mismatchRatePct) {
    this.mismatchRatePct = mismatchRatePct;
  }

  public ShadowSessionLiveMetrics shadowErrorRatePct(Double shadowErrorRatePct) {
    this.shadowErrorRatePct = shadowErrorRatePct;
    return this;
  }

  /**
   * Get shadowErrorRatePct
   * @return shadowErrorRatePct
  */
    @Schema(name = "shadowErrorRatePct", example = "0.42", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("shadowErrorRatePct")
  public Double getShadowErrorRatePct() {
    return shadowErrorRatePct;
  }

  public void setShadowErrorRatePct(Double shadowErrorRatePct) {
    this.shadowErrorRatePct = shadowErrorRatePct;
  }

  public ShadowSessionLiveMetrics baselineErrorRatePct(Double baselineErrorRatePct) {
    this.baselineErrorRatePct = baselineErrorRatePct;
    return this;
  }

  /**
   * Get baselineErrorRatePct
   * @return baselineErrorRatePct
  */
    @Schema(name = "baselineErrorRatePct", example = "0.38", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("baselineErrorRatePct")
  public Double getBaselineErrorRatePct() {
    return baselineErrorRatePct;
  }

  public void setBaselineErrorRatePct(Double baselineErrorRatePct) {
    this.baselineErrorRatePct = baselineErrorRatePct;
  }

  public ShadowSessionLiveMetrics shadowP99LatencyMs(Integer shadowP99LatencyMs) {
    this.shadowP99LatencyMs = shadowP99LatencyMs;
    return this;
  }

  /**
   * Get shadowP99LatencyMs
   * @return shadowP99LatencyMs
  */
    @Schema(name = "shadowP99LatencyMs", example = "245", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("shadowP99LatencyMs")
  public Integer getShadowP99LatencyMs() {
    return shadowP99LatencyMs;
  }

  public void setShadowP99LatencyMs(Integer shadowP99LatencyMs) {
    this.shadowP99LatencyMs = shadowP99LatencyMs;
  }

  public ShadowSessionLiveMetrics baselineP99LatencyMs(Integer baselineP99LatencyMs) {
    this.baselineP99LatencyMs = baselineP99LatencyMs;
    return this;
  }

  /**
   * Get baselineP99LatencyMs
   * @return baselineP99LatencyMs
  */
    @Schema(name = "baselineP99LatencyMs", example = "230", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("baselineP99LatencyMs")
  public Integer getBaselineP99LatencyMs() {
    return baselineP99LatencyMs;
  }

  public void setBaselineP99LatencyMs(Integer baselineP99LatencyMs) {
    this.baselineP99LatencyMs = baselineP99LatencyMs;
  }

  public ShadowSessionLiveMetrics latencyDeltaPct(Double latencyDeltaPct) {
    this.latencyDeltaPct = latencyDeltaPct;
    return this;
  }

  /**
   * Get latencyDeltaPct
   * @return latencyDeltaPct
  */
    @Schema(name = "latencyDeltaPct", example = "6.5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("latencyDeltaPct")
  public Double getLatencyDeltaPct() {
    return latencyDeltaPct;
  }

  public void setLatencyDeltaPct(Double latencyDeltaPct) {
    this.latencyDeltaPct = latencyDeltaPct;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShadowSessionLiveMetrics shadowSessionLiveMetrics = (ShadowSessionLiveMetrics) o;
    return Objects.equals(this.requestsMirrored, shadowSessionLiveMetrics.requestsMirrored) &&
        Objects.equals(this.requestsMismatched, shadowSessionLiveMetrics.requestsMismatched) &&
        Objects.equals(this.mismatchRatePct, shadowSessionLiveMetrics.mismatchRatePct) &&
        Objects.equals(this.shadowErrorRatePct, shadowSessionLiveMetrics.shadowErrorRatePct) &&
        Objects.equals(this.baselineErrorRatePct, shadowSessionLiveMetrics.baselineErrorRatePct) &&
        Objects.equals(this.shadowP99LatencyMs, shadowSessionLiveMetrics.shadowP99LatencyMs) &&
        Objects.equals(this.baselineP99LatencyMs, shadowSessionLiveMetrics.baselineP99LatencyMs) &&
        Objects.equals(this.latencyDeltaPct, shadowSessionLiveMetrics.latencyDeltaPct);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestsMirrored, requestsMismatched, mismatchRatePct, shadowErrorRatePct, baselineErrorRatePct, shadowP99LatencyMs, baselineP99LatencyMs, latencyDeltaPct);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShadowSessionLiveMetrics {\n");
    sb.append("    requestsMirrored: ").append(toIndentedString(requestsMirrored)).append("\n");
    sb.append("    requestsMismatched: ").append(toIndentedString(requestsMismatched)).append("\n");
    sb.append("    mismatchRatePct: ").append(toIndentedString(mismatchRatePct)).append("\n");
    sb.append("    shadowErrorRatePct: ").append(toIndentedString(shadowErrorRatePct)).append("\n");
    sb.append("    baselineErrorRatePct: ").append(toIndentedString(baselineErrorRatePct)).append("\n");
    sb.append("    shadowP99LatencyMs: ").append(toIndentedString(shadowP99LatencyMs)).append("\n");
    sb.append("    baselineP99LatencyMs: ").append(toIndentedString(baselineP99LatencyMs)).append("\n");
    sb.append("    latencyDeltaPct: ").append(toIndentedString(latencyDeltaPct)).append("\n");
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


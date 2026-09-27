package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ShadowReportSummary
 */
@JsonTypeName("ShadowReport_summary")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T04:04:28.619669137Z[GMT]")public class ShadowReportSummary {

  private Integer totalRequestsMirrored;

  private Integer totalMismatches;

  private Double mismatchRatePct;

  private Integer uniqueEndpointsAffected;

  private Integer criticalMismatches;

  private Integer warningMismatches;

  private Integer infoMismatches;

  public ShadowReportSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ShadowReportSummary(Integer totalRequestsMirrored, Integer totalMismatches, Integer uniqueEndpointsAffected) {
    this.totalRequestsMirrored = totalRequestsMirrored;
    this.totalMismatches = totalMismatches;
    this.uniqueEndpointsAffected = uniqueEndpointsAffected;
  }

  public ShadowReportSummary totalRequestsMirrored(Integer totalRequestsMirrored) {
    this.totalRequestsMirrored = totalRequestsMirrored;
    return this;
  }

  /**
   * Get totalRequestsMirrored
   * @return totalRequestsMirrored
  */
  @NotNull   @Schema(name = "totalRequestsMirrored", example = "428500", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalRequestsMirrored")
  public Integer getTotalRequestsMirrored() {
    return totalRequestsMirrored;
  }

  public void setTotalRequestsMirrored(Integer totalRequestsMirrored) {
    this.totalRequestsMirrored = totalRequestsMirrored;
  }

  public ShadowReportSummary totalMismatches(Integer totalMismatches) {
    this.totalMismatches = totalMismatches;
    return this;
  }

  /**
   * Get totalMismatches
   * @return totalMismatches
  */
  @NotNull   @Schema(name = "totalMismatches", example = "1204", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalMismatches")
  public Integer getTotalMismatches() {
    return totalMismatches;
  }

  public void setTotalMismatches(Integer totalMismatches) {
    this.totalMismatches = totalMismatches;
  }

  public ShadowReportSummary mismatchRatePct(Double mismatchRatePct) {
    this.mismatchRatePct = mismatchRatePct;
    return this;
  }

  /**
   * Get mismatchRatePct
   * @return mismatchRatePct
  */
    @Schema(name = "mismatchRatePct", example = "0.28", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mismatchRatePct")
  public Double getMismatchRatePct() {
    return mismatchRatePct;
  }

  public void setMismatchRatePct(Double mismatchRatePct) {
    this.mismatchRatePct = mismatchRatePct;
  }

  public ShadowReportSummary uniqueEndpointsAffected(Integer uniqueEndpointsAffected) {
    this.uniqueEndpointsAffected = uniqueEndpointsAffected;
    return this;
  }

  /**
   * Get uniqueEndpointsAffected
   * @return uniqueEndpointsAffected
  */
  @NotNull   @Schema(name = "uniqueEndpointsAffected", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("uniqueEndpointsAffected")
  public Integer getUniqueEndpointsAffected() {
    return uniqueEndpointsAffected;
  }

  public void setUniqueEndpointsAffected(Integer uniqueEndpointsAffected) {
    this.uniqueEndpointsAffected = uniqueEndpointsAffected;
  }

  public ShadowReportSummary criticalMismatches(Integer criticalMismatches) {
    this.criticalMismatches = criticalMismatches;
    return this;
  }

  /**
   * Get criticalMismatches
   * @return criticalMismatches
  */
    @Schema(name = "criticalMismatches", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("criticalMismatches")
  public Integer getCriticalMismatches() {
    return criticalMismatches;
  }

  public void setCriticalMismatches(Integer criticalMismatches) {
    this.criticalMismatches = criticalMismatches;
  }

  public ShadowReportSummary warningMismatches(Integer warningMismatches) {
    this.warningMismatches = warningMismatches;
    return this;
  }

  /**
   * Get warningMismatches
   * @return warningMismatches
  */
    @Schema(name = "warningMismatches", example = "42", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("warningMismatches")
  public Integer getWarningMismatches() {
    return warningMismatches;
  }

  public void setWarningMismatches(Integer warningMismatches) {
    this.warningMismatches = warningMismatches;
  }

  public ShadowReportSummary infoMismatches(Integer infoMismatches) {
    this.infoMismatches = infoMismatches;
    return this;
  }

  /**
   * Get infoMismatches
   * @return infoMismatches
  */
    @Schema(name = "infoMismatches", example = "1159", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("infoMismatches")
  public Integer getInfoMismatches() {
    return infoMismatches;
  }

  public void setInfoMismatches(Integer infoMismatches) {
    this.infoMismatches = infoMismatches;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShadowReportSummary shadowReportSummary = (ShadowReportSummary) o;
    return Objects.equals(this.totalRequestsMirrored, shadowReportSummary.totalRequestsMirrored) &&
        Objects.equals(this.totalMismatches, shadowReportSummary.totalMismatches) &&
        Objects.equals(this.mismatchRatePct, shadowReportSummary.mismatchRatePct) &&
        Objects.equals(this.uniqueEndpointsAffected, shadowReportSummary.uniqueEndpointsAffected) &&
        Objects.equals(this.criticalMismatches, shadowReportSummary.criticalMismatches) &&
        Objects.equals(this.warningMismatches, shadowReportSummary.warningMismatches) &&
        Objects.equals(this.infoMismatches, shadowReportSummary.infoMismatches);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalRequestsMirrored, totalMismatches, mismatchRatePct, uniqueEndpointsAffected, criticalMismatches, warningMismatches, infoMismatches);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShadowReportSummary {\n");
    sb.append("    totalRequestsMirrored: ").append(toIndentedString(totalRequestsMirrored)).append("\n");
    sb.append("    totalMismatches: ").append(toIndentedString(totalMismatches)).append("\n");
    sb.append("    mismatchRatePct: ").append(toIndentedString(mismatchRatePct)).append("\n");
    sb.append("    uniqueEndpointsAffected: ").append(toIndentedString(uniqueEndpointsAffected)).append("\n");
    sb.append("    criticalMismatches: ").append(toIndentedString(criticalMismatches)).append("\n");
    sb.append("    warningMismatches: ").append(toIndentedString(warningMismatches)).append("\n");
    sb.append("    infoMismatches: ").append(toIndentedString(infoMismatches)).append("\n");
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


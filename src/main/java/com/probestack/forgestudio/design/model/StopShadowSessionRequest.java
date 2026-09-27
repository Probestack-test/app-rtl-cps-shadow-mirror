package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StopShadowSessionRequest
 */
@JsonTypeName("stopShadowSession_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T04:04:28.619669137Z[GMT]")public class StopShadowSessionRequest {

  private String reason;

  private Boolean generateReport = true;

  public StopShadowSessionRequest reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Optional reason for stopping (for audit).
   * @return reason
  */
    @Schema(name = "reason", example = "Sufficient samples collected; ready to review report.", description = "Optional reason for stopping (for audit).", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reason")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public StopShadowSessionRequest generateReport(Boolean generateReport) {
    this.generateReport = generateReport;
    return this;
  }

  /**
   * Whether to generate a final report.
   * @return generateReport
  */
    @Schema(name = "generateReport", example = "true", description = "Whether to generate a final report.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("generateReport")
  public Boolean getGenerateReport() {
    return generateReport;
  }

  public void setGenerateReport(Boolean generateReport) {
    this.generateReport = generateReport;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StopShadowSessionRequest stopShadowSessionRequest = (StopShadowSessionRequest) o;
    return Objects.equals(this.reason, stopShadowSessionRequest.reason) &&
        Objects.equals(this.generateReport, stopShadowSessionRequest.generateReport);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reason, generateReport);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StopShadowSessionRequest {\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    generateReport: ").append(toIndentedString(generateReport)).append("\n");
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


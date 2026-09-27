package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ShadowReportMismatchesInnerBaselineResponse
 */
@JsonTypeName("ShadowReport_mismatches_inner_baselineResponse")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T04:04:28.619669137Z[GMT]")public class ShadowReportMismatchesInnerBaselineResponse {

  private Integer statusCode;

  private String bodyPreview;

  public ShadowReportMismatchesInnerBaselineResponse statusCode(Integer statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Get statusCode
   * @return statusCode
  */
    @Schema(name = "statusCode", example = "200", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statusCode")
  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public ShadowReportMismatchesInnerBaselineResponse bodyPreview(String bodyPreview) {
    this.bodyPreview = bodyPreview;
    return this;
  }

  /**
   * Get bodyPreview
   * @return bodyPreview
  */
    @Schema(name = "bodyPreview", example = "{\"status\":\"approved\",\"txnId\":\"abc123\"}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bodyPreview")
  public String getBodyPreview() {
    return bodyPreview;
  }

  public void setBodyPreview(String bodyPreview) {
    this.bodyPreview = bodyPreview;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShadowReportMismatchesInnerBaselineResponse shadowReportMismatchesInnerBaselineResponse = (ShadowReportMismatchesInnerBaselineResponse) o;
    return Objects.equals(this.statusCode, shadowReportMismatchesInnerBaselineResponse.statusCode) &&
        Objects.equals(this.bodyPreview, shadowReportMismatchesInnerBaselineResponse.bodyPreview);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, bodyPreview);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShadowReportMismatchesInnerBaselineResponse {\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    bodyPreview: ").append(toIndentedString(bodyPreview)).append("\n");
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


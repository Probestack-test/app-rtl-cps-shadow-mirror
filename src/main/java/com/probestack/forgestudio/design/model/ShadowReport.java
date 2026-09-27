package com.probestack.forgestudio.design.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.probestack.forgestudio.design.model.ShadowReportMismatchesInner;
import com.probestack.forgestudio.design.model.ShadowReportSummary;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ShadowReport
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T04:04:28.619669137Z[GMT]")public class ShadowReport {

  private UUID shadowId;

  /**
   * Final recommendation.
   */
  public enum VerdictEnum {
    SAFE_TO_PROCEED("SAFE_TO_PROCEED"),
    
    REVIEW_NEEDED("REVIEW_NEEDED"),
    
    DO_NOT_PROCEED("DO_NOT_PROCEED");

    private String value;

    VerdictEnum(String value) {
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
    public static VerdictEnum fromValue(String value) {
      for (VerdictEnum b : VerdictEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
  private VerdictEnum verdict;

  private BigDecimal confidence;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime generatedAt;

  private ShadowReportSummary summary;

  @Valid
  private List<@Valid ShadowReportMismatchesInner> mismatches = new ArrayList<>();

  @Valid
  private List<String> regressionRisks;

  public ShadowReport() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ShadowReport(UUID shadowId, VerdictEnum verdict, OffsetDateTime generatedAt, ShadowReportSummary summary, List<@Valid ShadowReportMismatchesInner> mismatches) {
    this.shadowId = shadowId;
    this.verdict = verdict;
    this.generatedAt = generatedAt;
    this.summary = summary;
    this.mismatches = mismatches;
  }

  public ShadowReport shadowId(UUID shadowId) {
    this.shadowId = shadowId;
    return this;
  }

  /**
   * Get shadowId
   * @return shadowId
  */
  @NotNull @Valid   @Schema(name = "shadowId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shadowId")
  public UUID getShadowId() {
    return shadowId;
  }

  public void setShadowId(UUID shadowId) {
    this.shadowId = shadowId;
  }

  public ShadowReport verdict(VerdictEnum verdict) {
    this.verdict = verdict;
    return this;
  }

  /**
   * Final recommendation.
   * @return verdict
  */
  @NotNull   @Schema(name = "verdict", example = "REVIEW_NEEDED", description = "Final recommendation.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("verdict")
  public VerdictEnum getVerdict() {
    return verdict;
  }

  public void setVerdict(VerdictEnum verdict) {
    this.verdict = verdict;
  }

  public ShadowReport confidence(BigDecimal confidence) {
    this.confidence = confidence;
    return this;
  }

  /**
   * Get confidence
   * minimum: 0
   * maximum: 1
   * @return confidence
  */
  @Valid @DecimalMin("0") @DecimalMax("1")   @Schema(name = "confidence", example = "0.89", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("confidence")
  public BigDecimal getConfidence() {
    return confidence;
  }

  public void setConfidence(BigDecimal confidence) {
    this.confidence = confidence;
  }

  public ShadowReport generatedAt(OffsetDateTime generatedAt) {
    this.generatedAt = generatedAt;
    return this;
  }

  /**
   * Get generatedAt
   * @return generatedAt
  */
  @NotNull @Valid   @Schema(name = "generatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("generatedAt")
  public OffsetDateTime getGeneratedAt() {
    return generatedAt;
  }

  public void setGeneratedAt(OffsetDateTime generatedAt) {
    this.generatedAt = generatedAt;
  }

  public ShadowReport summary(ShadowReportSummary summary) {
    this.summary = summary;
    return this;
  }

  /**
   * Get summary
   * @return summary
  */
  @NotNull @Valid   @Schema(name = "summary", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("summary")
  public ShadowReportSummary getSummary() {
    return summary;
  }

  public void setSummary(ShadowReportSummary summary) {
    this.summary = summary;
  }

  public ShadowReport mismatches(List<@Valid ShadowReportMismatchesInner> mismatches) {
    this.mismatches = mismatches;
    return this;
  }

  public ShadowReport addMismatchesItem(ShadowReportMismatchesInner mismatchesItem) {
    if (this.mismatches == null) {
      this.mismatches = new ArrayList<>();
    }
    this.mismatches.add(mismatchesItem);
    return this;
  }

  /**
   * Detailed mismatch list, sorted by severity.
   * @return mismatches
  */
  @NotNull @Valid   @Schema(name = "mismatches", description = "Detailed mismatch list, sorted by severity.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mismatches")
  public List<@Valid ShadowReportMismatchesInner> getMismatches() {
    return mismatches;
  }

  public void setMismatches(List<@Valid ShadowReportMismatchesInner> mismatches) {
    this.mismatches = mismatches;
  }

  public ShadowReport regressionRisks(List<String> regressionRisks) {
    this.regressionRisks = regressionRisks;
    return this;
  }

  public ShadowReport addRegressionRisksItem(String regressionRisksItem) {
    if (this.regressionRisks == null) {
      this.regressionRisks = new ArrayList<>();
    }
    this.regressionRisks.add(regressionRisksItem);
    return this;
  }

  /**
   * Potential regressions to fix before promotion.
   * @return regressionRisks
  */
    @Schema(name = "regressionRisks", example = "[\"Discount calculation returns NPE for 42 requests without campaignId.\",\"Response payload for /refund missing 'refundId' field on 12% of samples.\"]", description = "Potential regressions to fix before promotion.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("regressionRisks")
  public List<String> getRegressionRisks() {
    return regressionRisks;
  }

  public void setRegressionRisks(List<String> regressionRisks) {
    this.regressionRisks = regressionRisks;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShadowReport shadowReport = (ShadowReport) o;
    return Objects.equals(this.shadowId, shadowReport.shadowId) &&
        Objects.equals(this.verdict, shadowReport.verdict) &&
        Objects.equals(this.confidence, shadowReport.confidence) &&
        Objects.equals(this.generatedAt, shadowReport.generatedAt) &&
        Objects.equals(this.summary, shadowReport.summary) &&
        Objects.equals(this.mismatches, shadowReport.mismatches) &&
        Objects.equals(this.regressionRisks, shadowReport.regressionRisks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shadowId, verdict, confidence, generatedAt, summary, mismatches, regressionRisks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShadowReport {\n");
    sb.append("    shadowId: ").append(toIndentedString(shadowId)).append("\n");
    sb.append("    verdict: ").append(toIndentedString(verdict)).append("\n");
    sb.append("    confidence: ").append(toIndentedString(confidence)).append("\n");
    sb.append("    generatedAt: ").append(toIndentedString(generatedAt)).append("\n");
    sb.append("    summary: ").append(toIndentedString(summary)).append("\n");
    sb.append("    mismatches: ").append(toIndentedString(mismatches)).append("\n");
    sb.append("    regressionRisks: ").append(toIndentedString(regressionRisks)).append("\n");
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


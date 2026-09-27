package com.probestack.forgestudio.design.api;

import com.probestack.forgestudio.design.model.ShadowReport;
import com.probestack.forgestudio.design.model.ShadowSession;
import com.probestack.forgestudio.design.model.StartShadowSessionRequest;
import com.probestack.forgestudio.design.model.StopShadowSessionRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.constraints.*;
import jakarta.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.probestack.forgestudio.design.service.ShadowsService;
import com.probestack.forgestudio.design.validation.GeneratedRequestValidator;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T04:04:28.619669137Z[GMT]")
@Controller
@RequestMapping("${openapi.shadowMirror.base-path:/v1}")
public class ShadowsApiController implements ShadowsApi {

    private static final Logger log = LoggerFactory.getLogger(ShadowsApiController.class);

    private final ShadowsService shadowsService;

    private final GeneratedRequestValidator generatedRequestValidator;

    @Autowired()
    public ShadowsApiController(ShadowsService shadowsService, GeneratedRequestValidator generatedRequestValidator) {
        this.shadowsService = shadowsService;
        this.generatedRequestValidator = generatedRequestValidator;
    }

    @Override()
    public ResponseEntity<ShadowReport> getShadowReport(@PathVariable() UUID shadowId, @RequestParam(value = "onlyMismatches", required = false, defaultValue = "true") Boolean onlyMismatches, @RequestParam(value = "minSeverity", required = false, defaultValue = "INFO") String minSeverity) {
        log.info("Processing getShadowReport request");
        try {
            var response = shadowsService.getShadowReport(shadowId, onlyMismatches, minSeverity);
            log.info("getShadowReport completed successfully");
            return response;
        } catch (Exception e) {
            log.error("Failed to process getShadowReport: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override()
    public ResponseEntity<ShadowSession> getShadowStatus(@PathVariable() UUID shadowId) {
        log.info("Processing getShadowStatus request");
        try {
            var response = shadowsService.getShadowStatus(shadowId);
            log.info("getShadowStatus completed successfully");
            return response;
        } catch (Exception e) {
            log.error("Failed to process getShadowStatus: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override()
    public ResponseEntity<ShadowSession> startShadowSession(@RequestBody() StartShadowSessionRequest startShadowSessionRequest) {
        log.info("Processing startShadowSession request");
        try {
            generatedRequestValidator.validate("startShadowSession", startShadowSessionRequest);
            var response = shadowsService.startShadowSession(startShadowSessionRequest);
            log.info("startShadowSession completed successfully");
            return response;
        } catch (Exception e) {
            log.error("Failed to process startShadowSession: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override()
    public ResponseEntity<ShadowSession> stopShadowSession(@PathVariable() UUID shadowId, @RequestBody() StopShadowSessionRequest stopShadowSessionRequest) {
        log.info("Processing stopShadowSession request");
        try {
            generatedRequestValidator.validate("stopShadowSession", stopShadowSessionRequest);
            var response = shadowsService.stopShadowSession(shadowId, stopShadowSessionRequest);
            log.info("stopShadowSession completed successfully");
            return response;
        } catch (Exception e) {
            log.error("Failed to process stopShadowSession: {}", e.getMessage(), e);
            throw e;
        }
    }
}

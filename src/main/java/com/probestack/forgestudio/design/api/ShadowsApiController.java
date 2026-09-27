package com.probestack.forgestudio.design.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.constraints.*;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-27T01:32:49.939550536Z[GMT]")@Controller
@RequestMapping("${openapi.shadowMirror.base-path:/v1}")
public class ShadowsApiController implements ShadowsApi {

    private final NativeWebRequest request;

    @Autowired
    public ShadowsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}

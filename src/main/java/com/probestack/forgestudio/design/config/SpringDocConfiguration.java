package com.probestack.forgestudio.design.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "com.probestack.forgestudio.design.config.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Shadow Mirror")
                                .description("Production traffic shadowing platform for the ForgeSphere ecosystem. Mirrors real user requests to a shadow deployment (new version) while  real responses continue to come from the stable version. Zero user impact,  real-world validation.  Shadow Mirror captures every request/response pair, compares the shadow  responses against production, and reports behavioral differences,  performance deltas, and regressions BEFORE the new version ever  receives real traffic. ")
                                .contact(
                                        new Contact()
                                                .name("ForgeSphere Deployment Reliability")
                                                .email("shadow@forgesphere.example.com")
                                )
                                .version("1.0.0")
                )
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                )
                )
        ;
    }
}

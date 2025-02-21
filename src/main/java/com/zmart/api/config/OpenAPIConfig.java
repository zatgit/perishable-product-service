package com.zmart.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public OpenApiCustomizer customOpenApi() {
        return openApi -> {
            Info info = openApi.getInfo();
            if (info != null) {
                info.setVersion(appVersion);
            }
        };
    }
}
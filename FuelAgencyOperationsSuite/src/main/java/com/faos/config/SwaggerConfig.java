package com.faos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Fuel Agency Operations Suite API",
                version = "1.0",
                description = "API documentation for Fuel Agency Management System"
        )
)
public class SwaggerConfig {

}
// http://localhost:8080/swagger-ui/index.html


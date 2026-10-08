package com.king.deliveryking.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger UI: http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

    private static final String JWT_SCHEME = "JWT";

    @Bean
    public OpenAPI openAPI() {
        // Authorize 버튼에 토큰만 입력하면 "Bearer "를 붙여 Authorization 헤더로 전송
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info().title("Delivery King API").version("v1"))
                .components(new Components().addSecuritySchemes(JWT_SCHEME, bearerScheme))
                .addSecurityItem(new SecurityRequirement().addList(JWT_SCHEME));
    }
}

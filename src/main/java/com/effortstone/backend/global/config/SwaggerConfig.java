package com.effortstone.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 보안 스키마 이름을 FirebaseIdToken으로 지정합니다.
        String firebaseIdToken = "FirebaseIdToken";

        // Swagger UI에서 "Authorize" 버튼 클릭 시 Firebase ID 토큰을 입력하도록 함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(firebaseIdToken);

        // 보안 스키마를 정의합니다.
        Components components = new Components().addSecuritySchemes(firebaseIdToken,
                new SecurityScheme()
                        .name(firebaseIdToken)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("Firebase")   // Firebase 토큰임을 나타내기 위해 "Firebase"로 지정 (원래 JWT 형식)
        );

        return new OpenAPI()
                .info(apiInfo())
                .components(components)
                .addSecurityItem(securityRequirement);
    }

    private Info apiInfo() {
        return new Info()
                .title("Effort-Stone API 명세서")
                .description("Springdoc을 사용한 Effort-Stone Swagger Docs")
                .version("1.0.0");
    }
}

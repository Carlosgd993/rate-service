package com.inditex.rate.rate_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Gestión de Tarifas")
                        .version("1.0.0")
                        .description("API para gestionar tarifas de productos")
                        .contact(new Contact()
                                .name("Carlos")
                                .email("Carlosgd993@gmail.com")));
    }
}
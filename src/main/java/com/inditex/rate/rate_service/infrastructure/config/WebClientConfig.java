package com.inditex.rate.rate_service.infrastructure.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${currency.api.url:http://localhost:4010/v1/currencies}")
    private String apiUrl;

    @Value("${currency.api.username:indiTest}")
    private String username;

    @Value("${currency.api.password:indiTest123}")
    private String password;

    @Bean
    public WebClient webClient() {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Basic " + encodedAuth)
                .build();
    }
}

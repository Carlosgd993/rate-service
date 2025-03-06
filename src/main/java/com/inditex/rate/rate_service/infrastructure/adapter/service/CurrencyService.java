package com.inditex.rate.rate_service.infrastructure.adapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.inditex.rate.rate_service.domain.model.Currency;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyService {

    private final WebClient webClient;

    final static Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public CurrencyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Currency> getCurrencies() {
        return webClient.get()
                .uri("")
                .retrieve()
                .bodyToFlux(Currency.class)
                .onErrorResume(e -> {
                    logger.error("Error fetching currencies: " + e.getMessage());
                    return Flux.empty();
                });
    }

    public Mono<Currency> getCurrencyByCode(String currencyCode) {
        return webClient.get()
                .uri("/{currencyCode}", currencyCode)
                .retrieve()
                .bodyToMono(Currency.class)
                .onErrorResume(e -> {
                    logger.error("Error fetching currency " + currencyCode + ": " + e.getMessage());
                    return Mono.empty();
                });
    }
}

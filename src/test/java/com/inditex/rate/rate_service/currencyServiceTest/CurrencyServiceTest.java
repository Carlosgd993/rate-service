package com.inditex.rate.rate_service.currencyServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.inditex.rate.rate_service.domain.model.Currency;
import com.inditex.rate.rate_service.infrastructure.adapter.service.CurrencyService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    public void testGetCurrencies() {
        Currency currency1 = new Currency("$", "USD", 2);
        Currency currency2 = new Currency("€", "EUR", 2);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(Currency.class))
                .thenReturn(Flux.just(currency1, currency2));

        Flux<Currency> currencies = currencyService.getCurrencies();

        assertEquals(2, currencies.collectList().block().size());
    }

    @Test
    public void testGetCurrencyByCode() {
        Currency currency = new Currency("€", "EUR", 2);

        when(webClient.get())
                .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/{currencyCode}", "USD"))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
                .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Currency.class))
                .thenReturn(Mono.just(currency));

        Mono<Currency> result = currencyService.getCurrencyByCode("USD");

        assertEquals("EUR", result.block().code());
        assertEquals("€", result.block().symbol());
    }

}

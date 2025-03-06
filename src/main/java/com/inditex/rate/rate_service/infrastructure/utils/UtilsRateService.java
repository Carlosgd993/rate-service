package com.inditex.rate.rate_service.infrastructure.utils;

import java.time.Duration;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inditex.rate.rate_service.application.dto.RateRequestDto;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.domain.model.Currency;
import com.inditex.rate.rate_service.domain.model.Rate;
import com.inditex.rate.rate_service.infrastructure.adapter.service.CurrencyService;

import reactor.core.scheduler.Schedulers;

public class UtilsRateService {

    private static final Logger logger = LoggerFactory.getLogger(UtilsRateService.class);

    public static RateResponseDto convertToFormatDto(Rate rate, CurrencyService currencyService) {
        return new RateResponseDto(
                rate.getId(),
                rate.getBrandId(),
                rate.getProductId(),
                rate.getStartDate(),
                rate.getEndDate(),
                getFormatPrice(rate.getPrice(), rate.getCurrencyCode(), currencyService));
    }

    public static String getFormatPrice(Long price, String currencyCode, CurrencyService currencyService) {
        if (price == null || currencyCode == null) {
            return "";
        }

        Currency currency = getCurrencyByCurrencyCode(currencyCode, currencyService);
        if (currency == null) {
            String warmMessage = String.format("Error al obtener información sobre la moneda( %s ) "
                    + " se devuelve precio sin formato: %s", currencyCode, price);
            logger.warn(warmMessage);
            return price.toString();
        }

        double priceWithDecimals = price / Math.pow(10, currency.decimals());
        return priceWithDecimals + currency.symbol();
    }

    public static Rate createRateFromRequest(RateRequestDto rate) {
        return new Rate(
                rate.brandId(),
                rate.productId(),
                LocalDate.parse(rate.startDate()),
                LocalDate.parse(rate.endDate()),
                rate.price(),
                rate.currencyCode().toUpperCase());
    }

    private static Currency getCurrencyByCurrencyCode(String currencyCode, CurrencyService currencyService) {
        return currencyService.getCurrencyByCode(currencyCode)
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(Duration.ofSeconds(5))
                .block();
    }
}
package com.inditex.rate.rate_service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.inditex.rate.rate_service.application.dto.RateRequestDto;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.domain.model.Rate;

public class UtilsRateTest {
    public static Rate createRateTest() {
        return new Rate(
                1L,
                1L,
                1L,
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                10000L,
                "EUR");

    }

    public static RateResponseDto createRateResponseDto() {
        return new RateResponseDto(
                1L,
                1L,
                1L,
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                "100.00€");
    }

    public static RateRequestDto createRateRequestDto() {
        return new RateRequestDto(
                1L,
                1L,
                LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.now().plusMonths(6).format(DateTimeFormatter.ISO_LOCAL_DATE),
                100L,
                "EUR");
    }
}

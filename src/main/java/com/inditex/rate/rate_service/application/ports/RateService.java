package com.inditex.rate.rate_service.application.ports;

import java.time.LocalDate;
import java.util.Optional;

import com.inditex.rate.rate_service.application.dto.RateRequestDto;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;

public interface RateService {

    Long createRate(RateRequestDto rate);

    Optional<RateResponseDto> findRateById(Long id);

    void patchRatePrice(Long id, Long price, String currencyCode);

    void deleteRate(Long id);

    Optional<RateResponseDto> findRateByDateAndProductIdAndBrandId(LocalDate date, Long productId, Long brandId);

}

package com.inditex.rate.rate_service.infrastructure.adapter.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.inditex.rate.rate_service.application.dto.RateRequestDto;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.application.ports.RateRepositoryJpa;
import com.inditex.rate.rate_service.application.ports.RateService;
import com.inditex.rate.rate_service.domain.model.Rate;
import com.inditex.rate.rate_service.infrastructure.utils.UtilsRateService;

import jakarta.transaction.Transactional;

@Service
public class RateServiceImpl implements RateService {

    private final RateRepositoryJpa rateRepositoryJpa;
    private final CurrencyService currencyService;

    final static Logger logger = LoggerFactory.getLogger(RateServiceImpl.class);

    public RateServiceImpl(RateRepositoryJpa rateRepositoryJpa, CurrencyService currencyService) {
        this.rateRepositoryJpa = rateRepositoryJpa;
        this.currencyService = currencyService;
    }

    @Override
    @Transactional
    public Long createRate(RateRequestDto rate) {
        Rate newRate = UtilsRateService.createRateFromRequest(rate);
        return rateRepositoryJpa.save(newRate).getId();
    }

    @Override
    public Optional<RateResponseDto> findRateById(Long id) {
        return rateRepositoryJpa.findById(id)
                .map(rate -> UtilsRateService.convertToFormatDto(rate, currencyService));
    }

    @Override
    @Transactional
    public void patchRatePrice(Long id, Long price, String currencyCode) {

        Rate rateToUpdate = rateRepositoryJpa.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarifa con id " + id + " no encontrada"));

        rateToUpdate.setPrice(price);
        rateToUpdate.setCurrencyCode(currencyCode);

        rateRepositoryJpa.save(rateToUpdate);
    }

    @Override
    @Transactional
    public void deleteRate(Long id) {
        if (!rateRepositoryJpa.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Tarifa con id " + id + " no encontrada");
        }
        rateRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<RateResponseDto> findRateByDateAndProductIdAndBrandId(LocalDate date, Long productId,
            Long brandId) {

        return rateRepositoryJpa
                .findRateByDateAndProductIdAndBrandId(date, productId, brandId)
                .map(rate -> UtilsRateService.convertToFormatDto(rate, currencyService));

    }
}

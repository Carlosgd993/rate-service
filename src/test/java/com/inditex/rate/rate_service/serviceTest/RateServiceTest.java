package com.inditex.rate.rate_service.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inditex.rate.rate_service.UtilsRateTest;
import com.inditex.rate.rate_service.application.dto.RateRequestDto;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.application.ports.RateRepositoryJpa;
import com.inditex.rate.rate_service.domain.model.Currency;
import com.inditex.rate.rate_service.domain.model.Rate;
import com.inditex.rate.rate_service.infrastructure.adapter.service.CurrencyService;
import com.inditex.rate.rate_service.infrastructure.adapter.service.RateServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

        @InjectMocks
        private RateServiceImpl rateService;

        @Mock
        private RateRepositoryJpa rateRepository;

        @Mock
        private CurrencyService currencyService;

        @Test
        void rateServiceTest_FindRateById() {
                Long rateId = 1L;
                Rate rate = UtilsRateTest.createRateTest();
                Currency currency = new Currency("€", "EUR", 2);

                when(rateRepository.findById(rateId))
                                .thenReturn(Optional.of(rate));
                when(currencyService.getCurrencyByCode(rate.getCurrencyCode()))
                                .thenReturn(Mono.just(currency));

                Optional<RateResponseDto> serviceResponse = rateService.findRateById(rateId);

                assertTrue(serviceResponse.isPresent());

                RateResponseDto rateResponse = serviceResponse.get();
                assertEquals(rate.getId(), rateResponse.id());
                assertEquals(rate.getBrandId(), rateResponse.brandId());
                assertEquals(rate.getProductId(), rateResponse.productId());
                assertEquals(rate.getStartDate(), rateResponse.startDate());
                assertEquals(rate.getEndDate(), rateResponse.endDate());
                assertEquals("100.0€", rateResponse.price());

                verify(rateRepository, times(1))
                                .findById(rateId);
        }

        @Test
        void rateServiceTest_CreateRate() {
                RateRequestDto rateRequest = UtilsRateTest.createRateRequestDto();
                Rate newRate = UtilsRateTest.createRateTest();

                when(rateRepository.save(any(Rate.class)))
                                .thenReturn(newRate);

                Long rateId = rateService.createRate(rateRequest);

                assertNotNull(rateId);
                assertEquals(newRate.getId(), rateId);

                verify(rateRepository, times(1))
                                .save(any(Rate.class));
        }

        @Test
        void rateServiceTest_PatchRatePrice() {
                Long rateId = 1L;
                Long newPrice = 200L;
                String currencyCode = "EUR";
                Rate rateToUpdate = UtilsRateTest.createRateTest();

                when(rateRepository.findById(rateId))
                                .thenReturn(Optional.of(rateToUpdate));
                when(rateRepository.save(any(Rate.class)))
                                .thenReturn(rateToUpdate);

                rateService.patchRatePrice(rateId, newPrice, currencyCode);

                assertEquals(rateToUpdate.getPrice(), newPrice);
                verify(rateRepository, times(1))
                                .findById(rateId);
                verify(rateRepository, times(1))
                                .save(any(Rate.class));
        }

        @Test
        void rateServiceTest_DeleteRate() {
                Long rateId = 1L;

                when(rateRepository.existsById(rateId))
                                .thenReturn(true);

                rateService.deleteRate(rateId);

                verify(rateRepository, times(1))
                                .deleteById(rateId);
        }

        @Test
        void rateServiceTest_FindRateByDateAndProductIdAndBrandId() {
                LocalDate date = LocalDate.now();
                Long productId = 1L;
                Long brandId = 1L;
                Rate rate = UtilsRateTest.createRateTest();

                when(rateRepository.findRateByDateAndProductIdAndBrandId(date, productId, brandId))
                                .thenReturn(Optional.of(rate));

                when(currencyService.getCurrencyByCode(any()))
                                .thenReturn(Mono.just(new Currency("EUR", "€", 2)));

                Optional<RateResponseDto> rateFound = rateService
                                .findRateByDateAndProductIdAndBrandId(date, productId, brandId);

                assertTrue(rateFound.isPresent());
                assertEquals(productId, rateFound.get().productId());
                assertEquals(brandId, rateFound.get().brandId());

                verify(rateRepository, times(1))
                                .findRateByDateAndProductIdAndBrandId(date, productId, brandId);
                verify(currencyService, times(1))
                                .getCurrencyByCode(any());
        }
}

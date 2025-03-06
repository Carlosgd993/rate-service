package com.inditex.rate.rate_service.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.inditex.rate.rate_service.UtilsRateTest;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.application.ports.RateService;
import com.inditex.rate.rate_service.infrastructure.adapter.api.RateController;

@ExtendWith(MockitoExtension.class)
public class RateControllerUnitTest {

    @Mock
    private RateService rateService;

    @InjectMocks
    private RateController rateController;

    @Test
    void testRate_WhenRateIdHasResults() {
        Long validRateId = 1L;
        RateResponseDto rateResponseDto = UtilsRateTest.createRateResponseDto();

        when(rateService.findRateById(validRateId))
                .thenReturn(Optional.of(rateResponseDto));

        ResponseEntity<?> result = rateController.findRateById(validRateId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(rateResponseDto, result.getBody());

        verify(rateService, times(1))
                .findRateById(validRateId);
    }

    @Test
    void testRate_WhenRateIdHasNoResults() {
        Long invalidRateId = 1L;

        when(rateService.findRateById(invalidRateId))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> rateController.findRateById(invalidRateId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Tarifa con id " + invalidRateId + " no encontrada", exception.getReason());

        verify(rateService, times(1))
                .findRateById(invalidRateId);
    }

}

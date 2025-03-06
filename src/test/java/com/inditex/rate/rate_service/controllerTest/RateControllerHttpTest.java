package com.inditex.rate.rate_service.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.inditex.rate.rate_service.UtilsRateTest;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.application.ports.RateService;
import com.inditex.rate.rate_service.infrastructure.adapter.api.RateController;

@WebMvcTest(RateController.class)
public class RateControllerHttpTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RateService rateService;

    private static final String API = "/api/v1/rates";

    @Test
    void test_getRateById_WhenRateIdIsValid() throws Exception {
        Long validRateId = 1L;
        RateResponseDto expectedRateDto = UtilsRateTest.createRateResponseDto();

        when(rateService.findRateById(validRateId))
                .thenReturn(Optional.of(expectedRateDto));

        // When & Then
        mockMvc.perform(get(API + "/{rateId}", validRateId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validRateId))
                .andExpect(jsonPath("$.brandId").value(1L))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.startDate").value(expectedRateDto.startDate().toString()))
                .andExpect(jsonPath("$.endDate").value(expectedRateDto.endDate().toString()))
                .andExpect(jsonPath("$.price").value("100.00€"));
    }

    @Test
    void test_getRateById_WhenRateIdNotFound() throws Exception {
        Long invalidRateId = 1L;

        when(rateService.findRateById(invalidRateId))
                .thenReturn(Optional.empty());

        mockMvc.perform(get(API + "/{rateId}", invalidRateId))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_getRateById_WhenRateIsNull() throws Exception {
        Long invalidRateId = null;

        mockMvc.perform(get(API + "/{rateId}", invalidRateId))
                .andExpect(status().isNotFound());

    }
}

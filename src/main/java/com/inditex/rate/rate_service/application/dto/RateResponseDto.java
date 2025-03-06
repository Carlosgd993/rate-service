package com.inditex.rate.rate_service.application.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RateResponse", description = "DTO de respuesta de una tarifa", example = """
                {
                  "id": 1,
                  "brandId": 1,
                  "productId": 35455,
                  "startDate": "2024-01-01",
                  "endDate": "2024-05-31",
                  "price": "35.50€"
                }
                """)
public record RateResponseDto(
  @Schema(
    description = "Identificador único de la tarifa", 
    example = "1", 
    required = true) 
  Long id,

  @Schema(
    description = "Identificador de la marca", 
    example = "1", 
    required = true) 
  Long brandId,

  @Schema(
    description = "Identificador del producto", 
    example = "35455", 
    required = true) 
  Long productId,

  @Schema(
    description = "Fecha de inicio de la tarifa", 
    example = "2020-01-01", 
    required = true) 
  LocalDate startDate,

  @Schema(
    description = "Fecha de fin de la tarifa", 
    example = "2020-05-31", 
    required = true) 
  LocalDate endDate,

  @Schema(
    description = "Precio formateado", 
    example = "35.50€", 
    required = true) 
  String price) {
}
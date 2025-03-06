package com.inditex.rate.rate_service.application.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "RateRequest", description = "DTO para crear una tarifa", example = """
        {
          "brandId": 1,
          "productId": 35455,
          "startDate": "2024-01-01",
          "endDate": "2024-05-31",
          "price": 3550,
          "currencyCode": "EUR"
        }
        """)
public record RateRequestDto(
        @Schema(
            description = "Identificador único de la marca", 
            example = "1", required = true) 
        @NotNull(message = "El campo brandId es obligatorio") 
        @Min(value = 1, message = "brandId debe ser mayor que 0") 
        Long brandId,

        @Schema(
            description = "Identificador único del producto",
            example = "1", required = true) 
        @NotNull(message = "El campo productId es obligatorio") 
        Long productId,

        @Schema(
            description = "Fecha de inicio de aplicación de la tarifa", 
            example = "2020-01-01", required = true) 
            @NotBlank(message = "Fecha de aplicación de la tarifa") 
            @Pattern(
                regexp = "\\d{4}-\\d{2}-\\d{2}", 
                message = "startDate debe tener el formato ISO yyyy-MM-dd") 
            String startDate,

        @Schema(
            description = "Fecha de fin de aplicación de la tarifa", 
            example = "2020-05-31", required = true) 
        @NotBlank(message = "El campo endDate es obligatorio") 
        @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}", 
            message = "endDate debe tener el formato ISO yyyy-MM-dd") 
        String endDate,

        @Schema(
            description = "Precio del producto", 
            example = "1234", required = true) 
        @NotNull(message = "El campo price es obligatorio") 
        @Min(value = 1, message = "El precio no puede ser negativo") 
        Long price,

        @Schema(
            description = "ID de la moneda", 
            example = "EUR", required = true) 
        @NotNull(message = "El campo currencyCode es obligatorio") 
        @Size(min = 3, max = 3, message = "currencyCode debe tener exactamente 3 caracteres") 
        @Pattern(regexp = "^[A-Z]{3}$", message = "currencyCode debe ser exactamente 3 letras mayúsculas") 
        String currencyCode) {

    public Boolean areStartAndEndDatesValid() {
        LocalDate startLocalDate;
        LocalDate endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startDate);
            endLocalDate = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Fecha inválida: " + e.getParsedString() + " no existe");
        }
        if (startLocalDate.isAfter(endLocalDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "StartDate debe ser anterior a EndDate");
        }
        return true;
    }

}

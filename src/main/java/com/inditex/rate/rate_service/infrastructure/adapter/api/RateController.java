package com.inditex.rate.rate_service.infrastructure.adapter.api;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.inditex.rate.rate_service.application.dto.RateRequestDto;
import com.inditex.rate.rate_service.application.dto.RateResponseDto;
import com.inditex.rate.rate_service.application.ports.RateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Tag(name = "Rates", description = "API para la gestión de tarifas")
@RestController
@RequestMapping("/api/v1/rates")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @Operation(
        summary = "Crear una nueva tarifa", 
        description = "Crea una nueva tarifa con los datos proporcionados")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tarifa creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<?> createRate(@Valid @RequestBody RateRequestDto request) {
        request.areStartAndEndDatesValid();
        Long newRateId = rateService.createRate(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tarifa con id: " + newRateId + " creada correctamente");
    }

    @Operation(
        summary = "Buscar tarifa por ID",
        description = "Obtiene una tarifa específica por su identificador")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tarifa encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RateResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Tarifa no encontrada")
    })
    @GetMapping("/{rateId}")
    public ResponseEntity<?> findRateById(@PathVariable Long rateId) {
        return rateService.findRateById(rateId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarifa con id " + rateId + " no encontrada"));
    }

    @Operation(
        summary = "Actualizar precio de tarifa",
        description = "Actualiza el precio y moneda de una tarifa existente")
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Precio actualizado correctamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Tarifa no encontrada"),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos")
    })
    @PatchMapping("/{rateId}")
    public ResponseEntity<?> updateRatePrice(
        @PathVariable 
        @Min(value = 1, message = "rateId debe ser mayor que 0") 
        Long rateId,

        @RequestParam 
        @NotNull(message = "Inserte el nuevo precio") 
        @Min(value = 0, message = "El precio no puede ser negativo") 
        Long price,

        @RequestParam 
        @NotNull(message = "El campo currencyCode es obligatorio") 
        @Size(min = 3, max = 3, message = "currencyCode debe tener exactamente 3 caracteres") 
        @Pattern(regexp = "[A-Z]{3}", message = "currencyCode Solo pueden ser caractes alfanumericos") 
        String currencyCode) {

            rateService.patchRatePrice(rateId, price, currencyCode);
            return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Eliminar tarifa",
        description = "Elimina una tarifa por su identificador")
    @ApiResponses({
            @ApiResponse(
                responseCode = "204",
                description = "Tarifa eliminada correctamente"),
            @ApiResponse(
                responseCode = "404",
                description = "Tarifa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRateById(@PathVariable Long id) {

        rateService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Buscar tarifa por fecha, producto y marca",
        description = "Obtiene una tarifa específica filtrando por fecha, ID de producto y ID de marca")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tarifa encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = RateResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Tarifa no encontrada"),
        @ApiResponse(
            responseCode = "400",
            description = "Parámetros de búsqueda inválidos")
    })
    @GetMapping("/search")
    public ResponseEntity<?> findRateByDateAndProductAndBrand(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @NotNull(message = "La fecha es obligatoria")
            @Pattern(
                regexp = "\\d{4}-\\d{2}-\\d{2}",
                message = "Date debe tener el formato ISO yyyy-MM-dd") 
            String date,

            @RequestParam
            @Min(value = 1, message = "productId debe ser mayor que 0")
            @NotNull(message = "Product Id es obligatorio") 
            Long productId,

            @RequestParam 
            @Min(value = 1, message = "brandId debe ser mayor que 0") 
            @NotNull(message = "brandId es obligatorio") 
            Long brandId) {

        return rateService.findRateByDateAndProductIdAndBrandId(LocalDate.parse(date), productId, brandId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No se encontró tarifa para fecha: %s, producto: %d, marca: %d",
                                date, productId, brandId)));

    }
}
package com.hotelclover.hotelclover.Dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TarifasDTO {

    @NotNull(message = "La categoría de habitación es obligatoria")
    private Long idCategoriaHabitacion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio no puede ser menor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 dígitos y 2 decimales")
    private BigDecimal precio;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(max = 10, message = "La moneda no debe exceder 10 caracteres")
    private String moneda;

    @NotNull(message = "El impuesto es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El impuesto no puede ser negativo")
    @Digits(integer = 5, fraction = 2, message = "El impuesto debe tener hasta 5 dígitos y 2 decimales")
    private BigDecimal impuesto;

    @NotNull(message = "El número de noches es obligatorio")
    @Min(value = 1, message = "El número de noches debe ser mayor que 0")
    private Integer numeroNoches;

    @NotBlank(message = "La temporada es obligatoria")
    @Pattern(regexp = "alta|media|baja", flags = Pattern.Flag.CASE_INSENSITIVE, message = "La temporada debe ser 'alta', 'media' o 'baja'")
    @Size(max = 20, message = "La temporada no debe exceder 20 caracteres")
    private String temporada;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "activa|inactiva", flags = Pattern.Flag.CASE_INSENSITIVE, message = "El estado debe ser 'activa' o 'inactiva'")
    @Size(max = 10, message = "El estado no debe exceder 10 caracteres")
    private String estadoTarifa;
}
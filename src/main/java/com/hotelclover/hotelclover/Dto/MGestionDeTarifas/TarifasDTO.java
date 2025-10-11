package com.hotelclover.hotelclover.Dto.MGestionDeTarifas;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TarifasDTO {

    @NotBlank(message = "La categoría de habitación es obligatoria")
    @Size(max = 100, message = "La categoría no debe exceder 100 caracteres")
    private String categoriaHabitacion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser menor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 dígitos y 2 decimales")
    private Double precio;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(max = 10, message = "La moneda no debe exceder 10 caracteres")
    private String moneda;

    @NotNull(message = "El impuesto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El impuesto no puede ser negativo")
    @Digits(integer = 5, fraction = 2, message = "El impuesto debe tener hasta 5 dígitos y 2 decimales")
    private Double impuesto;

    @NotNull(message = "El número de noches es obligatorio")
    @Min(value = 1, message = "El número de noches debe ser mayor que 0")
    private Integer numeroNoches;

    @NotBlank(message = "La temporada es obligatoria")
    @Pattern(regexp = "alta|media|baja", message = "La temporada debe ser 'alta', 'media' o 'baja'")
    private String temporada;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "activa|inactiva", message = "El estado debe ser 'activa' o 'inactiva'")
    private String estadoTarifa;
}

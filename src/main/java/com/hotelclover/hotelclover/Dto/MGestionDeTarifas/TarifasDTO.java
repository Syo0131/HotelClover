package com.hotelclover.hotelclover.Dto.MGestionDeTarifas;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarifasDTO {

    private Long idTarifa;

    @NotBlank(message = "La categoría de habitación es obligatoria")
    private String categoriaHabitacion;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser menor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 dígitos y 2 decimales")
    private Double precio;

    @DecimalMin(value = "0.0", inclusive = true, message = "El impuesto no puede ser negativo")
    @Digits(integer = 5, fraction = 2, message = "El impuesto debe tener hasta 5 dígitos y 2 decimales")
    private Double impuesto;

    @NotBlank(message = "La moneda es obligatoria")
    private String moneda;

    @NotBlank(message = "La temporada es obligatoria")
    @Pattern(regexp = "alta|media|baja", message = "La temporada debe ser 'alta', 'media' o 'baja'")
    private String temporada;

    @Min(value = 1, message = "El número de noches debe ser mayor que 0")
    private Integer numeroNoches;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "activa|inactiva", message = "El estado debe ser 'activa' o 'inactiva'")
    private String estadoTarifa;

    private LocalDateTime fechaCreacion;
}

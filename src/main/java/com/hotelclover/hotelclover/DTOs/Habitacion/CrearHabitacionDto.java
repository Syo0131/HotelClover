package com.hotelclover.hotelclover.DTOs.Habitacion;

import com.hotelclover.hotelclover.Models.Habitacion.EstadoHabitacion;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CrearHabitacionDto(
        @NotBlank @Size(max = 20) String numero,
        @NotNull Long categoriaId,
        @NotNull EstadoHabitacion estado,
        @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 8, fraction = 2)
        BigDecimal tarifaNoche
) {}

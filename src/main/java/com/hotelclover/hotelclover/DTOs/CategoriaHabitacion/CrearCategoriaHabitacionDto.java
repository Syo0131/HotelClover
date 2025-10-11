package com.hotelclover.hotelclover.DTOs.CategoriaHabitacion;

import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CrearCategoriaHabitacionDto(
        @NotBlank @Size(max = 80) String nombre,
        @Size(max = 255) String descripcion,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 8, fraction = 2)
        BigDecimal tarifaNoche,
        @Size(max = 500) String caracteristicas,
        @NotNull EstadoCategoria estado
) {}

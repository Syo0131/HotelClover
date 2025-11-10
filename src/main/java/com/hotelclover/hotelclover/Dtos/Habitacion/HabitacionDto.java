package com.hotelclover.hotelclover.Dtos.Habitacion;

import com.hotelclover.hotelclover.Models.Habitacion.EstadoHabitacion;
import java.math.BigDecimal;

public record HabitacionDto(
        Long id,
        String numero,
        Long categoriaId,
        String categoriaNombre,
        EstadoHabitacion estado,
        BigDecimal tarifaNoche,
        BigDecimal tarifaEfectiva
) {}
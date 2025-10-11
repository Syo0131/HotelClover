package com.hotelclover.hotelclover.DTOs.CategoriaHabitacion;

import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
import java.math.BigDecimal;

public record CategoriaHabitacionDto(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal tarifaNoche,
        String caracteristicas,
        EstadoCategoria estado,
        Integer totalHabitaciones
) {}

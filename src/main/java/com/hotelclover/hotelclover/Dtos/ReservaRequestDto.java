package com.hotelclover.hotelclover.Dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO para crear o actualizar una reserva
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDto {

    @NotNull(message = "La fecha de entrada es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEntrada;

    @NotNull(message = "La fecha de salida es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;

    @NotNull(message = "El número de huéspedes es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 huésped")
    @Max(value = 10, message = "No se permiten más de 10 huéspedes")
    private Integer numeroDeHuespedes;

    @NotNull(message = "El ID de la categoría de habitación es obligatorio")
    @Positive(message = "El ID de la categoría debe ser positivo")
    private Long idCategoriaHabitacion;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser positivo")
    private Long idCliente;
}

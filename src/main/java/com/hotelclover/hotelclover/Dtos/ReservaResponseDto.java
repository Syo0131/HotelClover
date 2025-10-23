package com.hotelclover.hotelclover.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO para respuesta de reserva con información completa
 * Utiliza objetos anidados para mejor escalabilidad
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEntrada;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;

    private Integer numeroDeHuespedes;

    // Objeto anidado de categoría de habitación
    private CategoriaHabitacionDto categoriaHabitacion;

    // Objeto anidado de cliente
    private ClienteDto cliente;

    // Información adicional calculada
    private Integer diasEstancia;

    /**
     * DTO interno para información de categoría de habitación
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaHabitacionDto {
        private Long id;
        private String nombre;
        private String descripcion;
    }

    /**
     * DTO interno para información de cliente
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteDto {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
    }
}

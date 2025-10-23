package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Dtos.ReservaResponseDto;

import java.util.List;

/**
 * Servicio para la gestión de reservas
 */
public interface ReservaService {

    /**
     * Crea una nueva reserva
     */
    ReservaResponseDto crearReserva(ReservaRequestDto reservaDto);

    /**
     * Obtiene todas las reservas
     */
    List<ReservaResponseDto> obtenerTodasLasReservas();

    /**
     * Obtiene una reserva por su ID
     */
    ReservaResponseDto obtenerReservaPorId(Long id);

    /**
     * Actualiza una reserva existente
     */
    ReservaResponseDto actualizarReserva(Long id, ReservaRequestDto reservaDto);

    /**
     * Elimina una reserva
     */
    void eliminarReserva(Long id);

    /**
     * Obtiene reservas por cliente
     */
    List<ReservaResponseDto> obtenerReservasPorCliente(Long idCliente);

    /**
     * Obtiene reservas por categoría de habitación
     */
    List<ReservaResponseDto> obtenerReservasPorCategoria(Long idCategoria);
}

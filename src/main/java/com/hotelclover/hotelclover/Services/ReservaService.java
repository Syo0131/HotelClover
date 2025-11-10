package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Dtos.ReservaResponseDto;

import java.util.List;


public interface ReservaService {

    ReservaResponseDto crearReserva(ReservaRequestDto reservaDto);

    List<ReservaResponseDto> obtenerTodasLasReservas();

    ReservaResponseDto obtenerReservaPorId(Long id);

    ReservaResponseDto actualizarReserva(Long id, ReservaRequestDto reservaDto);

    void eliminarReserva(Long id);


    List<ReservaResponseDto> obtenerReservasPorCliente(Long idCliente);


    List<ReservaResponseDto> obtenerReservasPorCategoria(Long idCategoria);
}

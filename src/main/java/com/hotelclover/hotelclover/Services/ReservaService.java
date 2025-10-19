package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Models.Reserva;



public interface ReservaService {

    Reserva saveReserva(Reserva reserva);
    Iterable<Reserva> getAllReservas();
    Reserva getReservaById(Long id);
    void deleteReserva(Long id);
    Reserva updateReserva(Long id, Reserva reservaDetails);
    
}

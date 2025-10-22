package com.hotelclover.hotelclover.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelclover.hotelclover.Models.Reserva;
import com.hotelclover.hotelclover.Repositories.ReservaRepository;
import com.hotelclover.hotelclover.Services.ReservaService;


@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;


    @Override
    public Reserva saveReserva(Reserva reserva) {

        return reservaRepository.save(reserva);

    }

    @Override
    public Iterable<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva getReservaById(Long id) {

        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReserva(Long id) {

        reservaRepository.deleteById(id);
    }

    @Override
    public Reserva updateReserva(Long id, Reserva reservaDetails) {

        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null) {
            reserva.setEntryDate(reservaDetails.getEntryDate());
            reserva.setExitDate(reservaDetails.getExitDate());
            reserva.setNumeroDeHuespedes(reservaDetails.getNumeroDeHuespedes());
            reserva.setCategoriaHabitacion(reservaDetails.getCategoriaHabitacion());
            reserva.setCliente(reservaDetails.getCliente());
            return reservaRepository.save(reserva);
        }
        return null;
    }
    
}

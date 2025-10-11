package com.hotelclover.hotelclover.Services.MGestionDeTarifas;

import com.hotelclover.hotelclover.Dto.MGestionDeTarifas.TarifasDTO;
import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifa;
import com.hotelclover.hotelclover.Repositories.MGestionDeTarifas.TarifasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarifasService {

    private TarifasRepository tarifasRepository;

    public Tarifa createRate(TarifasDTO dto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setCategoriaHabitacion(dto.getCategoriaHabitacion());
        tarifa.setPrecio(dto.getPrecio());
        tarifa.setMoneda(dto.getMoneda());
        tarifa.setImpuesto(dto.getImpuesto());
        tarifa.setNumeroNoches(dto.getNumeroNoches());
        tarifa.setTemporada(dto.getTemporada());
        tarifa.setEstadoTarifa(dto.getEstadoTarifa());
        tarifa.setFechaCreacion(LocalDateTime.now());

        return tarifasRepository.save(tarifa);
    }

    public Tarifa updateRate(Long id, TarifasDTO dto) {
        Tarifa tarifa = tarifasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rate not found"));

        tarifa.setCategoriaHabitacion(dto.getCategoriaHabitacion());
        tarifa.setPrecio(dto.getPrecio());
        tarifa.setMoneda(dto.getMoneda());
        tarifa.setImpuesto(dto.getImpuesto());
        tarifa.setNumeroNoches(dto.getNumeroNoches());
        tarifa.setTemporada(dto.getTemporada());
        tarifa.setEstadoTarifa(dto.getEstadoTarifa());

        return tarifasRepository.save(tarifa);
    }

    public Optional<Tarifa> getRateById(Long id) {
        return tarifasRepository.findById(id);
    }

    public List<Tarifa> listByCategory(String category) {
        return tarifasRepository.findByCategoriaHabitacion(category);
    }

    public List<Tarifa> listBySeason(String season) {
        return tarifasRepository.findByTemporada(season);
    }

    public List<Tarifa> listByStatus(String status) {
        return tarifasRepository.findByEstadoTarifa(status);
    }

    public List<Tarifa> listByNumberOfNights(Integer nights) {
        return tarifasRepository.findByNumeroNoches(nights);
    }
}
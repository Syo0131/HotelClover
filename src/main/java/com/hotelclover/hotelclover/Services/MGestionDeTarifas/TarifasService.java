package com.hotelclover.hotelclover.Services.MGestionDeTarifas;

import com.hotelclover.hotelclover.Dto.MGestionDeTarifas.TarifasDTO;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.TipoUsuario;
import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifa;
import com.hotelclover.hotelclover.Repositories.MGestionDeTarifas.TarifasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TarifasService {

    private final TarifasRepository tarifasRepository;

    public Tarifa createRate(TarifasDTO dto, TipoUsuario userType) {
        if (userType != TipoUsuario.ADMINISTRADOR) {
            throw new SecurityException("Access denied: only administrators can create rates.");
        }

        Tarifa rate = new Tarifa();
        rate.setCategoriaHabitacion(dto.getCategoriaHabitacion());
        rate.setPrecio(dto.getPrecio());
        rate.setMoneda(dto.getMoneda());
        rate.setImpuesto(dto.getImpuesto());
        rate.setNumeroNoches(dto.getNumeroNoches());
        rate.setTemporada(dto.getTemporada());
        rate.setEstadoTarifa(dto.getEstadoTarifa());
        rate.setFechaCreacion(LocalDateTime.now());

        return tarifasRepository.save(rate);
    }

    public Tarifa updateRate(Long id, TarifasDTO dto, TipoUsuario userType) {
        if (userType != TipoUsuario.ADMINISTRADOR) {
            throw new SecurityException("Access denied: only administrators can update rates.");
        }

        Tarifa rate = tarifasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rate not found"));

        rate.setCategoriaHabitacion(dto.getCategoriaHabitacion());
        rate.setPrecio(dto.getPrecio());
        rate.setMoneda(dto.getMoneda());
        rate.setImpuesto(dto.getImpuesto());
        rate.setNumeroNoches(dto.getNumeroNoches());
        rate.setTemporada(dto.getTemporada());
        rate.setEstadoTarifa(dto.getEstadoTarifa());

        return tarifasRepository.save(rate);
    }

    public Optional<Tarifa> getRateById(Long id) {
        return tarifasRepository.findById(id);
    }

    public List<Tarifa> getRatesByCategory(String category) {
        return tarifasRepository.findByCategoriaHabitacion(category);
    }

    public List<Tarifa> getRatesBySeason(String season) {
        return tarifasRepository.findByTemporada(season);
    }

    public List<Tarifa> getRatesByStatus(String status) {
        return tarifasRepository.findByEstadoTarifa(status);
    }

    public List<Tarifa> getRatesByNumberOfNights(Integer nights) {
        return tarifasRepository.findByNumeroNoches(nights);
    }

    public List<Tarifa> generateRateReport(
            TipoUsuario userType,
            String roomCategory,
            String season,
            LocalDate startDate,
            LocalDate endDate) {
        if (userType != TipoUsuario.ADMINISTRADOR && userType != TipoUsuario.AUDITOR) {
            throw new SecurityException("Access denied: only administrators or auditors can generate reports.");
        }

        Iterable<Tarifa> ratesIterable = tarifasRepository.findAll();

        return StreamSupport.stream(ratesIterable.spliterator(), false)
                .filter(rate -> (roomCategory == null || rate.getCategoriaHabitacion().equalsIgnoreCase(roomCategory)) &&
                                (season == null || rate.getTemporada().equalsIgnoreCase(season)) &&
                                (startDate == null || !rate.getFechaCreacion().toLocalDate().isBefore(startDate)) &&
                                (endDate == null || !rate.getFechaCreacion().toLocalDate().isAfter(endDate)))
                .collect(Collectors.toList());
    }
}
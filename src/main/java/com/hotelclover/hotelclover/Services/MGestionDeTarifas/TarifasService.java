package com.hotelclover.hotelclover.Services.MGestionDeTarifas;

import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Tarifa;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.MGestionDeTarifas.TarifasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifasService {

    private final TarifasRepository tarifasRepository;
    private final CategoriaHabitacionRepository categoriaHabitacionRepository;

    public List<Tarifa> getAllTarifas() {
        return (List<Tarifa>) tarifasRepository.findAll();
    }

    public Tarifa obtenerPorId(Long id) {
        return tarifasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no encontrada con ID: " + id));
    }

    public Tarifa saveTarifa(Tarifa tarifa) {
        tarifa.setFechaCreacion(LocalDateTime.now());
        Long idCategoria = tarifa.getCategoriaHabitacion().getIdCategoriaHabitacion();
        CategoriaHabitacion categoria = categoriaHabitacionRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + idCategoria));
        tarifa.setCategoriaHabitacion(categoria);

        return tarifasRepository.save(tarifa);
    }

    public Tarifa actualizarTarifa(Long id, Tarifa tarifaActualizada) {
        Tarifa existente = obtenerPorId(id);

        existente.setPrecio(tarifaActualizada.getPrecio());
        existente.setImpuesto(tarifaActualizada.getImpuesto());
        existente.setMoneda(tarifaActualizada.getMoneda());
        existente.setNumeroNoches(tarifaActualizada.getNumeroNoches());
        existente.setTemporada(tarifaActualizada.getTemporada());
        existente.setEstadoTarifa(tarifaActualizada.getEstadoTarifa());

        return tarifasRepository.save(existente);
    }

    public void deleteTarifa(Long id) {
        tarifasRepository.deleteById(id);
    }

    public List<CategoriaHabitacion> getAllCategorias() {
        return categoriaHabitacionRepository.findAll();
    }
}
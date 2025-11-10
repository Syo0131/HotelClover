package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Dtos.Habitacion.*;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Habitacion;
import com.hotelclover.hotelclover.Models.Habitacion.EstadoHabitacion;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.HabitacionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepo;
    private final CategoriaHabitacionRepository categoriaRepo;

    public HabitacionService(HabitacionRepository habitacionRepo,
                             CategoriaHabitacionRepository categoriaRepo) {
        this.habitacionRepo = habitacionRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @Transactional
    public HabitacionDto crear(CrearHabitacionDto dto) {
        CategoriaHabitacion cat = categoriaRepo.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría inexistente."));

        if (habitacionRepo.existsByNumeroIgnoreCase(dto.numero())) {
            throw new IllegalArgumentException("Número de habitación ya existe.");
        }

        Habitacion entity = toEntity(dto, cat);
        entity = habitacionRepo.save(entity);
        return toDto(entity);
    }

    @Transactional
    public HabitacionDto actualizar(Long id, ActualizarHabitacionDto dto) {
        Habitacion entity = habitacionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada."));

        CategoriaHabitacion cat = categoriaRepo.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría inexistente."));

        if (!entity.getNumero().equalsIgnoreCase(dto.numero())
                && habitacionRepo.existsByNumeroIgnoreCase(dto.numero())) {
            throw new IllegalArgumentException("Número de habitación ya existe.");
        }

        updateEntityFromDto(dto, entity, cat);
        entity = habitacionRepo.save(entity);
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public HabitacionDto obtener(Long id) {
        Habitacion entity = habitacionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada."));
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<HabitacionDto> buscar(Long categoriaId,
                                      EstadoHabitacion estado,
                                      BigDecimal minTarifa,
                                      BigDecimal maxTarifa,
                                      Pageable pageable) {
        return habitacionRepo.search(categoriaId, estado, minTarifa, maxTarifa, pageable)
                .map(this::toDto);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!habitacionRepo.existsById(id)) return;
        habitacionRepo.deleteById(id);
    }

    

    private Habitacion toEntity(CrearHabitacionDto dto, CategoriaHabitacion categoria) {
        Habitacion h = new Habitacion();
        h.setNumero(dto.numero());
        h.setEstado(dto.estado());
        h.setTarifaNoche(dto.tarifaNoche());
        h.setCategoria(categoria);
        return h;
    }

    private void updateEntityFromDto(ActualizarHabitacionDto dto, Habitacion entity, CategoriaHabitacion categoria) {
        entity.setNumero(dto.numero());
        entity.setEstado(dto.estado());
        entity.setTarifaNoche(dto.tarifaNoche());
        entity.setCategoria(categoria);
    }

    private HabitacionDto toDto(Habitacion entity) {
    Long id = entity.getId(); 

    Long categoriaId = null;
    String categoriaNombre = null;
    BigDecimal tarifaCategoria = null;

    if (entity.getCategoria() != null) {
        
        categoriaId = entity.getCategoria().getIdCategoriaHabitacion();
        categoriaNombre = entity.getCategoria().getNombre();
        tarifaCategoria = entity.getCategoria().getTarifaNoche();
    }

    BigDecimal tarifaNoche = entity.getTarifaNoche();
    BigDecimal tarifaEfectiva = (tarifaNoche != null) ? tarifaNoche : tarifaCategoria;

    return new HabitacionDto(
            id,
            entity.getNumero(),
            categoriaId,
            categoriaNombre,
            entity.getEstado(),
            tarifaNoche,
            tarifaEfectiva
    );
}
}
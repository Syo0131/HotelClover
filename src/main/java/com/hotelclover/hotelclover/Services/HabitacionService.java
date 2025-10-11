package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.DTOs.Habitacion.*;
import com.hotelclover.hotelclover.Mappers.HabitacionMapper;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Habitacion;
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
    private final HabitacionMapper mapper;

    public HabitacionService(HabitacionRepository habitacionRepo,
                             CategoriaHabitacionRepository categoriaRepo,
                             HabitacionMapper mapper) {
        this.habitacionRepo = habitacionRepo;
        this.categoriaRepo = categoriaRepo;
        this.mapper = mapper;
    }

    @Transactional
    public HabitacionDto crear(CrearHabitacionDto dto) {
        // valida categoría
        CategoriaHabitacion cat = categoriaRepo.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría inexistente."));
        // valida números únicos
        if (habitacionRepo.existsByNumeroIgnoreCase(dto.numero())) throw new IllegalArgumentException("Número ya existe.");

        Habitacion entity = mapper.toEntity(dto);
        // aseguramos la relación (por si el mapper solo setea ref)
        entity.setCategoria(cat);

        entity = habitacionRepo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public HabitacionDto actualizar(Long id, ActualizarHabitacionDto dto) {
        var entity = habitacionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada."));

        // valida categoría
        CategoriaHabitacion cat = categoriaRepo.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría inexistente."));

        mapper.updateEntityFromDto(dto, entity);
        entity.setCategoria(cat);

        entity = habitacionRepo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public HabitacionDto obtener(Long id) {
        var entity = habitacionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada."));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<HabitacionDto> buscar(Long categoriaId,
                                      Habitacion.EstadoHabitacion estado,
                                      BigDecimal minTarifa,
                                      BigDecimal maxTarifa,
                                      Pageable pageable) {
        return habitacionRepo.search(categoriaId, estado, minTarifa, maxTarifa, pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!habitacionRepo.existsById(id)) return;
        habitacionRepo.deleteById(id);
    }
}

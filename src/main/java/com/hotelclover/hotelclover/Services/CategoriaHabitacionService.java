package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.DTOs.CategoriaHabitacion.*;
import com.hotelclover.hotelclover.Mappers.CategoriaHabitacionMapper;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.HabitacionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CategoriaHabitacionService {

    private final CategoriaHabitacionRepository categoriaRepo;
    private final HabitacionRepository habitacionRepo;
    private final CategoriaHabitacionMapper mapper;

    public CategoriaHabitacionService(CategoriaHabitacionRepository categoriaRepo,
                                      HabitacionRepository habitacionRepo,
                                      CategoriaHabitacionMapper mapper) {
        this.categoriaRepo = categoriaRepo;
        this.habitacionRepo = habitacionRepo;
        this.mapper = mapper;
    }

    @Transactional
    public CategoriaHabitacionDto crear(CrearCategoriaHabitacionDto dto) {
        if (categoriaRepo.existsByNombreIgnoreCase(dto.nombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        CategoriaHabitacion entity = mapper.toEntity(dto);
        entity = categoriaRepo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public CategoriaHabitacionDto actualizar(Long id, ActualizarCategoriaHabitacionDto dto) {
        var entity = categoriaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (categoriaRepo.existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(dto.nombre(), id)) {
            throw new IllegalArgumentException("Ya existe otra categoría con ese nombre.");
        }

        mapper.updateEntityFromDto(dto, entity);
        entity = categoriaRepo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public CategoriaHabitacionDto obtener(Long id) {
        var entity = categoriaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<CategoriaHabitacionDto> buscar(String q,
                                               CategoriaHabitacion.EstadoCategoria estado,
                                               BigDecimal minTarifa,
                                               BigDecimal maxTarifa,
                                               Pageable pageable) {
        return categoriaRepo.search(q, estado, minTarifa, maxTarifa, pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!categoriaRepo.existsById(id)) return;
        long vinculadas = habitacionRepo.countByCategoria_IdCategoriaHabitacion(id);
        if (vinculadas > 0) {
            throw new IllegalStateException("No se puede eliminar: hay habitaciones asociadas (" + vinculadas + ").");
        }
        categoriaRepo.deleteById(id);
    }
}

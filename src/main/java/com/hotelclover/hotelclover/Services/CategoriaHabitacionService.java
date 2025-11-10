package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Dtos.CategoriaHabitacion.*;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
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

    public CategoriaHabitacionService(CategoriaHabitacionRepository categoriaRepo,
                                      HabitacionRepository habitacionRepo) {
        this.categoriaRepo = categoriaRepo;
        this.habitacionRepo = habitacionRepo;
    }

    @Transactional
    public CategoriaHabitacionDto crear(CrearCategoriaHabitacionDto dto) {
        if (categoriaRepo.existsByNombreIgnoreCase(dto.nombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        CategoriaHabitacion entity = toEntity(dto);
        entity = categoriaRepo.save(entity);
        return toDto(entity);
    }

    @Transactional
    public CategoriaHabitacionDto actualizar(Long id, ActualizarCategoriaHabitacionDto dto) {
        var entity = categoriaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        if (categoriaRepo.existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(dto.nombre(), id)) {
            throw new IllegalArgumentException("Ya existe otra categoría con ese nombre.");
        }

        updateEntityFromDto(dto, entity);
        entity = categoriaRepo.save(entity);
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public CategoriaHabitacionDto obtener(Long id) {
        var entity = categoriaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<CategoriaHabitacionDto> buscar(String q,
                                               EstadoCategoria estado,
                                               BigDecimal minTarifa,
                                               BigDecimal maxTarifa,
                                               Pageable pageable) {
        return categoriaRepo.search(q, estado, minTarifa, maxTarifa, pageable)
                .map(this::toDto);
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

    

    private CategoriaHabitacion toEntity(CrearCategoriaHabitacionDto dto) {
        var entity = new CategoriaHabitacion();
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setTarifaNoche(dto.tarifaNoche());
        entity.setCaracteristicas(dto.caracteristicas());
        entity.setEstado(dto.estado());
        return entity;
    }

    private void updateEntityFromDto(ActualizarCategoriaHabitacionDto dto, CategoriaHabitacion entity) {
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setTarifaNoche(dto.tarifaNoche());
        entity.setCaracteristicas(dto.caracteristicas());
        entity.setEstado(dto.estado());
    }

    private CategoriaHabitacionDto toDto(CategoriaHabitacion entity) {
        Long id = entity.getIdCategoriaHabitacion(); 
        Integer total = null;
        if (id != null) {
            total = Math.toIntExact(habitacionRepo.countByCategoria_IdCategoriaHabitacion(id));
        }
        return new CategoriaHabitacionDto(
                id,
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getTarifaNoche(),
                entity.getCaracteristicas(),
                entity.getEstado(),
                total
        );
    }
}
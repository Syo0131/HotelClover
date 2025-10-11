package com.hotelclover.hotelclover.Mappers;

import com.hotelclover.hotelclover.DTOs.Habitacion.*;
import com.hotelclover.hotelclover.Models.Habitacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    // ====== Entity -> DTO ======
    @Mapping(target = "id", source = "id")
    @Mapping(target = "categoriaId", source = "categoria.idCategoriaHabitacion")
    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    @Mapping(target = "tarifaEfectiva",
             expression = "java(habitacion.getTarifaNoche() != null ? habitacion.getTarifaNoche() : habitacion.getCategoria().getTarifaNoche())")
    HabitacionDto toDto(Habitacion habitacion);

    // ====== DTO -> Entity (crear) ======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", expression = "java(new CategoriaHabitacion())") // se asignará luego en el service
    Habitacion toEntity(CrearHabitacionDto dto);

    // ====== Actualizar (merge sobre entity existente) ======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", expression = "java(new CategoriaHabitacion())") // el service repondrá la relación
    void updateEntityFromDto(ActualizarHabitacionDto dto, @MappingTarget Habitacion entity);
}

package com.hotelclover.hotelclover.Mappers;

import com.hotelclover.hotelclover.DTOs.CategoriaHabitacion.CategoriaHabitacionDto;
import com.hotelclover.hotelclover.DTOs.CategoriaHabitacion.CrearCategoriaHabitacionDto;
import com.hotelclover.hotelclover.DTOs.CategoriaHabitacion.ActualizarCategoriaHabitacionDto;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CategoriaHabitacionMapper {

    @Mapping(target = "id", source = "idCategoriaHabitacion")
    @Mapping(target = "totalHabitaciones",
             expression = "java(categoria.getHabitaciones() == null ? 0 : categoria.getHabitaciones().size())")
    CategoriaHabitacionDto toDto(CategoriaHabitacion categoria);

    @Mapping(target = "idCategoriaHabitacion", ignore = true)
    @Mapping(target = "habitaciones", ignore = true)
    CategoriaHabitacion toEntity(CrearCategoriaHabitacionDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idCategoriaHabitacion", ignore = true)
    @Mapping(target = "habitaciones", ignore = true)
    void updateEntityFromDto(ActualizarCategoriaHabitacionDto dto,
                             @MappingTarget CategoriaHabitacion entity);
}
package com.hotelclover.hotelclover.Mappers;

import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Dtos.ReservaResponseDto;
import com.hotelclover.hotelclover.Models.Reserva;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Clientes;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReservaMapper {

    public ReservaResponseDto toResponseDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        // Convertir java.util.Date a LocalDate
        LocalDate fechaEntrada = new java.sql.Date(reserva.getEntryDate().getTime()).toLocalDate();
        LocalDate fechaSalida = new java.sql.Date(reserva.getExitDate().getTime()).toLocalDate();

        // Calcular días de estancia
        long diasEstancia = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);

        // Crear DTO de categoría de habitación
        ReservaResponseDto.CategoriaHabitacionDto categoriaDto = ReservaResponseDto.CategoriaHabitacionDto.builder()
                .id(reserva.getCategoriaHabitacion().getId())
                .nombre(reserva.getCategoriaHabitacion().getNombre())
                .descripcion(reserva.getCategoriaHabitacion().getDescripcion())
                .build();

        // Crear DTO de cliente
        ReservaResponseDto.ClienteDto clienteDto = ReservaResponseDto.ClienteDto.builder()
                .id(reserva.getCliente().getIdCliente())
                .nombre(reserva.getCliente().getNombre())
                .apellido(reserva.getCliente().getApellido())
                .email(reserva.getCliente().getEmail())
                .telefono(reserva.getCliente().getTelefono())
                .build();

        // Crear el DTO de respuesta con objetos anidados
        return ReservaResponseDto.builder()
                .id(reserva.getId())
                .fechaEntrada(fechaEntrada)
                .fechaSalida(fechaSalida)
                .numeroDeHuespedes(reserva.getNumeroDeHuespedes())
                .categoriaHabitacion(categoriaDto)
                .cliente(clienteDto)
                .diasEstancia((int) diasEstancia)
                .build();
    }

    public Reserva toEntity(ReservaRequestDto dto, CategoriaHabitacion categoria, Clientes cliente) {
        if (dto == null) {
            return null;
        }

        return Reserva.builder()
                .entryDate(Date.valueOf(dto.getFechaEntrada()))
                .exitDate(Date.valueOf(dto.getFechaSalida()))
                .numeroDeHuespedes(dto.getNumeroDeHuespedes())
                .categoriaHabitacion(categoria)
                .cliente(cliente)
                .build();
    }

    public void updateEntityFromDto(ReservaRequestDto dto, Reserva reserva,
            CategoriaHabitacion categoria, Clientes cliente) {
        if (dto == null || reserva == null) {
            return;
        }

        reserva.setEntryDate(Date.valueOf(dto.getFechaEntrada()));
        reserva.setExitDate(Date.valueOf(dto.getFechaSalida()));
        reserva.setNumeroDeHuespedes(dto.getNumeroDeHuespedes());
        reserva.setCategoriaHabitacion(categoria);
        reserva.setCliente(cliente);
    }
}

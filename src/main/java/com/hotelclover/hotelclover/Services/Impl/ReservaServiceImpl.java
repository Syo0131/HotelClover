package com.hotelclover.hotelclover.Services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Dtos.ReservaResponseDto;
import com.hotelclover.hotelclover.Exceptions.BadRequestException;
import com.hotelclover.hotelclover.Exceptions.ResourceNotFoundException;
import com.hotelclover.hotelclover.Mappers.ReservaMapper;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Usuario;
import com.hotelclover.hotelclover.Models.Reserva;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;
import com.hotelclover.hotelclover.Repositories.ReservaRepository;
import com.hotelclover.hotelclover.Services.ReservaService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de reservas
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClientesRepository clientesRepository;
    private final CategoriaHabitacionRepository categoriaRepository;
    private final ReservaMapper reservaMapper;

    @Override
    public ReservaResponseDto crearReserva(ReservaRequestDto reservaDto) {
        log.info("Creando nueva reserva para cliente ID: {}", reservaDto.getIdCliente());

        // Validar fechas
        validarFechas(reservaDto.getFechaEntrada(), reservaDto.getFechaSalida());

        // Buscar cliente
        Usuario cliente = clientesRepository.findById(reservaDto.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente", "id", reservaDto.getIdCliente()));

        // Buscar categoría
        CategoriaHabitacion categoria = categoriaRepository.findById(reservaDto.getIdCategoriaHabitacion())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategoriaHabitacion", "id", reservaDto.getIdCategoriaHabitacion()));

        // Crear y guardar reserva
        Reserva reserva = reservaMapper.toEntity(reservaDto, categoria, cliente);
        Reserva reservaGuardada = reservaRepository.save(reserva);

        log.info("Reserva creada exitosamente con ID: {}", reservaGuardada.getId());
        return reservaMapper.toResponseDto(reservaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDto> obtenerTodasLasReservas() {
        log.info("Obteniendo todas las reservas");

        return reservaRepository.findAll().stream()
                .map(reservaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponseDto obtenerReservaPorId(Long id) {
        log.info("Obteniendo reserva con ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva", "id", id));

        return reservaMapper.toResponseDto(reserva);
    }

    @Override
    public ReservaResponseDto actualizarReserva(Long id, ReservaRequestDto reservaDto) {
        log.info("Actualizando reserva con ID: {}", id);

        // Validar fechas
        validarFechas(reservaDto.getFechaEntrada(), reservaDto.getFechaSalida());

        // Buscar reserva existente
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva", "id", id));

        // Buscar cliente
        Usuario cliente = clientesRepository.findById(reservaDto.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente", "id", reservaDto.getIdCliente()));

        // Buscar categoría
        CategoriaHabitacion categoria = categoriaRepository.findById(reservaDto.getIdCategoriaHabitacion())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "CategoriaHabitacion", "id", reservaDto.getIdCategoriaHabitacion()));

        // Actualizar reserva
        reservaMapper.updateEntityFromDto(reservaDto, reservaExistente, categoria, cliente);
        Reserva reservaActualizada = reservaRepository.save(reservaExistente);

        log.info("Reserva actualizada exitosamente con ID: {}", id);
        return reservaMapper.toResponseDto(reservaActualizada);
    }

    @Override
    public void eliminarReserva(Long id) {
        log.info("Eliminando reserva con ID: {}", id);

        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva", "id", id);
        }

        reservaRepository.deleteById(id);
        log.info("Reserva eliminada exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDto> obtenerReservasPorCliente(Long idCliente) {
        log.info("Obteniendo reservas del cliente con ID: {}", idCliente);

        // Verificar que el cliente existe
        if (!clientesRepository.existsById(idCliente)) {
            throw new ResourceNotFoundException("Cliente", "id", idCliente);
        }

        return reservaRepository.findByCliente_Id(idCliente).stream()
                .map(reservaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDto> obtenerReservasPorCategoria(Long idCategoria) {
        log.info("Obteniendo reservas de la categoría con ID: {}", idCategoria);

        // Verificar que la categoría existe
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResourceNotFoundException("CategoriaHabitacion", "id", idCategoria);
        }

        return reservaRepository.findByCategoriaHabitacion_IdCategoriaHabitacion(idCategoria).stream()
                .map(reservaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Valida que las fechas de reserva sean correctas
     */
    private void validarFechas(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (fechaEntrada.isAfter(fechaSalida)) {
            throw new BadRequestException(
                    "La fecha de entrada no puede ser posterior a la fecha de salida");
        }

        if (fechaEntrada.isBefore(LocalDate.now())) {
            throw new BadRequestException(
                    "La fecha de entrada no puede ser anterior a la fecha actual");
        }

        if (fechaEntrada.equals(fechaSalida)) {
            throw new BadRequestException(
                    "La fecha de entrada y salida no pueden ser el mismo día");
        }
    }
}

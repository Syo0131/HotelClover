package com.hotelclover.hotelclover.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Dtos.ReservaResponseDto;
import com.hotelclover.hotelclover.Services.ReservaService;

import java.util.List;

/**
 * Controlador REST para la gestión de reservas
 * Implementa operaciones CRUD completas
 */
@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    /**
     * Crea una nueva reserva
     * POST /api/reservas
     * 
     * @param reservaDto Datos de la reserva a crear
     * @return ReservaResponseDto con los datos de la reserva creada
     */
    @PostMapping
    public ResponseEntity<ReservaResponseDto> crearReserva(
            @Valid @RequestBody ReservaRequestDto reservaDto) {
        log.info("Solicitud para crear nueva reserva");

        ReservaResponseDto reservaCreada = reservaService.crearReserva(reservaDto);
        return new ResponseEntity<>(reservaCreada, HttpStatus.CREATED);
    }

    /**
     * Obtiene todas las reservas
     * GET /api/reservas
     * 
     * @return Lista de todas las reservas
     */
    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> obtenerTodasLasReservas() {
        log.info("Solicitud para obtener todas las reservas");

        List<ReservaResponseDto> reservas = reservaService.obtenerTodasLasReservas();
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene una reserva por su ID
     * GET /api/reservas/{id}
     * 
     * @param id ID de la reserva
     * @return ReservaResponseDto con los datos de la reserva
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> obtenerReservaPorId(
            @PathVariable Long id) {
        log.info("Solicitud para obtener reserva con ID: {}", id);

        ReservaResponseDto reserva = reservaService.obtenerReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }

    /**
     * Actualiza una reserva existente
     * PUT /api/reservas/{id}
     * 
     * @param id         ID de la reserva a actualizar
     * @param reservaDto Nuevos datos de la reserva
     * @return ReservaResponseDto con los datos actualizados
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDto reservaDto) {
        log.info("Solicitud para actualizar reserva con ID: {}", id);

        ReservaResponseDto reservaActualizada = reservaService.actualizarReserva(id, reservaDto);
        return ResponseEntity.ok(reservaActualizada);
    }

    /**
     * Elimina una reserva
     * DELETE /api/reservas/{id}
     * 
     * @param id ID de la reserva a eliminar
     * @return ResponseEntity sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        log.info("Solicitud para eliminar reserva con ID: {}", id);

        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todas las reservas de un cliente
     * GET /api/reservas/cliente/{idCliente}
     * 
     * @param idCliente ID del cliente
     * @return Lista de reservas del cliente
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorCliente(
            @PathVariable Long idCliente) {
        log.info("Solicitud para obtener reservas del cliente con ID: {}", idCliente);

        List<ReservaResponseDto> reservas = reservaService.obtenerReservasPorCliente(idCliente);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene todas las reservas de una categoría de habitación
     * GET /api/reservas/categoria/{idCategoria}
     * 
     * @param idCategoria ID de la categoría
     * @return Lista de reservas de la categoría
     */
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorCategoria(
            @PathVariable Long idCategoria) {
        log.info("Solicitud para obtener reservas de la categoría con ID: {}", idCategoria);

        List<ReservaResponseDto> reservas = reservaService.obtenerReservasPorCategoria(idCategoria);
        return ResponseEntity.ok(reservas);
    }
}

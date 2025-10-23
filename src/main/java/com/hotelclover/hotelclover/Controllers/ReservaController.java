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


@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;


    @PostMapping
    public ResponseEntity<ReservaResponseDto> crearReserva(
            @Valid @RequestBody ReservaRequestDto reservaDto) {
        log.info("Solicitud para crear nueva reserva");

        ReservaResponseDto reservaCreada = reservaService.crearReserva(reservaDto);
        return new ResponseEntity<>(reservaCreada, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> obtenerTodasLasReservas() {
        log.info("Solicitud para obtener todas las reservas");

        List<ReservaResponseDto> reservas = reservaService.obtenerTodasLasReservas();
        return ResponseEntity.ok(reservas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> obtenerReservaPorId(
            @PathVariable Long id) {
        log.info("Solicitud para obtener reserva con ID: {}", id);

        ReservaResponseDto reserva = reservaService.obtenerReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDto reservaDto) {
        log.info("Solicitud para actualizar reserva con ID: {}", id);

        ReservaResponseDto reservaActualizada = reservaService.actualizarReserva(id, reservaDto);
        return ResponseEntity.ok(reservaActualizada);
    }
      @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        log.info("Solicitud para eliminar reserva con ID: {}", id);

        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorCliente(
            @PathVariable Long idCliente) {
        log.info("Solicitud para obtener reservas del cliente con ID: {}", idCliente);

        List<ReservaResponseDto> reservas = reservaService.obtenerReservasPorCliente(idCliente);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorCategoria(
            @PathVariable Long idCategoria) {
        log.info("Solicitud para obtener reservas de la categoría con ID: {}", idCategoria);

        List<ReservaResponseDto> reservas = reservaService.obtenerReservasPorCategoria(idCategoria);
        return ResponseEntity.ok(reservas);
    }
}


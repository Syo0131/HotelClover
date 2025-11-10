package com.hotelclover.hotelclover.Controllers;

import com.hotelclover.hotelclover.Dtos.Common.PageResponse;
import com.hotelclover.hotelclover.Dtos.Habitacion.*;
import com.hotelclover.hotelclover.Models.Habitacion.EstadoHabitacion;
import com.hotelclover.hotelclover.Services.HabitacionService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService service;

    public HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HabitacionDto> crear(@Validated @RequestBody CrearHabitacionDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDto> actualizar(@PathVariable Long id,
                                                    @Validated @RequestBody ActualizarHabitacionDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<HabitacionDto>> buscar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) EstadoHabitacion estado,
            @RequestParam(required = false) BigDecimal minTarifa,
            @RequestParam(required = false) BigDecimal maxTarifa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pageable = PageRequest.of(page, size, Sort.by("numero").ascending());
        var result = service.buscar(categoriaId, estado, minTarifa, maxTarifa, pageable);
        return ResponseEntity.ok(PageResponse.of(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
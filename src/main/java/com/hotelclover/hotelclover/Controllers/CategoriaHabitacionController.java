package com.hotelclover.hotelclover.Controllers;

import com.hotelclover.hotelclover.DTOs.CategoriaHabitacion.*;
import com.hotelclover.hotelclover.DTOs.Common.PageResponse;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
import com.hotelclover.hotelclover.Services.CategoriaHabitacionService;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaHabitacionController {

    private final CategoriaHabitacionService service;

    public CategoriaHabitacionController(CategoriaHabitacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoriaHabitacionDto> crear(@Validated @RequestBody CrearCategoriaHabitacionDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaHabitacionDto> actualizar(@PathVariable Long id,
                                                             @Validated @RequestBody ActualizarCategoriaHabitacionDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaHabitacionDto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoriaHabitacionDto>> buscar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) EstadoCategoria estado,
            @RequestParam(required = false) BigDecimal minTarifa,
            @RequestParam(required = false) BigDecimal maxTarifa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        var result = service.buscar(q, estado, minTarifa, maxTarifa, pageable);
        return ResponseEntity.ok(PageResponse.of(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

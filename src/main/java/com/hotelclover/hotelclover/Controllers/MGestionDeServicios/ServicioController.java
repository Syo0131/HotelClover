package com.hotelclover.hotelclover.Controllers.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import com.hotelclover.hotelclover.Services.MGestionDeServicios.ServicioService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping
    public ResponseEntity<Servicio> create(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.createServicio(servicio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getById(@PathVariable Long id) {
        return servicioService.getServicioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Servicio>> getAll() {
        return ResponseEntity.ok(servicioService.getAllServicios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> update(@PathVariable Long id, @RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.updateServicio(id, servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicioService.deleteServicio(id);
        return ResponseEntity.noContent().build();
    }
}
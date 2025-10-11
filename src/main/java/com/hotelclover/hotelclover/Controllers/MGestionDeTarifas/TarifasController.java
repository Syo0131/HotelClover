package com.hotelclover.hotelclover.Controllers.MGestionDeTarifas;

import com.hotelclover.hotelclover.Dto.MGestionDeTarifas.TarifasDTO;
import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifa;
import com.hotelclover.hotelclover.Services.MGestionDeTarifas.TarifasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
@RequiredArgsConstructor
public class TarifasController {

    private final TarifasService tarifasService;

@PostMapping
    public ResponseEntity<Tarifa> createRate(@RequestBody TarifasDTO dto) {
        Tarifa nuevaTarifa = tarifasService.createRate(dto);
        return ResponseEntity.ok(nuevaTarifa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateRate(@PathVariable Long id, @RequestBody TarifasDTO dto) {
        Tarifa tarifaActualizada = tarifasService.updateRate(id, dto);
        return ResponseEntity.ok(tarifaActualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getRateById(@PathVariable Long id) {
        return tarifasService.getRateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Tarifa>> listbycategory(@RequestParam String categoria) {
        return ResponseEntity.ok(tarifasService.listByCategory(categoria));
    }

    @GetMapping("/temporada")
    public ResponseEntity<List<Tarifa>> listBySeason(@RequestParam String temporada) {
        return ResponseEntity.ok(tarifasService.listBySeason(temporada));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<Tarifa>> listByState(@RequestParam String estado) {
        return ResponseEntity.ok(tarifasService.listByStatus(estado));
    }

    @GetMapping("/noches")
    public ResponseEntity<List<Tarifa>> listByNumberOfNights(@RequestParam Integer noches) {
        return ResponseEntity.ok(tarifasService.listByNumberOfNights(noches));
    }
}
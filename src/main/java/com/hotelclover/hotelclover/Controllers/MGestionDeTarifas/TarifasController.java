package com.hotelclover.hotelclover.Controllers.MGestionDeTarifas;

import com.hotelclover.hotelclover.Dtos.TarifasDTO;
import com.hotelclover.hotelclover.Models.Tarifa;
import com.hotelclover.hotelclover.Models.TipoUsuario;
import com.hotelclover.hotelclover.Services.MGestionDeTarifas.TarifasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarifa")
@RequiredArgsConstructor
public class TarifasController {

    private final TarifasService tarifasService;

    @PostMapping("/create")
    public ResponseEntity<Tarifa> createRate(
            @Valid @RequestBody TarifasDTO dto,
            @RequestParam TipoUsuario userType) {
        Tarifa rate = tarifasService.createRate(dto, userType);
        return ResponseEntity.ok(rate);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Tarifa> updateRate(
            @PathVariable Long id,
            @Valid @RequestBody TarifasDTO dto,
            @RequestParam TipoUsuario userType) {
        Tarifa rate = tarifasService.updateRate(id, dto, userType);
        return ResponseEntity.ok(rate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getRateById(@PathVariable Long id) {
        Optional<Tarifa> rate = tarifasService.getRateById(id);
        return rate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/category")
    public ResponseEntity<List<Tarifa>> getRatesByCategory(@RequestParam String category) {
        return ResponseEntity.ok(tarifasService.getRatesByCategory(category));
    }

    @GetMapping("/season")
    public ResponseEntity<List<Tarifa>> getRatesBySeason(@RequestParam String season) {
        return ResponseEntity.ok(tarifasService.getRatesBySeason(season));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Tarifa>> getRatesByStatus(@RequestParam String status) {
        return ResponseEntity.ok(tarifasService.getRatesByStatus(status));
    }

    @GetMapping("/nights")
    public ResponseEntity<List<Tarifa>> getRatesByNumberOfNights(@RequestParam Integer nights) {
        return ResponseEntity.ok(tarifasService.getRatesByNumberOfNights(nights));
    }

    @GetMapping("/report")
    public ResponseEntity<List<Tarifa>> generateRateReport(
            @RequestParam TipoUsuario userType,
            @RequestParam(required = false) String roomCategory,
            @RequestParam(required = false) String season,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Tarifa> report = tarifasService.generateRateReport(userType, roomCategory, season, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
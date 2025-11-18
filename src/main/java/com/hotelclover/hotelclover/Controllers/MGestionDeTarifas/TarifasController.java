package com.hotelclover.hotelclover.Controllers.MGestionDeTarifas;

import com.hotelclover.hotelclover.Models.Tarifa;
import com.hotelclover.hotelclover.Services.MGestionDeTarifas.TarifasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifasController {

    private final TarifasService tarifasService;

    @PostMapping("/create")
    public ResponseEntity<Tarifa> createRate(@Valid @RequestBody Tarifa tarifa) {
        Tarifa saved = tarifasService.saveTarifa(tarifa);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tarifa> updateRate(@PathVariable Long id, @Valid @RequestBody Tarifa tarifa) {
        Tarifa updated = tarifasService.actualizarTarifa(id, tarifa);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/gestionar")
    public String listarTarifas(Model model) {
        List<Tarifa> tarifas = tarifasService.getAllTarifas();
        model.addAttribute("tarifas", tarifas);
        return "Tarifa/tarifa";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Tarifa tarifa = tarifasService.obtenerPorId(id);
        model.addAttribute("tarifa", tarifa);
        model.addAttribute("categorias", tarifasService.getAllCategorias());
        return "Tarifa/update";
    }

    @GetMapping("/report")
    public ResponseEntity<List<Tarifa>> generateRateReport(
            @RequestParam(required = false) String roomCategory,
            @RequestParam(required = false) String season,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Tarifa> report = tarifasService.generateRateReport(roomCategory, season, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
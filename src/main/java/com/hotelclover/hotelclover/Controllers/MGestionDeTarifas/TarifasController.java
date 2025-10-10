package com.hotelclover.hotelclover.Controllers.MGestionDeTarifas;

import com.hotelclover.hotelclover.Dto.MGestionDeTarifas.TarifasDTO;
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
    public ResponseEntity<TarifasDTO> createTariff(@RequestBody TarifasDTO dto) {
        TarifasDTO created = tarifasService.createTariff(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TarifasDTO>> getAllTariffs() {
        List<TarifasDTO> list = tarifasService.getAllTariffs();
        return ResponseEntity.ok(list);
    }

    // Get tariff by ID
    @GetMapping("/{id}")
    public ResponseEntity<TarifasDTO> getTariffById(@PathVariable Long id) {
        return tarifasService.getTariffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifasDTO> updateTariff(@PathVariable Long id, @RequestBody TarifasDTO dto) {
        return tarifasService.updateTariff(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTariff(@PathVariable Long id) {
        boolean deleted = tarifasService.deleteTariff(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

package com.hotelclover.hotelclover.Controllers.MGestionDeClientes.MAuditoria;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MAuditoria.AuditoriaCliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MAuditoria.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaRepository auditoriaRepository;

    @GetMapping
    public ResponseEntity<List<AuditoriaCliente>> listarTodas() {
        return ResponseEntity.ok(auditoriaRepository.findAll());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<AuditoriaCliente>> listarPorCliente(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaRepository.findByClienteId(id));
    }
}
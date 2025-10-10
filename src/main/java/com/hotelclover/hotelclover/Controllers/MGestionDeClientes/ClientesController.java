package com.hotelclover.hotelclover.Controllers.MGestionDeClientes;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Clientes;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @PostMapping
    public ResponseEntity<Clientes> create(@RequestBody Clientes client) {
        return ResponseEntity.ok(clientesService.createClient(client));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clientes> getById(@PathVariable Long id) {
        return clientesService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Clientes>> getAll() {
        return ResponseEntity.ok(clientesService.getAllClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clientes> update(@PathVariable Long id, @RequestBody Clientes client) {
        return ResponseEntity.ok(clientesService.updateClient(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientesService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

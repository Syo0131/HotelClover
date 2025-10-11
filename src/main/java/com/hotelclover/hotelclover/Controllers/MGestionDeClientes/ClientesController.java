package com.hotelclover.hotelclover.Controllers.MGestionDeClientes;

import com.hotelclover.hotelclover.Dto.MGestionDeClientes.ClientesDTO;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.ClientesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientService;

    @PostMapping("/register")
    public Cliente registerClient(@Valid @RequestBody ClientesDTO dto) {
        return clientService.registerClient(dto);
    }

    @PutMapping("/{id}")
    public Cliente updateClient(@PathVariable Long id, @Valid @RequestBody ClientesDTO dto) {
        return clientService.updateClient(id, dto);
    }

    @GetMapping("/{id}")
    public Optional<Cliente> getClient(@PathVariable Long id) {
        return clientService.getClientById(id);
    }
}
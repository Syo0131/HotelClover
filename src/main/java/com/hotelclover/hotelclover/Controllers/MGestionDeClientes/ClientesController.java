package com.hotelclover.hotelclover.Controllers.MGestionDeClientes;

import com.hotelclover.hotelclover.Dto.MGestionDeClientes.ClientesDTO;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.ClientesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientService;

    @PostMapping("/registro-cliente")
    public String procesarFormulario(@Valid @ModelAttribute("cliente") ClientesDTO dto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            clientService.registerClient(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "error.cliente", ex.getMessage());
            return "register";
        }

        return "redirect:/login";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Cliente updateClient(@PathVariable Long id, @Valid @RequestBody ClientesDTO dto) {
        return clientService.updateClient(id, dto);
    }

    @GetMapping("/{id:\\d+}")
    @ResponseBody
    public Optional<Cliente> getClient(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/registro-cliente-form")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new ClientesDTO());
        return "register";
    }
}
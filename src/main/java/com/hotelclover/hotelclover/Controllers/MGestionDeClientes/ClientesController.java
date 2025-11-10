package com.hotelclover.hotelclover.Controllers.MGestionDeClientes;

import com.hotelclover.hotelclover.Dtos.UsuarioDTO;
import com.hotelclover.hotelclover.Models.Usuario;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.ClientesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientService;

    @PostMapping("/registro-cliente")
    public String procesarFormulario(@Valid @ModelAttribute("cliente") UsuarioDTO dto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            clientService.validarEmailDuplicado(dto.getEmail(), null);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "error.cliente", ex.getMessage());
            return "register";
        }
        try {
            clientService.registerClient(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "error.cliente", ex.getMessage());
            return "register";
        }
        return "redirect:/api/clientes/login";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Usuario updateClient(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        return clientService.updateClient(id, dto);
    }

    @GetMapping("/{id:\\d+}")
    @ResponseBody
    public Usuario getClient(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/registro-cliente-form")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new UsuarioDTO());
        return "register";
    }

    @GetMapping("/update/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Usuario cliente = clientService.getClientById(id);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        dto.setDireccion(cliente.getDireccion());

        model.addAttribute("cliente", dto);
        return "update";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarCliente(@PathVariable Long id,
            @Valid @ModelAttribute("cliente") UsuarioDTO dto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "update";
        }

        try {
            clientService.updateClient(id, dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "error.cliente", ex.getMessage());
            return "update";
        }

        return "redirect:/dashboard";
    }
}
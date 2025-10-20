package com.hotelclover.hotelclover.Controllers;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @Autowired
    private ClientesRepository clientesrepository;

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model, Principal principal) {
        System.out.println("Email autenticado: " + principal.getName());

        Cliente cliente = clientesrepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        model.addAttribute("cliente", cliente);
        return "dashboard";
    }
}
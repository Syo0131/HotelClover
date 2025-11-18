package com.hotelclover.hotelclover.Controllers;

import com.hotelclover.hotelclover.Models.TipoClientes;
import com.hotelclover.hotelclover.Models.Clientes;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        Clientes cliente = (Clientes) session.getAttribute("cliente");

        if (cliente == null) {
            return "redirect:/api/clientes/login";
        }
        if (cliente.getTipoUsuario() != TipoClientes.CLIENTE) {
            return "redirect:/dashboardAdministrativo";
        }
        model.addAttribute("cliente", cliente);
        return "dashboard";
    }

    @GetMapping("/dashboardAdministrativo")
    public String mostrarDashboardAdminRecepcionista(HttpSession session, Model model) {
        Clientes cliente = (Clientes) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/api/clientes/login";
        }
        if (cliente.getTipoUsuario() != TipoClientes.ADMINISTRADOR &&
                cliente.getTipoUsuario() != TipoClientes.RECEPCIONISTA) {
            return "redirect:/dashboard";
        }
        model.addAttribute("cliente", cliente);
        return "dashboardAdministrativo";
    }
}
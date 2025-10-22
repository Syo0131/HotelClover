package com.hotelclover.hotelclover.Controllers;

import com.hotelclover.hotelclover.Models.TipoUsuario;
import com.hotelclover.hotelclover.Models.Usuario;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        Usuario cliente = (Usuario) session.getAttribute("cliente");

        if (cliente == null) {
            return "redirect:/api/clientes/login";
        }
        if (cliente.getTipoUsuario() != TipoUsuario.CLIENTE) {
            return "redirect:/dashboardAdministrativo";
        }
        model.addAttribute("cliente", cliente);
        return "dashboard";
    }

    @GetMapping("/dashboardAdministrativo")
    public String mostrarDashboardAdminRecepcionista(HttpSession session, Model model) {
        Usuario cliente = (Usuario) session.getAttribute("cliente");
        if (cliente == null) {
            return "redirect:/api/clientes/login";
        }
        if (cliente.getTipoUsuario() != TipoUsuario.ADMINISTRADOR &&
                cliente.getTipoUsuario() != TipoUsuario.RECEPCIONISTA) {
            return "redirect:/dashboard";
        }
        model.addAttribute("cliente", cliente);
        return "dashboardAdministrativo";
    }
}
package com.hotelclover.hotelclover.Controllers.MGestionDeServicios;

import com.hotelclover.hotelclover.Dto.MGestionDeServicios.ServicioDTO;
import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import com.hotelclover.hotelclover.Services.MGestionDeServicios.ServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // ===== THYMELEAF =====

    @GetMapping("/registro-servicio-form")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("servicio", new ServicioDTO());
        return "servicio-registro";
    }

    @PostMapping("/servicio-registro")
    public String procesarFormulario(@Valid @ModelAttribute("servicio") ServicioDTO dto,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "servicio-registro";
        }
        try {
            servicioService.registerServicio(dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("nombre", "error.servicio", ex.getMessage());
            return "servicio-registro";
        }
        return "redirect:/api/servicios/lista";
    }

    @GetMapping("/update/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        ServicioDTO dto = servicioService.entityToDto(servicioService.getServicioById(id));
        model.addAttribute("servicio", dto);
        return "servicio-update";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarServicio(@PathVariable Long id,
                                     @Valid @ModelAttribute("servicio") ServicioDTO dto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "servicio-update";
        }
        try {
            servicioService.updateServicio(id, dto);
        } catch (IllegalArgumentException ex) {
            result.rejectValue("nombre", "error.servicio", ex.getMessage());
            return "servicio-update";
        }
        return "redirect:/api/servicios/lista";
    }

    @GetMapping("/lista")
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioService.getAllServicios());
        return "servicio-lista";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        servicioService.deleteServicio(id);
        return "redirect:/api/servicios/lista";
    }

    // ===== REST API =====

    @GetMapping("/{id}")
    @ResponseBody
    public ServicioDTO getServicio(@PathVariable Long id) {
        return servicioService.entityToDto(servicioService.getServicioById(id));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ServicioDTO updateServicioRest(@PathVariable Long id, @Valid @RequestBody ServicioDTO dto) {
        Servicio updated = servicioService.updateServicio(id, dto);
        return servicioService.entityToDto(updated);
    }
}

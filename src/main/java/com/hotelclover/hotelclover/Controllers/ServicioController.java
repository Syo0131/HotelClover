package com.hotelclover.hotelclover.Controllers;

import com.hotelclover.hotelclover.Dtos.ServicioDTO;
import com.hotelclover.hotelclover.Models.Servicio;
import com.hotelclover.hotelclover.Services.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de servicios.
 * Integra endpoints REST y vistas Thymeleaf.
 */
@Controller
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // =====================================================
    // 🟢 SECCIÓN THYMELEAF (VISTAS HTML)
    // =====================================================

    /** Muestra el formulario de registro de un nuevo servicio */
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("servicio", new ServicioDTO());
        return "servicio-registro";
    }

    /** Procesa el formulario para registrar un nuevo servicio */
    @PostMapping("/guardar")
    public String registrarServicio(@Valid @ModelAttribute("servicio") ServicioDTO dto,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            return "servicio-registro";
        }

        try {
            servicioService.registerServicio(dto);
            return "redirect:/api/servicios/lista";
        } catch (IllegalArgumentException ex) {
            result.rejectValue("nombre", "error.servicio", ex.getMessage());
            return "servicio-registro";
        }
    }

    /** Muestra el formulario para editar un servicio existente */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Servicio servicio = servicioService.getServicioById(id);
        model.addAttribute("servicio", servicioService.entityToDto(servicio));
        return "servicio-update";
    }

    /** Procesa la actualización de un servicio */
    @PostMapping("/actualizar/{id}")
    public String actualizarServicio(@PathVariable Long id,
                                     @Valid @ModelAttribute("servicio") ServicioDTO dto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "servicio-update";
        }

        try {
            servicioService.updateServicio(id, dto);
            return "redirect:/api/servicios/lista";
        } catch (IllegalArgumentException ex) {
            result.rejectValue("nombre", "error.servicio", ex.getMessage());
            return "servicio-update";
        }
    }

    /** Lista todos los servicios en una vista Thymeleaf */
    @GetMapping("/lista")
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioService.getAllServicios());
        return "servicio-lista";
    }

    /** Elimina un servicio desde la vista */
    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        servicioService.deleteServicio(id);
        return "redirect:/api/servicios/lista";
    }

    // =====================================================
    // 🔵 SECCIÓN REST API (RESPUESTAS JSON)
    // =====================================================

    /** Obtiene un servicio por ID */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Long id) {
        Servicio servicio = servicioService.getServicioById(id);
        return ResponseEntity.ok(servicioService.entityToDto(servicio));
    }

    /** Lista todos los servicios (formato JSON) */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ServicioDTO>> listarServiciosRest() {
        return ResponseEntity.ok(servicioService.getAllServicios());
    }

    /** Crea un nuevo servicio (REST API) */
    @PostMapping
    @ResponseBody
    public ResponseEntity<ServicioDTO> crearServicio(@Valid @RequestBody ServicioDTO dto) {
        Servicio nuevo = servicioService.registerServicio(dto);
        return ResponseEntity.ok(servicioService.entityToDto(nuevo));
    }

    /** Actualiza un servicio existente (REST API) */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ServicioDTO> actualizarServicioRest(@PathVariable Long id,
                                                              @Valid @RequestBody ServicioDTO dto) {
        Servicio actualizado = servicioService.updateServicio(id, dto);
        return ResponseEntity.ok(servicioService.entityToDto(actualizado));
    }

    /** Elimina un servicio (REST API) */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> eliminarServicioRest(@PathVariable Long id) {
        servicioService.deleteServicio(id);
        return ResponseEntity.noContent().build();
    }
}

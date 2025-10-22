package com.hotelclover.hotelclover.Controllers.MGestionDeTarifas;

import com.hotelclover.hotelclover.Models.Tarifa;
import com.hotelclover.hotelclover.Services.MGestionDeTarifas.TarifasService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifasController {

    private final TarifasService tarifasService;

    @GetMapping("/crear")
    public String mostrarFormularioTarifa(Model model) {
        model.addAttribute("tarifa", new Tarifa());
        return "Tarifa/create";
    }

    @PostMapping("/crear")
    public String guardarTarifa(@ModelAttribute("tarifa") @Valid Tarifa tarifa) {
        tarifasService.saveTarifa(tarifa);
        return "redirect:/tarifas/gestionar";
    }

    @GetMapping("/gestionar")
    public String listarTarifas(Model model) {
        List<Tarifa> tarifas = tarifasService.getAllTarifas();
        model.addAttribute("tarifas", tarifas);
        return "Tarifa/tarifa"; 
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Tarifa tarifa = tarifasService.obtenerPorId(id);
        model.addAttribute("tarifa", tarifa);
        return "Tarifa/update";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarTarifa(@PathVariable Long id, @ModelAttribute("tarifa") @Valid Tarifa tarifa) {
        tarifasService.actualizarTarifa(id, tarifa);
        return "redirect:/tarifas/gestionar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTarifa(@PathVariable Long id) {
        tarifasService.deleteTarifa(id);
        return "redirect:/tarifas/gestionar";
    }
}
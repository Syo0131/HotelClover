package com.hotelclover.hotelclover.Services.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import com.hotelclover.hotelclover.Repositories.MGestionDeServicios.ServicioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public Servicio createServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Optional<Servicio> getServicioById(Long id) {
        return servicioRepository.findById(id);
    }

    public Iterable<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public void deleteServicio(Long id) {
        servicioRepository.deleteById(id);
    }

    public Servicio updateServicio(Long id, Servicio updatedServicio) {
        return servicioRepository.findById(id).map(servicio -> {
            servicio.setNombre(updatedServicio.getNombre());
            servicio.setActivo(updatedServicio.isActivo());
            servicio.setPrecioBase(updatedServicio.getPrecioBase());
            return servicioRepository.save(servicio);
        }).orElseThrow(() -> new RuntimeException("Servicio not found"));
    }
}
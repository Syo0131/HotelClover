package com.hotelclover.hotelclover.Services.MGestionDeServicios;

import com.hotelclover.hotelclover.Dto.MGestionDeServicios.ServicioDTO;
import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import com.hotelclover.hotelclover.Models.MGestionDeServicios.TipoServicio;
import com.hotelclover.hotelclover.Repositories.MGestionDeServicios.ServicioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public Servicio registerServicio(ServicioDTO dto) {
        validarNombreDuplicado(dto.getNombre(), null);

        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());
        servicio.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        servicio.setPrecioBase(dto.getPrecioBase());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setTipoServicio(dto.getTipoServicio() != null ? dto.getTipoServicio() : TipoServicio.OTROS);

        if (dto.getPrecioBase() == null || dto.getPrecioBase() <= 0) {
            throw new IllegalArgumentException("El precio base es obligatorio y debe ser mayor a 0");
        }

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }

        return servicioRepository.save(servicio);
    }

    public Servicio updateServicio(Long id, ServicioDTO dto) {
        Servicio original = getServicioById(id);

        validarNombreDuplicado(dto.getNombre(), id);

        original.setNombre(dto.getNombre());
        original.setActivo(dto.getActivo() != null ? dto.getActivo() : original.isActivo());
        original.setPrecioBase(dto.getPrecioBase());

        if (dto.getDescripcion() != null && !dto.getDescripcion().isBlank()) {
            original.setDescripcion(dto.getDescripcion());
        }

        if (dto.getTipoServicio() != null) {
            original.setTipoServicio(dto.getTipoServicio());
        }

        return servicioRepository.save(original);
    }

    public Servicio getServicioById(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
    }

    public Iterable<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public void deleteServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado");
        }
        servicioRepository.deleteById(id);
    }

    public void validarNombreDuplicado(String nombre, Long idActual) {
        servicioRepository.findByNombre(nombre).ifPresent(servicioExistente -> {
            if (idActual == null || !servicioExistente.getIdServicio().equals(idActual)) {
                throw new IllegalArgumentException("Ya existe un servicio con ese nombre.");
            }
        });
    }
}
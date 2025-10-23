package com.hotelclover.hotelclover.Services.MGestionDeServicios;

import com.hotelclover.hotelclover.Dto.MGestionDeServicios.ServicioDTO;
import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import com.hotelclover.hotelclover.Repositories.MGestionDeServicios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Registrar nuevo servicio
    public Servicio registerServicio(ServicioDTO dto) {
        validarNombreDuplicado(dto.getNombre(), null);
        Servicio servicio = dtoToEntity(dto);
        return servicioRepository.save(servicio);
    }

    // Actualizar servicio existente
    public Servicio updateServicio(Long id, ServicioDTO dto) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        validarNombreDuplicado(dto.getNombre(), id);

        servicio.setNombre(dto.getNombre());
        servicio.setActivo(dto.getActivo());
        servicio.setPrecioBase(dto.getPrecioBase());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setTipoServicio(dto.getTipoServicio());

        return servicioRepository.save(servicio);
    }

    // Obtener un servicio por ID
    public Servicio getServicioById(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
    }

    // Obtener todos los servicios
    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    // Eliminar servicio
    public void deleteServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException("Servicio no encontrado");
        }
        servicioRepository.deleteById(id);
    }

    // Validar nombre duplicado
    public void validarNombreDuplicado(String nombre, Long idActual) {
        Optional<Servicio> existente = servicioRepository.findByNombre(nombre);
        if (existente.isPresent() && (idActual == null || !existente.get().getIdServicio().equals(idActual))) {
            throw new IllegalArgumentException("Ya existe un servicio con ese nombre");
        }
    }

    // Conversión DTO -> Entity
    private Servicio dtoToEntity(ServicioDTO dto) {
        Servicio s = new Servicio();
        s.setNombre(dto.getNombre());
        s.setActivo(dto.getActivo());
        s.setPrecioBase(dto.getPrecioBase());
        s.setDescripcion(dto.getDescripcion());
        s.setTipoServicio(dto.getTipoServicio());
        return s;
    }

    // Conversión Entity -> DTO
    public ServicioDTO entityToDto(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(servicio.getIdServicio());
        dto.setNombre(servicio.getNombre());
        dto.setActivo(servicio.isActivo());
        dto.setPrecioBase(servicio.getPrecioBase());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setTipoServicio(servicio.getTipoServicio());
        return dto;
    }
}

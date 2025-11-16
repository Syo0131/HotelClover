package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Dtos.ServicioDTO;
import com.hotelclover.hotelclover.Models.Servicio;
import com.hotelclover.hotelclover.Repositories.ServicioRepository;
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

    /**
     * Registrar un nuevo servicio
     */
    public Servicio registerServicio(ServicioDTO dto) {
        validarNombreDuplicado(dto.getNombre(), null);
        Servicio servicio = dtoToEntity(dto);
        return servicioRepository.save(servicio);
    }

    /**
     * Actualizar servicio existente
     */
    public Servicio updateServicio(Long id, ServicioDTO dto) {
        Servicio servicio = servicioRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));


        validarNombreDuplicado(dto.getNombre(), id);

        servicio.setNombre(dto.getNombre());
        servicio.setEstado(dto.getEstado());
        servicio.setPrecioBase(dto.getPrecioBase());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setTipoServicio(dto.getTipoServicio());

        return servicioRepository.save(servicio);
    }

    /**
     * Obtener servicio por ID
     */
    public Servicio getServicioById(Long id) {
        return servicioRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + id));

    }

    /**
     * Listar todos los servicios
     */
    public List<ServicioDTO> getAllServicios() {
        return ((List<Servicio>) servicioRepository.findAll())
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Eliminar servicio
     */
    public void deleteServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException("El servicio con ID " + id + " no existe");
        }
        servicioRepository.deleteById(id);
    }

    /**
     * Validar que no exista otro servicio con el mismo nombre
     */
    public void validarNombreDuplicado(String nombre, Long idActual) {
        Optional<Servicio> existente = servicioRepository.findByNombre(nombre);
        if (existente.isPresent() &&
            (idActual == null || !existente.get().getIdServicio().equals(idActual))) {
            throw new IllegalArgumentException("Ya existe un servicio con el nombre: " + nombre);
        }
    }

    /**
     * Conversión DTO → Entity
     */
    private Servicio dtoToEntity(ServicioDTO dto) {
        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());
        servicio.setEstado(dto.getEstado());
        servicio.setPrecioBase(dto.getPrecioBase());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setTipoServicio(dto.getTipoServicio());
        return servicio;
    }

    /**
     * Conversión Entity → DTO
     */
    public ServicioDTO entityToDto(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(servicio.getIdServicio());
        dto.setNombre(servicio.getNombre());
        dto.setEstado(servicio.isEstado());
        dto.setPrecioBase(servicio.getPrecioBase());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setTipoServicio(servicio.getTipoServicio());
        dto.setFechaCreacion(servicio.getFechaCreacion());
        dto.setFechaActualizacion(servicio.getFechaActualizacion());
        return dto;
    }
}

package com.hotelclover.hotelclover.Repositories.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicioRepository extends CrudRepository<Servicio, Long> {

    Optional<Servicio> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    Optional<Servicio> findByIdServicio(Long idServicio);
}
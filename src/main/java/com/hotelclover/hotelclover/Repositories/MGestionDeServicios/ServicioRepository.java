package com.hotelclover.hotelclover.Repositories.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Optional<Servicio> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
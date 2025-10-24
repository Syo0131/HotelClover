package com.hotelclover.hotelclover.Repositories;

import com.hotelclover.hotelclover.Models.Servicio;
import com.hotelclover.hotelclover.Models.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Optional<Servicio> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Servicio> findByEstadoTrue();

    List<Servicio> findByTipoServicio(TipoServicio tipoServicio);
}

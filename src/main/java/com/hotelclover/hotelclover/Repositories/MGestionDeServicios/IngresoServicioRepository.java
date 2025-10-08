package com.hotelclover.hotelclover.Repositories.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.IngresoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IngresoServicioRepository extends JpaRepository<IngresoServicio, Long> {

    List<IngresoServicio> findByIdServicio(Long idServicio);

    List<IngresoServicio> findByIdCategoria(Long idCategoria);

    List<IngresoServicio> findByPeriodo(LocalDate periodo);
}

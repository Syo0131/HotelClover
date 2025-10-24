package com.hotelclover.hotelclover.Repositories;

import com.hotelclover.hotelclover.Models.IngresoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IngresoServicioRepository extends JpaRepository<IngresoServicio, Long> {

    List<IngresoServicio> findByServicio_IdServicio(Long idServicio);

    List<IngresoServicio> findByIdCategoria(Long idCategoria);

    List<IngresoServicio> findByPeriodo(LocalDate periodo);

    List<IngresoServicio> findByPeriodoBetween(LocalDate inicio, LocalDate fin);
}

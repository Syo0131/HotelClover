package com.hotelclover.hotelclover.Repositories.MGestionDeTarifas;

import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifasRepository extends JpaRepository<Tarifas, Long> {

    List<Tarifas> findByCategoriaHabitacion(String categoriaHabitacion);

    List<Tarifas> findByTemporada(String temporada);

    List<Tarifas> findByEstadoTarifa(String estadoTarifa);

    List<Tarifas> findByNumeroNoches(Integer numeroNoches);
}

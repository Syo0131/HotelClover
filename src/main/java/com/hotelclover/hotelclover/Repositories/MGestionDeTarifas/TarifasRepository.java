package com.hotelclover.hotelclover.Repositories.MGestionDeTarifas;

import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface TarifasRepository extends CrudRepository<Tarifas, Long> {

    List<Tarifas> findByCategoriaHabitacion(String categoriaHabitacion);

    List<Tarifas> findByTemporada(String temporada);

    List<Tarifas> findByEstadoTarifa(String estadoTarifa);

    List<Tarifas> findByNumeroNoches(Integer numeroNoches);
}

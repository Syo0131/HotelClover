package com.hotelclover.hotelclover.Repositories.MGestionDeTarifas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelclover.hotelclover.Models.Tarifa;

import java.util.List;

@Repository
    public interface TarifasRepository extends CrudRepository<Tarifa, Long> {

    List<Tarifa> findByCategoriaHabitacion(String categoriaHabitacion);

    List<Tarifa> findByTemporada(String temporada);

    List<Tarifa> findByEstadoTarifa(String estadoTarifa);

    List<Tarifa> findByNumeroNoches(Integer numeroNoches);
}

package com.hotelclover.hotelclover.Repositories.MGestionDeTarifas;

import com.hotelclover.hotelclover.Models.MGestionDeTarifas.Tarifa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface TarifasRepository extends CrudRepository<Tarifa, Long> {

    List<Tarifa> findByCategoriaHabitacion(String categoriaHabitacion);

    List<Tarifa> findByTemporada(String temporada);

    List<Tarifa> findByEstadoTarifa(String estadoTarifa);

    List<Tarifa> findByNumeroNoches(Integer numeroNoches);
}

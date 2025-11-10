package com.hotelclover.hotelclover.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotelclover.hotelclover.Models.Reserva;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Métodos de búsqueda personalizados
    List<Reserva> findByCliente_Id(Long id);

    List<Reserva> findByCategoriaHabitacion_IdCategoriaHabitacion(Long idCategoria);

    List<Reserva> findByEntryDateBetween(Date startDate, Date endDate);
}

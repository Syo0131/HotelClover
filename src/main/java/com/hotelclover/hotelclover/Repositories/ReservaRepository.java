package com.hotelclover.hotelclover.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hotelclover.hotelclover.Models.Reserva;
   


@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
    
}

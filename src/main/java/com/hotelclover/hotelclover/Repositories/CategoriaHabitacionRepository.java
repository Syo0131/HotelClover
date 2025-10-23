package com.hotelclover.hotelclover.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;

@Repository
public interface CategoriaHabitacionRepository extends JpaRepository<CategoriaHabitacion, Long> {
}

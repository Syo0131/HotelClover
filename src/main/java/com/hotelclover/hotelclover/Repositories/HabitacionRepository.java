package com.hotelclover.hotelclover.Repositories;

import com.hotelclover.hotelclover.Models.Habitacion;
import com.hotelclover.hotelclover.Models.Habitacion.EstadoHabitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    
    long countByCategoria_IdCategoriaHabitacion(Long categoriaId);

    
    @Query("""
      SELECT h FROM Habitacion h
      JOIN h.categoria c
      WHERE (:categoriaId IS NULL OR c.idCategoriaHabitacion = :categoriaId)
        AND (:estado IS NULL OR h.estado = :estado)
        AND (:minTarifa IS NULL OR COALESCE(h.tarifaNoche, c.tarifaNoche) >= :minTarifa)
        AND (:maxTarifa IS NULL OR COALESCE(h.tarifaNoche, c.tarifaNoche) <= :maxTarifa)
      """)
    Page<Habitacion> search(
        @Param("categoriaId") Long categoriaId,
        @Param("estado") EstadoHabitacion estado,
        @Param("minTarifa") BigDecimal minTarifa,
        @Param("maxTarifa") BigDecimal maxTarifa,
        Pageable pageable
    );

    
    boolean existsByNumeroIgnoreCase(String numero);
}

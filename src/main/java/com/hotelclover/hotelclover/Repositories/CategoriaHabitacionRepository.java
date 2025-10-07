package com.hotelclover.hotelclover.Repositories;

import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CategoriaHabitacionRepository extends JpaRepository<CategoriaHabitacion, Long> {

    
    boolean existsByNombreIgnoreCase(String nombre);

    
    boolean existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(String nombre, Long idCategoriaHabitacion);

    
    @Query("""
        SELECT c FROM CategoriaHabitacion c
        WHERE (:estado IS NULL OR c.estado = :estado)
          AND (:minTarifa IS NULL OR c.tarifaNoche >= :minTarifa)
          AND (:maxTarifa IS NULL OR c.tarifaNoche <= :maxTarifa)
          AND (
                :q IS NULL
             OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(c.caracteristicas) LIKE LOWER(CONCAT('%', :q, '%'))
          )
        """)
    Page<CategoriaHabitacion> search(
            @Param("q") String q,
            @Param("estado") EstadoCategoria estado,
            @Param("minTarifa") BigDecimal minTarifa,
            @Param("maxTarifa") BigDecimal maxTarifa,
            Pageable pageable
    );
}
package com.hotelclover.hotelclover.Repositories.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.ServicioCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioCategoriaRepository extends JpaRepository<ServicioCategoria, Long> {

    List<ServicioCategoria> findByServicio_IdServicio(Long idServicio);

    List<ServicioCategoria> findByIdCategoria(Long idCategoria);

    List<ServicioCategoria> findByServicio_IdServicioAndIdCategoria(Long idServicio, Long idCategoria);
}

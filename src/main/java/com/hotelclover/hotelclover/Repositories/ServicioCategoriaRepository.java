package com.hotelclover.hotelclover.Repositories;

import com.hotelclover.hotelclover.Models.ServicioCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioCategoriaRepository extends JpaRepository<ServicioCategoria, Long> {

    List<ServicioCategoria> findByServicio_IdServicio(Long idServicio);

    List<ServicioCategoria> findByIdCategoria(Long idCategoria);
}

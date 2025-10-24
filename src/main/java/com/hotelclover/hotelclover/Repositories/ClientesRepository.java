package com.hotelclover.hotelclover.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotelclover.hotelclover.Models.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
}

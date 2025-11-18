package com.hotelclover.hotelclover.Repositories.MGestionDeClientes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelclover.hotelclover.Models.Clientes;

import java.util.Optional;

@Repository
public interface ClientesRepository extends CrudRepository<Clientes, Long> {

    Optional<Clientes> findByNombre(String nombre);

    Optional<Clientes> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Clientes> findByTelefono(String telefono);
}
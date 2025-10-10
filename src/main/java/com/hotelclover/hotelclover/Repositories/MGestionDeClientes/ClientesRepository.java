package com.hotelclover.hotelclover.Repositories.MGestionDeClientes;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Clientes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientesRepository extends CrudRepository<Clientes, Long> {
    
    Optional<Clientes> findByUsername(String nombre);

    Optional<Clientes> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Clientes> findByTelefono(String telefono);
}
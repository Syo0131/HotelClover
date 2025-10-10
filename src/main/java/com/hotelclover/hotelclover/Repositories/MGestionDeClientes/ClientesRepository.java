package com.hotelclover.hotelclover.Repositories.MGestionDeClientes;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientesRepository extends CrudRepository<Cliente, Long> {
    
    Optional<Cliente> findByUsername(String nombre);

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Cliente> findByTelefono(String telefono);
}
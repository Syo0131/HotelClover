package com.hotelclover.hotelclover.Repositories.MGestionDeClientes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelclover.hotelclover.Models.Usuario;

import java.util.Optional;

@Repository
public interface ClientesRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Usuario> findByTelefono(String telefono);
}
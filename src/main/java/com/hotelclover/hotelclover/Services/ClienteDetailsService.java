package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Models.Usuario;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteDetailsService implements UserDetailsService {

    @Autowired
    private ClientesRepository clientesRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Autenticando cliente: " + email);

        Usuario cliente = clientesRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no registrado"));
        return new User(
                cliente.getEmail(),
                cliente.getContrasena(),
                List.of(new SimpleGrantedAuthority(cliente.getTipoUsuario().name())));
    }
}
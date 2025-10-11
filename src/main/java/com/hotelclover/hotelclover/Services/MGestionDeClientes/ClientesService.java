package com.hotelclover.hotelclover.Services.MGestionDeClientes;

import com.hotelclover.hotelclover.Dto.MGestionDeClientes.ClientesDTO;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente registerClient(ClientesDTO dto) {
        if (clientesRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Cliente client = new Cliente();
        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        client.setTelefono(dto.getTelefono());
        client.setFechaNacimiento(dto.getFechaNacimiento());
        client.setTipoUsuario(com.hotelclover.hotelclover.Models.MGestionDeClientes.TipoUsuario.CLIENTE);

        return clientesRepository.save(client);
    }

    public Cliente updateClient(Long id, ClientesDTO dto) {
        Cliente client = clientesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setTelefono(dto.getTelefono());
        client.setFechaNacimiento(dto.getFechaNacimiento());
        client.setContrasena(passwordEncoder.encode(dto.getContrasena()));

        return clientesRepository.save(client);
    }

    public Optional<Cliente> getClientById(Long id) {
        return clientesRepository.findById(id);
    }
}

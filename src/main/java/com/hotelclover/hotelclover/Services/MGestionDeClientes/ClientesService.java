package com.hotelclover.hotelclover.Services.MGestionDeClientes;

import com.hotelclover.hotelclover.Dto.MGestionDeClientes.ClientesDTO;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientesService {

    private final ClientesRepository clientesRepository;
    private final PasswordEncoder passwordEncoder;

    public Cliente registerClient(ClientesDTO dto) {
        validarEmailDuplicado(dto.getEmail(), null);

        Cliente client = new Cliente();
        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        client.setTelefono(dto.getTelefono());
        client.setFechaNacimiento(dto.getFechaNacimiento());
        client.setDireccion(dto.getDireccion());
        client.setTipoUsuario(com.hotelclover.hotelclover.Models.MGestionDeClientes.TipoUsuario.CLIENTE);

        if (dto.getContrasena() == null || dto.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (dto.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }

        return clientesRepository.save(client);
    }

    public Cliente updateClient(Long id, ClientesDTO dto) {
        Cliente original = getClientById(id);

        validarEmailDuplicado(dto.getEmail(), id);

        original.setNombre(dto.getNombre());
        original.setApellido(dto.getApellido());
        original.setEmail(dto.getEmail());
        original.setTelefono(dto.getTelefono());

        if (dto.getFechaNacimiento() != null) {
            original.setFechaNacimiento(dto.getFechaNacimiento());
        }

        if (dto.getDireccion() != null && !dto.getDireccion().isBlank()) {
            original.setDireccion(dto.getDireccion());
        }

        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            original.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }

        return clientesRepository.save(original);
    }

    public Cliente getClientById(Long id) {
        return clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void validarEmailDuplicado(String email, Long idActual) {
        clientesRepository.findByEmail(email).ifPresent(clienteExistente -> {
            if (idActual == null || !clienteExistente.getId().equals(idActual)) {
                throw new IllegalArgumentException("Ya existe un cliente con ese correo electrónico.");
            }
        });
    }
}
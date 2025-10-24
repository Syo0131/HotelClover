package com.hotelclover.hotelclover.Services.MGestionDeClientes;

import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;
import com.hotelclover.hotelclover.Dtos.UsuarioDTO;
import com.hotelclover.hotelclover.Models.TipoUsuario;
import com.hotelclover.hotelclover.Models.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientesService {

    private final ClientesRepository clientesRepository;

    private final PasswordEncoder passwordEncoder;

    public Usuario registerClient(UsuarioDTO dto) {
        validarEmailDuplicado(dto.getEmail(), null);

        Usuario client = new Usuario();
        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        client.setTelefono(dto.getTelefono());
        client.setFechaNacimiento(dto.getFechaNacimiento());
        client.setDireccion(dto.getDireccion());
        client.setTipoUsuario(TipoUsuario.CLIENTE);

        if (dto.getContrasena() == null || dto.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (dto.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }

        return clientesRepository.save(client);
    }

    public Usuario updateClient(Long id, UsuarioDTO dto) {
        Usuario original = getClientById(id);

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

    public Usuario getClientById(Long id) {
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

    public Usuario autenticar(String email, String contrasena) {
        return clientesRepository.findByEmail(email)
                .filter(cliente -> passwordEncoder.matches(contrasena, cliente.getContrasena()))
                .orElse(null);
    }
}
package com.hotelclover.hotelclover;

import com.hotelclover.hotelclover.Dtos.UsuarioDTO;
import com.hotelclover.hotelclover.Models.TipoUsuario;
import com.hotelclover.hotelclover.Models.Usuario;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.ClientesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientesServiceTest {

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientesService clientesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterClientSuccess() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Ana");
        dto.setApellido("Ramírez");
        dto.setEmail("ana@example.com");
        dto.setContrasena("123456");
        dto.setTelefono("8091234567");
        dto.setDireccion("Calle 1");
        dto.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(passwordEncoder.encode("123456")).thenReturn("encoded123");
        when(clientesRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Usuario result = clientesService.registerClient(dto);

        assertEquals("Ana", result.getNombre());
        assertEquals("encoded123", result.getContrasena());
        assertEquals(TipoUsuario.CLIENTE, result.getTipoUsuario());
    }

    @Test
    void testRegisterClientFailsWithoutPassword() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("ana@example.com");
        dto.setContrasena("");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            clientesService.registerClient(dto);
        });

        assertEquals("La contraseña es obligatoria", ex.getMessage());
    }

    @Test
    void testValidarEmailDuplicadoThrowsException() {
        Usuario existing = new Usuario();
        existing.setId(1L);
        existing.setEmail("ana@example.com");

        when(clientesRepository.findByEmail("ana@example.com")).thenReturn(Optional.of(existing));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            clientesService.validarEmailDuplicado("ana@example.com", null);
        });

        assertEquals("Ya existe un cliente con ese correo electrónico.", ex.getMessage());
    }

    @Test
    void testUpdateClientSuccess() {
        Usuario original = new Usuario();
        original.setId(1L);
        original.setEmail("old@example.com");
        original.setContrasena("oldpass");

        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("new@example.com");
        dto.setContrasena("newpass");

        when(clientesRepository.findById(1L)).thenReturn(Optional.of(original));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNew");
        when(clientesRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Usuario updated = clientesService.updateClient(1L, dto);

        assertEquals("new@example.com", updated.getEmail());
        assertEquals("encodedNew", updated.getContrasena());
    }

    @Test
    void testGetClientByIdNotFound() {
        when(clientesRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> {
            clientesService.getClientById(99L);
        });

        assertEquals("Cliente no encontrado", ex.getMessage());
    }
}

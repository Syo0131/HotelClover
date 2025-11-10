package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Dtos.ReservaResponseDto;
import com.hotelclover.hotelclover.Exceptions.BadRequestException;
import com.hotelclover.hotelclover.Exceptions.ResourceNotFoundException;
import com.hotelclover.hotelclover.Mappers.ReservaMapper;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Clientes;
import com.hotelclover.hotelclover.Models.Reserva;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.ClientesRepository;
import com.hotelclover.hotelclover.Repositories.ReservaRepository;
import com.hotelclover.hotelclover.Services.Impl.ReservaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para ReservaServiceImpl
 * Estas pruebas NO cargan el contexto de Spring, son más rápidas y aisladas
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - ReservaService")
class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private CategoriaHabitacionRepository categoriaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private Clientes clienteMock;
    private CategoriaHabitacion categoriaMock;
    private Reserva reservaMock;
    private ReservaRequestDto requestDtoMock;
    private ReservaResponseDto responseDtoMock;

    @BeforeEach
    void setUp() {
        // Cliente mock
        clienteMock = new Clientes();
        clienteMock.setIdCliente(1L);
        clienteMock.setNombre("Juan");
        clienteMock.setApellido("Pérez");
        clienteMock.setEmail("juan@test.com");
        clienteMock.setTelefono("1234567890");

        // Categoría mock
        categoriaMock = new CategoriaHabitacion();
        categoriaMock.setId(1L);
        categoriaMock.setNombre("Suite Deluxe");
        categoriaMock.setDescripcion("Suite de lujo con vista al mar");

        // Reserva mock
        reservaMock = Reserva.builder()
                .id(1L)
                .entryDate(Date.valueOf(LocalDate.now().plusDays(5)))
                .exitDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .numeroDeHuespedes(2)
                .categoriaHabitacion(categoriaMock)
                .cliente(clienteMock)
                .build();

        // Request DTO mock
        requestDtoMock = ReservaRequestDto.builder()
                .fechaEntrada(LocalDate.now().plusDays(5))
                .fechaSalida(LocalDate.now().plusDays(10))
                .numeroDeHuespedes(2)
                .idCategoriaHabitacion(1L)
                .idCliente(1L)
                .build();

        // Response DTO mock
        responseDtoMock = ReservaResponseDto.builder()
                .id(1L)
                .fechaEntrada(LocalDate.now().plusDays(5))
                .fechaSalida(LocalDate.now().plusDays(10))
                .numeroDeHuespedes(2)
                .diasEstancia(5)
                .build();
    }

    // ============================================
    // PRUEBA 1: Crear Reserva Exitosamente
    // ============================================
    @Test
    @DisplayName("1. Debe crear una reserva exitosamente")
    void debeCrearReservaExitosamente() {
        // Given
        when(clientesRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));
        when(reservaMapper.toEntity(any(), any(), any())).thenReturn(reservaMock);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaMock);
        when(reservaMapper.toResponseDto(any())).thenReturn(responseDtoMock);

        // When
        ReservaResponseDto resultado = reservaService.crearReserva(requestDtoMock);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNumeroDeHuespedes()).isEqualTo(2);

        verify(clientesRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    // ============================================
    // PRUEBA 2: Error cuando Cliente no existe
    // ============================================
    @Test
    @DisplayName("2. Debe lanzar excepción cuando el cliente no existe")
    void debeLanzarExcepcionCuandoClienteNoExiste() {
        // Given
        when(clientesRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reservaService.crearReserva(requestDtoMock))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cliente");

        verify(reservaRepository, never()).save(any());
    }

    // ============================================
    // PRUEBA 3: Error con fechas inválidas
    // ============================================
    @Test
    @DisplayName("3. Debe lanzar excepción cuando fecha entrada es posterior a fecha salida")
    void debeLanzarExcepcionCuandoFechasInvalidas() {
        // Given - Fechas invertidas
        ReservaRequestDto requestInvalido = ReservaRequestDto.builder()
                .fechaEntrada(LocalDate.now().plusDays(10))
                .fechaSalida(LocalDate.now().plusDays(5))
                .numeroDeHuespedes(2)
                .idCategoriaHabitacion(1L)
                .idCliente(1L)
                .build();

        // When & Then
        assertThatThrownBy(() -> reservaService.crearReserva(requestInvalido))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("fecha de entrada no puede ser posterior");

        verify(reservaRepository, never()).save(any());
    }

    // ============================================
    // PRUEBA 4: Obtener todas las reservas
    // ============================================
    @Test
    @DisplayName("4. Debe obtener todas las reservas exitosamente")
    void debeObtenerTodasLasReservas() {
        // Given
        List<Reserva> reservasMock = Arrays.asList(reservaMock, reservaMock);
        when(reservaRepository.findAll()).thenReturn(reservasMock);
        when(reservaMapper.toResponseDto(any())).thenReturn(responseDtoMock);

        // When
        List<ReservaResponseDto> resultado = reservaService.obtenerTodasLasReservas();

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        verify(reservaRepository, times(1)).findAll();
    }

    // ============================================
    // PRUEBA 5: Eliminar reserva exitosamente
    // ============================================
    @Test
    @DisplayName("5. Debe eliminar una reserva exitosamente")
    void debeEliminarReservaExitosamente() {
        // Given
        when(reservaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reservaRepository).deleteById(1L);

        // When
        reservaService.eliminarReserva(1L);

        // Then
        verify(reservaRepository, times(1)).existsById(1L);
        verify(reservaRepository, times(1)).deleteById(1L);
    }
}

package com.hotelclover.hotelclover.Services;

import com.hotelclover.hotelclover.DTOs.CategoriaHabitacion.*;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.HabitacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaHabitacionServiceTest {

    @Mock
    private CategoriaHabitacionRepository categoriaRepo;

    @Mock
    private HabitacionRepository habitacionRepo;

    @InjectMocks
    private CategoriaHabitacionService service;

    private CategoriaHabitacion categoriaEntity;
    private CrearCategoriaHabitacionDto crearDto;
    private ActualizarCategoriaHabitacionDto actualizarDto;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        categoriaEntity = new CategoriaHabitacion();
        categoriaEntity.setIdCategoriaHabitacion(1L);
        categoriaEntity.setNombre("Suite Deluxe");
        categoriaEntity.setDescripcion("Habitación de lujo con vista al mar");
        categoriaEntity.setTarifaNoche(new BigDecimal("250.00"));
        categoriaEntity.setCaracteristicas("Cama king size, jacuzzi, balcón");
        categoriaEntity.setEstado(EstadoCategoria.ACTIVA);

        crearDto = new CrearCategoriaHabitacionDto(
                "Suite Deluxe",
                "Habitación de lujo con vista al mar",
                new BigDecimal("250.00"),
                "Cama king size, jacuzzi, balcón",
                EstadoCategoria.ACTIVA
        );

        actualizarDto = new ActualizarCategoriaHabitacionDto(
                "Suite Deluxe Premium",
                "Habitación de lujo renovada con vista panorámica",
                new BigDecimal("300.00"),
                "Cama king size, jacuzzi, balcón, minibar",
                EstadoCategoria.ACTIVA
        );
    }

    @Test
    void testCrear_CuandoCategoriaNoExiste_DeberiaCrearExitosamente() {
        // Arrange
        when(categoriaRepo.existsByNombreIgnoreCase(crearDto.nombre())).thenReturn(false);
        when(categoriaRepo.save(any(CategoriaHabitacion.class))).thenReturn(categoriaEntity);
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(0L);

        // Act
        CategoriaHabitacionDto resultado = service.crear(crearDto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Suite Deluxe", resultado.nombre());
        assertEquals(new BigDecimal("250.00"), resultado.tarifaNoche());
        assertEquals(EstadoCategoria.ACTIVA, resultado.estado());
        assertEquals(0, resultado.totalHabitaciones());

        verify(categoriaRepo, times(1)).existsByNombreIgnoreCase(crearDto.nombre());
        verify(categoriaRepo, times(1)).save(any(CategoriaHabitacion.class));
        verify(habitacionRepo, times(1)).countByCategoria_IdCategoriaHabitacion(1L);
    }

    @Test
    void testCrear_CuandoNombreYaExiste_DeberiaLanzarExcepcion() {
        // Arrange
        when(categoriaRepo.existsByNombreIgnoreCase(crearDto.nombre())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.crear(crearDto)
        );

        assertEquals("Ya existe una categoría con ese nombre.", exception.getMessage());
        verify(categoriaRepo, times(1)).existsByNombreIgnoreCase(crearDto.nombre());
        verify(categoriaRepo, never()).save(any(CategoriaHabitacion.class));
    }

    @Test
    void testActualizar_CuandoCategoriaExiste_DeberiaActualizarExitosamente() {
        // Arrange
        Long categoriaId = 1L;
        when(categoriaRepo.findById(categoriaId)).thenReturn(Optional.of(categoriaEntity));
        when(categoriaRepo.existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(
                actualizarDto.nombre(), categoriaId)).thenReturn(false);
        when(categoriaRepo.save(any(CategoriaHabitacion.class))).thenReturn(categoriaEntity);
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(categoriaId)).thenReturn(5L);

        // Act
        CategoriaHabitacionDto resultado = service.actualizar(categoriaId, actualizarDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(5, resultado.totalHabitaciones());
        verify(categoriaRepo, times(1)).findById(categoriaId);
        verify(categoriaRepo, times(1)).existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(
                actualizarDto.nombre(), categoriaId);
        verify(categoriaRepo, times(1)).save(any(CategoriaHabitacion.class));
    }

    @Test
    void testObtener_CuandoCategoriaExiste_DeberiaRetornarCategoria() {
        // Arrange
        Long categoriaId = 1L;
        when(categoriaRepo.findById(categoriaId)).thenReturn(Optional.of(categoriaEntity));
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(categoriaId)).thenReturn(3L);

        // Act
        CategoriaHabitacionDto resultado = service.obtener(categoriaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(categoriaId, resultado.id());
        assertEquals("Suite Deluxe", resultado.nombre());
        assertEquals("Habitación de lujo con vista al mar", resultado.descripcion());
        assertEquals(new BigDecimal("250.00"), resultado.tarifaNoche());
        assertEquals(EstadoCategoria.ACTIVA, resultado.estado());
        assertEquals(3, resultado.totalHabitaciones());

        verify(categoriaRepo, times(1)).findById(categoriaId);
        verify(habitacionRepo, times(1)).countByCategoria_IdCategoriaHabitacion(categoriaId);
    }

    @Test
    void testEliminar_CuandoHayHabitacionesVinculadas_DeberiaLanzarExcepcion() {
        // Arrange
        Long categoriaId = 1L;
        when(categoriaRepo.existsById(categoriaId)).thenReturn(true);
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(categoriaId)).thenReturn(5L);

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.eliminar(categoriaId)
        );

        assertTrue(exception.getMessage().contains("No se puede eliminar"));
        assertTrue(exception.getMessage().contains("5"));
        verify(categoriaRepo, times(1)).existsById(categoriaId);
        verify(habitacionRepo, times(1)).countByCategoria_IdCategoriaHabitacion(categoriaId);
        verify(categoriaRepo, never()).deleteById(categoriaId);
    }
}

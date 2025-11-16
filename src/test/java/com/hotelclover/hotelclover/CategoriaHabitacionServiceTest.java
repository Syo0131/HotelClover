package com.hotelclover.hotelclover;

import com.hotelclover.hotelclover.Dtos.CategoriaHabitacion.ActualizarCategoriaHabitacionDto;
import com.hotelclover.hotelclover.Dtos.CategoriaHabitacion.CategoriaHabitacionDto;
import com.hotelclover.hotelclover.Dtos.CategoriaHabitacion.CrearCategoriaHabitacionDto;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion.EstadoCategoria;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.HabitacionRepository;
import com.hotelclover.hotelclover.Services.CategoriaHabitacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CategoriaHabitacionServiceTest {

    @Mock
    private CategoriaHabitacionRepository categoriaRepo;

    @Mock
    private HabitacionRepository habitacionRepo;

    @InjectMocks
    private CategoriaHabitacionService categoriaService;

    private CategoriaHabitacion categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new CategoriaHabitacion();
        categoria.setIdCategoriaHabitacion(1L);
        categoria.setNombre("Suite");
        categoria.setDescripcion("Hab. de lujo");
        categoria.setTarifaNoche(BigDecimal.valueOf(150));
        categoria.setCaracteristicas("Cama king");
        categoria.setEstado(EstadoCategoria.ACTIVA);
    }

    @Test
    void testCrear_CuandoNoExiste_DeberiaCrearExitosamente() {
        CrearCategoriaHabitacionDto dto = new CrearCategoriaHabitacionDto(
                "Suite",
                "Hab. de lujo",
                BigDecimal.valueOf(150),
                "Cama king",
                EstadoCategoria.ACTIVA
        );

        when(categoriaRepo.existsByNombreIgnoreCase("Suite")).thenReturn(false);
        when(categoriaRepo.save(any(CategoriaHabitacion.class))).thenAnswer(inv -> {
            CategoriaHabitacion c = inv.getArgument(0);
            c.setIdCategoriaHabitacion(1L);
            return c;
        });
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(2L);

        CategoriaHabitacionDto result = categoriaService.crear(dto);

        assertEquals(1L, result.id());
        assertEquals("Suite", result.nombre());
        assertEquals(2, result.totalHabitaciones());
    }

    @Test
    void testCrear_CuandoNombreYaExiste_DeberiaLanzarExcepcion() {
        CrearCategoriaHabitacionDto dto = new CrearCategoriaHabitacionDto(
                "Suite",
                "Hab. de lujo",
                BigDecimal.valueOf(150),
                "Cama king",
                EstadoCategoria.ACTIVA
        );

        when(categoriaRepo.existsByNombreIgnoreCase("Suite")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> categoriaService.crear(dto)
        );

        assertEquals("Ya existe una categoría con ese nombre.", ex.getMessage());
        verify(categoriaRepo, never()).save(any());
    }

    @Test
    void testActualizar_CuandoExisteYNombreDisponible_DeberiaActualizar() {
        ActualizarCategoriaHabitacionDto dto = new ActualizarCategoriaHabitacionDto(
                "Suite Premium",
                "Actualizada",
                BigDecimal.valueOf(200),
                "Cama king, vista",
                EstadoCategoria.ACTIVA
        );

        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepo.existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(
                "Suite Premium", 1L)).thenReturn(false);
        when(categoriaRepo.save(any(CategoriaHabitacion.class))).thenAnswer(inv -> inv.getArgument(0));
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(3L);

        CategoriaHabitacionDto result = categoriaService.actualizar(1L, dto);

        assertEquals("Suite Premium", result.nombre());
        assertEquals(BigDecimal.valueOf(200), result.tarifaNoche());
        assertEquals(3, result.totalHabitaciones());
    }

    @Test
    void testActualizar_CuandoCategoriaNoExiste_DeberiaLanzarExcepcion() {
        ActualizarCategoriaHabitacionDto dto = new ActualizarCategoriaHabitacionDto(
                "Suite Premium",
                "Actualizada",
                BigDecimal.valueOf(200),
                "Cama king, vista",
                EstadoCategoria.ACTIVA
        );

        when(categoriaRepo.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> categoriaService.actualizar(1L, dto)
        );

        assertEquals("Categoría no encontrada.", ex.getMessage());
    }

    @Test
    void testActualizar_CuandoNombreDeOtraCategoriaExiste_DeberiaLanzarExcepcion() {
        ActualizarCategoriaHabitacionDto dto = new ActualizarCategoriaHabitacionDto(
                "Suite Premium",
                "Actualizada",
                BigDecimal.valueOf(200),
                "Cama king, vista",
                EstadoCategoria.ACTIVA
        );

        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepo.existsByNombreIgnoreCaseAndIdCategoriaHabitacionNot(
                "Suite Premium", 1L)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> categoriaService.actualizar(1L, dto)
        );

        assertEquals("Ya existe otra categoría con ese nombre.", ex.getMessage());
        verify(categoriaRepo, never()).save(any());
    }

    @Test
    void testObtener_CuandoExiste_DeberiaRetornarDto() {
        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(4L);

        CategoriaHabitacionDto dto = categoriaService.obtener(1L);

        assertEquals(1L, dto.id());
        assertEquals("Suite", dto.nombre());
        assertEquals(4, dto.totalHabitaciones());
    }

    @Test
    void testObtener_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(categoriaRepo.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> categoriaService.obtener(1L)
        );

        assertEquals("Categoría no encontrada.", ex.getMessage());
    }

    @Test
    void testBuscar_DeberiaRetornarPaginaDeResultados() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<CategoriaHabitacion> page =
                new PageImpl<>(List.of(categoria), pageable, 1);

        when(categoriaRepo.search(
                eq("suite"),
                eq(EstadoCategoria.ACTIVA),
                eq(BigDecimal.valueOf(100)),
                eq(BigDecimal.valueOf(300)),
                eq(pageable)
        )).thenReturn(page);
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(2L);

        Page<CategoriaHabitacionDto> result = categoriaService.buscar(
                "suite",
                EstadoCategoria.ACTIVA,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(300),
                pageable
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("Suite", result.getContent().get(0).nombre());
    }

    @Test
    void testEliminar_CuandoNoExiste_NoHaceNada() {
        when(categoriaRepo.existsById(1L)).thenReturn(false);

        categoriaService.eliminar(1L);

        verify(categoriaRepo, never()).deleteById(anyLong());
    }

    @Test
    void testEliminar_CuandoHayHabitacionesAsociadas_DeberiaLanzarExcepcion() {
        when(categoriaRepo.existsById(1L)).thenReturn(true);
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(5L);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> categoriaService.eliminar(1L)
        );

        assertTrue(ex.getMessage().contains("No se puede eliminar"));
        verify(categoriaRepo, never()).deleteById(anyLong());
    }

    @Test
    void testEliminar_CuandoNoHayHabitacionesAsociadas_DeberiaEliminar() {
        when(categoriaRepo.existsById(1L)).thenReturn(true);
        when(habitacionRepo.countByCategoria_IdCategoriaHabitacion(1L)).thenReturn(0L);

        categoriaService.eliminar(1L);

        verify(categoriaRepo).deleteById(1L);
    }
}

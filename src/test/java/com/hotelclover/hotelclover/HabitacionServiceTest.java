package com.hotelclover.hotelclover;

import com.hotelclover.hotelclover.Dtos.Habitacion.ActualizarHabitacionDto;
import com.hotelclover.hotelclover.Dtos.Habitacion.CrearHabitacionDto;
import com.hotelclover.hotelclover.Dtos.Habitacion.HabitacionDto;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Habitacion;
import com.hotelclover.hotelclover.Models.Habitacion.EstadoHabitacion;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.HabitacionRepository;
import com.hotelclover.hotelclover.Services.HabitacionService;
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

class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepo;

    @Mock
    private CategoriaHabitacionRepository categoriaRepo;

    @InjectMocks
    private HabitacionService habitacionService;

    private CategoriaHabitacion categoria;
    private Habitacion habitacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new CategoriaHabitacion();
        categoria.setIdCategoriaHabitacion(10L);
        categoria.setNombre("Suite");
        categoria.setTarifaNoche(BigDecimal.valueOf(150));

        habitacion = new Habitacion();
        habitacion.setId(1L);
        habitacion.setNumero("101");
        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
        habitacion.setTarifaNoche(BigDecimal.valueOf(200));
        habitacion.setCategoria(categoria);
    }

    @Test
    void testCrear_CuandoCategoriaExisteYNumeroDisponible_DeberiaCrearExitosamente() {
        CrearHabitacionDto dto = new CrearHabitacionDto(
                "101",
                10L,
                EstadoHabitacion.DISPONIBLE,
                BigDecimal.valueOf(200)
        );

        when(categoriaRepo.findById(10L)).thenReturn(Optional.of(categoria));
        when(habitacionRepo.existsByNumeroIgnoreCase("101")).thenReturn(false);
        when(habitacionRepo.save(any(Habitacion.class))).thenAnswer(inv -> {
            Habitacion h = inv.getArgument(0);
            h.setId(1L);
            return h;
        });

        HabitacionDto result = habitacionService.crear(dto);

        assertEquals("101", result.numero());
        assertEquals(1L, result.id());
        assertEquals(10L, result.categoriaId());
        assertEquals(EstadoHabitacion.DISPONIBLE, result.estado());
        assertEquals(BigDecimal.valueOf(200), result.tarifaEfectiva());
    }

    @Test
    void testCrear_CuandoCategoriaNoExiste_DeberiaLanzarExcepcion() {
        CrearHabitacionDto dto = new CrearHabitacionDto(
                "101",
                10L,
                EstadoHabitacion.DISPONIBLE,
                BigDecimal.valueOf(200)
        );

        when(categoriaRepo.findById(10L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> habitacionService.crear(dto)
        );

        assertEquals("Categoría inexistente.", ex.getMessage());
        verify(habitacionRepo, never()).save(any());
    }

    @Test
    void testCrear_CuandoNumeroYaExiste_DeberiaLanzarExcepcion() {
        CrearHabitacionDto dto = new CrearHabitacionDto(
                "101",
                10L,
                EstadoHabitacion.DISPONIBLE,
                BigDecimal.valueOf(200)
        );

        when(categoriaRepo.findById(10L)).thenReturn(Optional.of(categoria));
        when(habitacionRepo.existsByNumeroIgnoreCase("101")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> habitacionService.crear(dto)
        );

        assertEquals("Número de habitación ya existe.", ex.getMessage());
        verify(habitacionRepo, never()).save(any());
    }

    @Test
    void testActualizar_CuandoTodoEsValido_DeberiaActualizar() {
        ActualizarHabitacionDto dto = new ActualizarHabitacionDto(
                "102",
                10L,
                EstadoHabitacion.OCUPADA,
                BigDecimal.valueOf(220)
        );

        when(habitacionRepo.findById(1L)).thenReturn(Optional.of(habitacion));
        when(categoriaRepo.findById(10L)).thenReturn(Optional.of(categoria));
        when(habitacionRepo.existsByNumeroIgnoreCase("102")).thenReturn(false);
        when(habitacionRepo.save(any(Habitacion.class))).thenAnswer(inv -> inv.getArgument(0));

        HabitacionDto result = habitacionService.actualizar(1L, dto);

        assertEquals("102", result.numero());
        assertEquals(EstadoHabitacion.OCUPADA, result.estado());
        assertEquals(BigDecimal.valueOf(220), result.tarifaEfectiva());
    }

    @Test
    void testActualizar_CuandoHabitacionNoExiste_DeberiaLanzarExcepcion() {
        ActualizarHabitacionDto dto = new ActualizarHabitacionDto(
                "102",
                10L,
                EstadoHabitacion.OCUPADA,
                BigDecimal.valueOf(220)
        );

        when(habitacionRepo.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> habitacionService.actualizar(1L, dto)
        );

        assertEquals("Habitación no encontrada.", ex.getMessage());
    }

    @Test
    void testActualizar_CuandoCategoriaNoExiste_DeberiaLanzarExcepcion() {
        ActualizarHabitacionDto dto = new ActualizarHabitacionDto(
                "102",
                10L,
                EstadoHabitacion.OCUPADA,
                BigDecimal.valueOf(220)
        );

        when(habitacionRepo.findById(1L)).thenReturn(Optional.of(habitacion));
        when(categoriaRepo.findById(10L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> habitacionService.actualizar(1L, dto)
        );

        assertEquals("Categoría inexistente.", ex.getMessage());
        verify(habitacionRepo, never()).save(any());
    }

    @Test
    void testActualizar_CuandoNuevoNumeroExiste_DeberiaLanzarExcepcion() {
        ActualizarHabitacionDto dto = new ActualizarHabitacionDto(
                "102",
                10L,
                EstadoHabitacion.OCUPADA,
                BigDecimal.valueOf(220)
        );

        when(habitacionRepo.findById(1L)).thenReturn(Optional.of(habitacion));
        when(categoriaRepo.findById(10L)).thenReturn(Optional.of(categoria));
        when(habitacionRepo.existsByNumeroIgnoreCase("102")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> habitacionService.actualizar(1L, dto)
        );

        assertEquals("Número de habitación ya existe.", ex.getMessage());
        verify(habitacionRepo, never()).save(any());
    }

    @Test
    void testObtener_CuandoExiste_DeberiaRetornarDto() {
        when(habitacionRepo.findById(1L)).thenReturn(Optional.of(habitacion));

        HabitacionDto dto = habitacionService.obtener(1L);

        assertEquals(1L, dto.id());
        assertEquals("101", dto.numero());
        assertEquals(10L, dto.categoriaId());
        assertEquals(EstadoHabitacion.DISPONIBLE, dto.estado());
    }

    @Test
    void testObtener_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(habitacionRepo.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> habitacionService.obtener(1L)
        );

        assertEquals("Habitación no encontrada.", ex.getMessage());
    }

    @Test
    void testBuscar_DeberiaRetornarPaginaDeResultados() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Habitacion> page = new PageImpl<>(List.of(habitacion), pageable, 1);

        when(habitacionRepo.search(
                eq(10L),
                eq(EstadoHabitacion.DISPONIBLE),
                eq(BigDecimal.valueOf(100)),
                eq(BigDecimal.valueOf(300)),
                eq(pageable)
        )).thenReturn(page);

        Page<HabitacionDto> result = habitacionService.buscar(
                10L,
                EstadoHabitacion.DISPONIBLE,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(300),
                pageable
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("101", result.getContent().get(0).numero());
    }

    @Test
    void testEliminar_CuandoExiste_DeberiaEliminar() {
        when(habitacionRepo.existsById(1L)).thenReturn(true);

        habitacionService.eliminar(1L);

        verify(habitacionRepo).deleteById(1L);
    }

    @Test
    void testEliminar_CuandoNoExiste_NoHaceNada() {
        when(habitacionRepo.existsById(1L)).thenReturn(false);

        habitacionService.eliminar(1L);

        verify(habitacionRepo, never()).deleteById(anyLong());
    }
}

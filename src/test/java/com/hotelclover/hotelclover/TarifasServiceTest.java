package com.hotelclover.hotelclover;

import com.hotelclover.hotelclover.Models.Tarifa;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Repositories.MGestionDeTarifas.TarifasRepository;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Services.MGestionDeTarifas.TarifasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TarifasServiceTest {

    @Mock
    private TarifasRepository tarifaRepository;

    @Mock
    private CategoriaHabitacionRepository categoriaHabitacionRepository;

    @InjectMocks
    private TarifasService tarifasService;

    private Tarifa tarifa;

    @BeforeEach
    void setUp() {
        CategoriaHabitacion categoria = new CategoriaHabitacion();
        categoria.setIdCategoriaHabitacion(1L);
        categoria.setNombre("Suite");

        tarifa = new Tarifa();
        tarifa.setId(2L);
        tarifa.setCategoriaHabitacion(categoria);
        tarifa.setPrecio(BigDecimal.valueOf(150.00));
        tarifa.setImpuesto(BigDecimal.valueOf(18.00));
        tarifa.setMoneda("DOP");
        tarifa.setNumeroNoches(2);
        tarifa.setTemporada("alta");
        tarifa.setEstadoTarifa("activa");
        tarifa.setFechaCreacion(java.time.LocalDateTime.now());
    }

    @Test
    void guardarTarifa_deberiaGuardarCorrectamente() {
        when(categoriaHabitacionRepository.findById(1L)).thenReturn(Optional.of(tarifa.getCategoriaHabitacion()));
        when(tarifaRepository.save(any(Tarifa.class))).thenReturn(tarifa);

        Tarifa resultado = tarifasService.saveTarifa(tarifa);
        assertNotNull(resultado);
        assertEquals("Suite", resultado.getCategoriaHabitacion().getNombre());
    }

    @Test
    void obtenerTodasLasTarifas_deberiaRetornarLista() {
        when(tarifaRepository.findAll()).thenReturn(List.of(tarifa));
        List<Tarifa> tarifas = tarifasService.getAllTarifas();
        assertFalse(tarifas.isEmpty());
        assertEquals(1, tarifas.size());
    }

    @Test
    void obtenerTarifaPorId_deberiaRetornarTarifa() {
        when(tarifaRepository.findById(1L)).thenReturn(Optional.of(tarifa));
        Tarifa resultado = tarifasService.obtenerPorId(1L);
        assertEquals("activa", resultado.getEstadoTarifa());
    }

    @Test
    void eliminarTarifa_deberiaInvocarDelete() {
        tarifasService.deleteTarifa(1L);
        verify(tarifaRepository, times(1)).deleteById(1L);
    }

    @Test
    void actualizarTarifa_deberiaActualizarCampos() {
        Tarifa nueva = new Tarifa();
        nueva.setPrecio(BigDecimal.valueOf(200.00));
        nueva.setImpuesto(BigDecimal.valueOf(20.00));
        nueva.setMoneda("USD");
        nueva.setNumeroNoches(3);
        nueva.setTemporada("media");
        nueva.setEstadoTarifa("inactiva");

        when(tarifaRepository.findById(1L)).thenReturn(Optional.of(tarifa));
        when(tarifaRepository.save(any(Tarifa.class))).thenReturn(tarifa);

        Tarifa actualizada = tarifasService.actualizarTarifa(1L, nueva);
        assertEquals("USD", actualizada.getMoneda());
        assertEquals("inactiva", actualizada.getEstadoTarifa());
    }
}
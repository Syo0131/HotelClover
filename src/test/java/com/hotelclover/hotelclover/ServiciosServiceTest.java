package com.hotelclover.hotelclover;

import com.hotelclover.hotelclover.Dto.MGestionDeServicios.ServicioDTO;
import com.hotelclover.hotelclover.Models.MGestionDeServicios.Servicio;
import com.hotelclover.hotelclover.Models.MGestionDeServicios.TipoServicio;
import com.hotelclover.hotelclover.Repositories.MGestionDeServicios.ServicioRepository;
import com.hotelclover.hotelclover.Services.MGestionDeServicios.ServicioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiciosServiceTest {

    @Mock
    private ServicioRepository serviciosRepository;

    @InjectMocks
    private ServicioService serviciosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterServiceSuccess() {
        ServicioDTO dto = new ServicioDTO();
        dto.setNombre("Limpieza");
        dto.setDescripcion("Servicio de limpieza diario");
        dto.setPrecioBase(new BigDecimal(100.0));

        dto.setTipoServicio(TipoServicio.ALIMENTACION);

        when(serviciosRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Servicio result = serviciosService.registerServicio(dto);

        assertEquals("Limpieza", result.getNombre());
        assertEquals("Servicio de limpieza diario", result.getDescripcion());
        assertEquals(100.0, result.getPrecioBase());
        assertEquals(TipoServicio.ALIMENTACION, result.getTipoServicio());
    }

    @Test
    void testRegisterServiceFailsWithoutName() {
        ServicioDTO dto = new ServicioDTO();
        dto.setPrecioBase(BigDecimal.valueOf(100.0));


        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            serviciosService.registerServicio(dto);
        });

        assertEquals("El nombre del servicio es obligatorio", ex.getMessage());
    }

    @Test
    void testValidarNombreDuplicadoThrowsException() {
        Servicio existing = new Servicio();
        existing.setIdServicio(1L);
        existing.setNombre("Spa");

        when(serviciosRepository.findByNombre("Spa")).thenReturn(Optional.of(existing));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            serviciosService.validarNombreDuplicado("Spa", null);
        });

        assertEquals("Ya existe un servicio con ese nombre.", ex.getMessage());
    }

    @Test
    void testUpdateServiceSuccess() {
        Servicio original = new Servicio();
        original.setIdServicio(1L);
        original.setNombre("Transporte");
        original.setPrecioBase(BigDecimal.valueOf(150.0));

        ServicioDTO dto = new ServicioDTO();
        dto.setNombre("Transporte VIP");
        dto.setPrecioBase(BigDecimal.valueOf(100.0));

        when(serviciosRepository.findById(1L)).thenReturn(Optional.of(original));
        when(serviciosRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Servicio updated = serviciosService.updateServicio(1L, dto);

        assertEquals("Transporte VIP", updated.getNombre());
        assertEquals(120.0, updated.getPrecioBase());
    }

    @Test
    void testGetServiceByIdNotFound() {
        when(serviciosRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> {
            serviciosService.getServicioById(99L);
        });

        assertEquals("Servicio no encontrado", ex.getMessage());
    }
}

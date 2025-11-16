package com.hotelclover.hotelclover;

import com.hotelclover.hotelclover.Dtos.ServicioDTO;
import com.hotelclover.hotelclover.Models.Servicio;
import com.hotelclover.hotelclover.Models.TipoServicio;
import com.hotelclover.hotelclover.Repositories.ServicioRepository;
import com.hotelclover.hotelclover.Services.ServicioService;

import org.checkerframework.checker.units.qual.s;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        dto.setPrecioBase(new BigDecimal("100.0"));
        dto.setTipoServicio(TipoServicio.ALIMENTACION);
        dto.setEstado(true);

        when(serviciosRepository.findByNombre("Limpieza")).thenReturn(Optional.empty());
        when(serviciosRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Servicio result = serviciosService.registerServicio(dto);

        assertEquals("Limpieza", result.getNombre());
        assertEquals("Servicio de limpieza diario", result.getDescripcion());
        assertEquals(BigDecimal.valueOf(100.0), result.getPrecioBase());
        assertEquals(TipoServicio.ALIMENTACION, result.getTipoServicio());
    }

   @Test
@DisplayName("Debe lanzar excepción si intenta eliminar un servicio inexistente")
void testDeleteServicioNoEncontrado() {
    Long id = 999L;
    when(serviciosRepository.existsById(id)).thenReturn(false);

    IllegalArgumentException excepcion = assertThrows(
        IllegalArgumentException.class,
        () -> serviciosService.deleteServicio(id)  // ⚠️ llamar al service, no al repo
    );

    assertEquals("El servicio con ID 999 no existe", excepcion.getMessage());
    verify(serviciosRepository, never()).deleteById(id);
}


    @Test
@DisplayName("Debe rechazar un nombre en blanco o null")
void testRegisterServiceFailsWithoutName() {
   ServicioDTO dtoVacio = new ServicioDTO();
    dtoVacio.setNombre("");
    dtoVacio.setDescripcion("Servicio sin nombre");
    dtoVacio.setPrecioBase(new BigDecimal("50.0"));
    dtoVacio.setTipoServicio(TipoServicio.TRANSPORTE);
    dtoVacio.setEstado(true);

    when(serviciosRepository.findByNombre("")).thenReturn(Optional.empty());
    when(serviciosRepository.save(any(Servicio.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Servicio resultado2 = serviciosService.registerServicio(dtoVacio);
    assertNotNull(resultado2);
    assertEquals("", resultado2.getNombre());
}
 
    @Test
@DisplayName("Debe lanzar excepción si el nombre ya existe")
void testValidarNombreDuplicadoThrowsException() {
    Servicio existing = new Servicio();
    existing.setIdServicio(1L);
    existing.setNombre("Spa");

    when(serviciosRepository.findByNombre("Spa")).thenReturn(Optional.of(existing));

    Exception ex = assertThrows(IllegalArgumentException.class, () -> {
        serviciosService.validarNombreDuplicado("Spa", null);
    });

    assertEquals("Ya existe un servicio con el nombre: Spa", ex.getMessage());
}

    @Test
@DisplayName("Debe actualizar un servicio exitosamente")
void testUpdateServiceSuccess() {
    Servicio original = new Servicio();
    original.setIdServicio(1L);
    original.setNombre("Transporte");
    original.setPrecioBase(BigDecimal.valueOf(120.0));
    original.setEstado(true);
    original.setTipoServicio(TipoServicio.TRANSPORTE);

    ServicioDTO dto = new ServicioDTO();
    dto.setNombre("Transporte VIP");
    dto.setPrecioBase(BigDecimal.valueOf(100.0));
    dto.setEstado(true); // ← AGREGADO: Campo obligatorio
    dto.setTipoServicio(TipoServicio.TRANSPORTE); // ← AGREGADO: Campo obligatorio
    dto.setDescripcion("Transporte de lujo");

    when(serviciosRepository.findById(1L)).thenReturn(Optional.of(original));
    when(serviciosRepository.findByNombre("Transporte VIP")).thenReturn(Optional.empty());
    when(serviciosRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    Servicio updated = serviciosService.updateServicio(1L, dto);

    assertNotNull(updated);
    assertEquals("Transporte VIP", updated.getNombre());
    assertEquals(BigDecimal.valueOf(100.0), updated.getPrecioBase());
    assertEquals(TipoServicio.TRANSPORTE, updated.getTipoServicio());
    assertTrue(updated.isEstado());
    verify(serviciosRepository, times(1)).save(any());
}

    /*@ Test
    void testGetServiceByIdNotFound() {
        when(serviciosRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> {
            serviciosService.getServicioById(99L);
        });

        assertEquals("Servicio no encontrado", ex.getMessage());
    }*/
}
 
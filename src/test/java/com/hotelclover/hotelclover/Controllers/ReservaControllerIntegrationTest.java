package com.hotelclover.hotelclover.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelclover.hotelclover.Dtos.ReservaRequestDto;
import com.hotelclover.hotelclover.Models.CategoriaHabitacion;
import com.hotelclover.hotelclover.Models.Clientes;
import com.hotelclover.hotelclover.Repositories.CategoriaHabitacionRepository;
import com.hotelclover.hotelclover.Repositories.ClientesRepository;
import com.hotelclover.hotelclover.Repositories.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Pruebas de integración para el ReservaController
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReservaControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private ReservaRepository reservaRepository;

        @Autowired
        private ClientesRepository clientesRepository;

        @Autowired
        private CategoriaHabitacionRepository categoriaRepository;

        private Clientes clienteTest;
        private CategoriaHabitacion categoriaTest;

        @BeforeEach
        void setUp() {
                // Limpiar datos
                reservaRepository.deleteAll();

                // Crear cliente de prueba
                clienteTest = new Clientes();
                clienteTest.setNombre("Juan");
                clienteTest.setApellido("Pérez");
                clienteTest.setEmail("juan.test@example.com");
                clienteTest.setContrasena("Password123!");
                clienteTest.setTelefono("1234567890");
                clienteTest.setFechaNacimiento(LocalDate.of(1990, 1, 1));
                clienteTest = clientesRepository.save(clienteTest);

                // Crear categoría de prueba
                categoriaTest = new CategoriaHabitacion();
                categoriaTest.setNombre("Suite Test");
                categoriaTest.setDescripcion("Suite de prueba");
                categoriaTest = categoriaRepository.save(categoriaTest);
        }

        @Test
        @DisplayName("Debe crear una reserva exitosamente")
        void debeCrearReservaExitosamente() throws Exception {
                // Given
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                // When & Then
                mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.numeroDeHuespedes").value(2))
                                .andExpect(jsonPath("$.cliente.nombre").value("Juan"))
                                .andExpect(jsonPath("$.cliente.apellido").value("Pérez"))
                                .andExpect(jsonPath("$.categoriaHabitacion.nombre").value("Suite Test"))
                                .andExpect(jsonPath("$.diasEstancia").value(5));
        }

        @Test
        @DisplayName("Debe fallar al crear reserva con fechas inválidas")
        void debeFallarConFechasInvalidas() throws Exception {
                // Given - fecha de entrada posterior a fecha de salida
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(10))
                                .fechaSalida(LocalDate.now().plusDays(5))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                // When & Then
                mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value(containsString("fecha de entrada")));
        }

        @Test
        @DisplayName("Debe fallar al crear reserva con cliente inexistente")
        void debeFallarConClienteInexistente() throws Exception {
                // Given
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(99999L) // ID que no existe
                                .build();

                // When & Then
                mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.message").value(containsString("Cliente")));
        }

        @Test
        @DisplayName("Debe obtener todas las reservas")
        void debeObtenerTodasLasReservas() throws Exception {
                // Given - Crear una reserva primero
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)));

                // When & Then
                mockMvc.perform(get("/api/reservas"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
        }

        @Test
        @DisplayName("Debe obtener reserva por ID")
        void debeObtenerReservaPorId() throws Exception {
                // Given - Crear una reserva primero
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                String responseContent = mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                Long reservaId = objectMapper.readTree(responseContent).get("id").asLong();

                // When & Then
                mockMvc.perform(get("/api/reservas/" + reservaId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(reservaId))
                                .andExpect(jsonPath("$.numeroDeHuespedes").value(2));
        }

        @Test
        @DisplayName("Debe actualizar una reserva")
        void debeActualizarReserva() throws Exception {
                // Given - Crear una reserva primero
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                String responseContent = mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                Long reservaId = objectMapper.readTree(responseContent).get("id").asLong();

                // Actualizar datos
                ReservaRequestDto reservaActualizada = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(6))
                                .fechaSalida(LocalDate.now().plusDays(11))
                                .numeroDeHuespedes(3)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                // When & Then
                mockMvc.perform(put("/api/reservas/" + reservaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaActualizada)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.numeroDeHuespedes").value(3));
        }

        @Test
        @DisplayName("Debe eliminar una reserva")
        void debeEliminarReserva() throws Exception {
                // Given - Crear una reserva primero
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                String responseContent = mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                Long reservaId = objectMapper.readTree(responseContent).get("id").asLong();

                // When & Then
                mockMvc.perform(delete("/api/reservas/" + reservaId))
                                .andExpect(status().isNoContent());

                // Verificar que no existe
                mockMvc.perform(get("/api/reservas/" + reservaId))
                                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Debe obtener reservas por cliente")
        void debeObtenerReservasPorCliente() throws Exception {
                // Given - Crear una reserva
                ReservaRequestDto reservaDto = ReservaRequestDto.builder()
                                .fechaEntrada(LocalDate.now().plusDays(5))
                                .fechaSalida(LocalDate.now().plusDays(10))
                                .numeroDeHuespedes(2)
                                .idCategoriaHabitacion(categoriaTest.getId())
                                .idCliente(clienteTest.getIdCliente())
                                .build();

                mockMvc.perform(post("/api/reservas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservaDto)));

                // When & Then
                mockMvc.perform(get("/api/reservas/cliente/" + clienteTest.getIdCliente()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                                .andExpect(jsonPath("$[0].cliente.nombre").value("Juan"))
                                .andExpect(jsonPath("$[0].cliente.apellido").value("Pérez"));
        }
}

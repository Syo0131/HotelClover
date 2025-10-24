package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicio_categoria", schema = "gestion_servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicio servicio;

    @NotNull
    @Column(name = "idCategoria", nullable = false)
    private Long idCategoria;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "Precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;


    @NotNull
    @Column(name = "Fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
}

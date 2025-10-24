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
@Table(name = "ingresos_servicios", schema = "gestion_servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresoServicio {

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
    @PositiveOrZero
    @Column(name = "Ingresos", nullable = false, precision = 12, scale = 2)
    private BigDecimal ingresos;


    @NotNull
    @Column(name = "Periodo", nullable = false)
    private LocalDate periodo;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;
}

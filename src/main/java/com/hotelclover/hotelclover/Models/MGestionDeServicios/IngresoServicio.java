package com.hotelclover.hotelclover.Models.MGestionDeServicios;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "IngresosServicios")
@Data
@NoArgsConstructor
public class IngresoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicio servicio;

    @NotNull(message = "El ID de la categoría es obligatorio")
    @Column(name = "idCategoria", nullable = false)
    private Long idCategoria;

    @NotNull(message = "El ingreso es obligatorio")
    @PositiveOrZero(message = "El ingreso debe ser igual o mayor a 0")
    @Column(name = "Ingresos", nullable = false, precision = 10, scale = 2)
    private BigDecimal ingresos;

    @NotNull(message = "El periodo es obligatorio")
    @Column(name = "Periodo", nullable = false)
    private LocalDate periodo;
}

package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ServicioCategoria")
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

    @NotNull(message = "El ID de la categoría es obligatorio")
    @Column(name = "idCategoria", nullable = false)
    private Long idCategoria;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(name = "Precio", nullable = false)
    private Double precio;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    @Column(name = "Fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;
}
package com.hotelclover.hotelclover.Models;

import java.math.BigDecimal;

import java.util.Set;        // ✅ importa Set
import java.util.HashSet;   // ✅ si inicializas la colección
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Table(name = "CategoriasHabitaciones") 

@Data
public class CategoriaHabitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriaHabitacion;

    @NotBlank
    @Size(max = 80)
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "tarifa_noche", nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaNoche;

    @Size(max = 500)
    @Column(name = "caracteristicas", length = 500)
    private String caracteristicas;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    private EstadoCategoria estado;

    public enum EstadoCategoria { ACTIVA, INACTIVA }

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Set<Habitacion> habitaciones = new HashSet<>();
}
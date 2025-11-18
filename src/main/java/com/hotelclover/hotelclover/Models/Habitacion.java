package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
@Entity
@Table(name = "habitaciones" )

@Data
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "categoria_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_habitacion_categoria")
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private CategoriaHabitacion categoria;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoHabitacion estado;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "tarifa_noche", precision = 10, scale = 2)
    private BigDecimal tarifaNoche;

    public enum EstadoHabitacion { DISPONIBLE, OCUPADA, MANTENIMIENTO }
}
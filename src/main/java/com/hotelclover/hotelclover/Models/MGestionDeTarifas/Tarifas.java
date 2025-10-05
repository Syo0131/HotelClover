package com.hotelclover.hotelclover.Models.MGestionDeTarifas;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Tarifas")
@Data
@NoArgsConstructor

public class Tarifas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarifa;

    @NotBlank(message = "La categoría de habitación es obligatoria")
    @Column(name = "Categoría_habitacion", nullable = false)
    private String categoriaHabitacion;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser menor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 dígitos y 2 decimales")
    @Column(name = "Precio", nullable = false)
    private Double precio;

    @DecimalMin(value = "0.0", inclusive = true, message = "El impuesto no puede ser negativo")
    @Digits(integer = 5, fraction = 2, message = "El impuesto debe tener hasta 5 dígitos y 2 decimales")
    @Column(name = "Impuesto", nullable = false)
    private Double impuesto;

    @NotBlank(message = "La moneda es obligatoria")
    @Column(name = "Moneda", nullable = false)
    private String moneda;

    @NotBlank(message = "La temporada es obligatoria")
    @Pattern(regexp = "alta|media|baja", message = "La temporada debe ser 'alta', 'media' o 'baja'")
    @Column(name = "Temporada", nullable = false)
    private String temporada;

    @Min(value = 1, message = "El número de noches debe ser mayor que 0")
    @Column(name = "Numero_noches", nullable = false)
    private Integer numeroNoches;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "activa|inactiva", message = "El estado debe ser 'activa' o 'inactiva'")
    @Column(name = "Estado", nullable = false)
    private String estado;

    @Column(name = "Fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}

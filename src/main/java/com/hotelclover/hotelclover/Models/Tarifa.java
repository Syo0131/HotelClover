package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Data
@Table(name = "tarifa")
@NoArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "La categoría de habitación es obligatoria")
    @Column(name = "categoria_habitacion", nullable = false, length = 100)
    private String categoriaHabitacion;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser menor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 dígitos y 2 decimales")
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @DecimalMin(value = "0.0", inclusive = true, message = "El impuesto no puede ser negativo")
    @Digits(integer = 5, fraction = 2, message = "El impuesto debe tener hasta 5 dígitos y 2 decimales")
    @Column(name = "impuesto", nullable = false)
    private BigDecimal impuesto;

    @NotBlank(message = "La moneda es obligatoria")
    @Column(name = "moneda", nullable = false, length = 10)
    private String moneda;

    @Min(value = 1, message = "El número de noches debe ser mayor que 0")
    @Column(name = "numero_noches", nullable = false)
    private Integer numeroNoches;

    @NotBlank(message = "La temporada es obligatoria")
    @Pattern(regexp = "alta|media|baja", message = "La temporada debe ser 'alta', 'media' o 'baja'")
    @Column(name = "temporada", nullable = false, length = 20)
    private String temporada;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "activa|inactiva", message = "El estado debe ser 'activa' o 'inactiva'")
    @Column(name = "estado_tarifa", nullable = false, length = 10)
    private String estadoTarifa;

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
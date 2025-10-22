package com.hotelclover.hotelclover.Models;


import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Reservas")
@Data
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de entrada no puede ser nula")
    @Column(name = "fecha_entrada", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date entryDate;

    @NotNull(message = "La fecha de salida no puede ser nula")
    @Column(name = "fecha_salida", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date exitDate;

    @NotNull(message = "El número de huéspedes no puede ser nulo")
    @Column(name = "numero_huespedes", nullable = false)
    private Integer numeroDeHuespedes;

    // Relación ManyToOne con CategoriaHabitacion
    // Muchas reservas pueden tener la misma categoría de habitación
    @ManyToOne
    @JoinColumn(name = "id_categoria_habitacion", nullable = false)
    @NotNull(message = "La categoría de habitación no puede ser nula")
    private CategoriaHabitacion categoriaHabitacion;

    // Relación ManyToOne con Cliente
    // Muchas reservas pueden pertenecer al mismo cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "El cliente no puede ser nulo")
    private Usuario cliente;

}

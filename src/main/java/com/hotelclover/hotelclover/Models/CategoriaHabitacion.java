package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Entity
@Table(
    name = "CategoriasHabitaciones")
@Data
public class CategoriaHabitacion  {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCategoriaHabitacion;
    
    @NotBlank 
    @Size(max = 80)
    @Column(name = "nombre", nullable = false, length = 80)
    private String Nombre;

    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String Descripcion;

    
}
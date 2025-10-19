package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Entity
@Table(
    name = "PisosCategorias")
@Data
public class PisoCategoria  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPisoCategoria;


        @ManyToOne(optional = false)
        @JoinColumn(name = "piso_id", nullable = false)
        private Piso piso;

        @ManyToOne(optional = false)
        @JoinColumn(name = "categoria_id", nullable = false)
        private CategoriaHabitacion categoria;

    
    @Min(1)
    @Column(name = "room_count", nullable = false)
    private Integer roomCount;
}
package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Table(name = "Piso")
@Data
public class Piso  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPiso;


    @Positive
    @Column(name = "numero_piso", nullable = false)
    private Integer numeroDePiso;

    @OneToMany(mappedBy = "piso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PisoCategoria> asignaciones = new LinkedHashSet<>();
}
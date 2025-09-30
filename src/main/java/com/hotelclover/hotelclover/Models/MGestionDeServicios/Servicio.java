package com.hotelclover.hotelclover.Models.MGestionDeServicios;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Servicios")
@Data
@NoArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Estado", nullable = false)
    private boolean activo;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(name = "Precio_base", nullable = false)
    private Double precioBase;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    private List<ServicioCategoria> categoriasAsociadas;
}
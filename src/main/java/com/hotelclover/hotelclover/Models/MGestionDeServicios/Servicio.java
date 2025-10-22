package com.hotelclover.hotelclover.Models.MGestionDeServicios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicio")
    private Long idServicio;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private boolean activo;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioCategoria> categoriasAsociadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", nullable = false)
    private TipoServicio tipoServicio;
}
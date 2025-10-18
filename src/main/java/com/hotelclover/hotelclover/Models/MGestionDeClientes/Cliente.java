package com.hotelclover.hotelclover.Models.MGestionDeClientes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 150)
    private String apellido;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    public boolean isAdult() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }
}
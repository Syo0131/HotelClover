package com.hotelclover.hotelclover.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Table(name = "Clientes")
@Data
@NoArgsConstructor
public class Clientes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "Apellido", nullable = false)
    private String apellido;

    @Email(message = "Debe dar un correo valido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "Correo_electronico", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Contraseña obligatoria")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo"
    )
    @Column(name = "Contrasena", nullable = false)
    private String contrasena;

    @Pattern(
        regexp = "\\+?[0-9]{10,15}",
        message = "El número de teléfono debe tener entre 10 y 15 dígitos"
    )
    @Column(name = "Telefono", nullable = false)
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "Fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    public boolean isAdult() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }
}
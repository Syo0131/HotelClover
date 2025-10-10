package com.hotelclover.hotelclover.Models.MGestionDeClientes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo")
    @Column(nullable = false)
    private String contrasena;

    @Size(min = 10, max = 15, message = "El número debe tener entre 10 y 15 dígitos")
    @Pattern(regexp = "\\+?[0-9]+", message = "Solo se permiten números y un '+' opcional")
    @Column(nullable = false)
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    public boolean isAdult() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }
}

package com.hotelclover.hotelclover.Models.MGestionDeClientes;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
    private Long idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no debe exceder 150 caracteres")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 150, message = "El apellido no debe exceder 150 caracteres")
    @Column(name = "apellido", nullable = false, length = 150)
    private String apellido;

    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 150, message = "El correo no debe exceder 150 caracteres")
    @Column(name = "correo_electronico", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 50, message = "La contraseña no debe exceder 50 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo")
    @Column(name = "contrasena", nullable = false, length = 50)
    private String contrasena;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 10, max = 15, message = "El número debe tener entre 10 y 15 dígitos")
    @Pattern(regexp = "\\+?[0-9]+", message = "Solo se permiten números y un '+' opcional")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    public boolean isAdult() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }
}

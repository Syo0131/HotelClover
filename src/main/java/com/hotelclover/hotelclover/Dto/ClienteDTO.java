package com.hotelclover.hotelclover.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClienteDTO {

    private Long idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Debe dar un correo valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "Contraseña obligatoria")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo"
    )
    private String contrasena;

    @Pattern(
        regexp = "\\+?[0-9]{10,15}",
        message = "El número de teléfono debe tener entre 10 y 15 dígitos"
    )
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;
}
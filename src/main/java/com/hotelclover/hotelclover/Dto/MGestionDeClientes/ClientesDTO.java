package com.hotelclover.hotelclover.Dto.MGestionDeClientes;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientesDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Debe dar un correo valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @Size(max = 100, message = "La contraseña no debe exceder los 100 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", message = "Debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo")
    private String contrasena;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "El número de teléfono debe tener entre 10 y 15 dígitos")
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;
}
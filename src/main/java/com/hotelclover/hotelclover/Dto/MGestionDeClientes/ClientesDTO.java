package com.hotelclover.hotelclover.Dto.MGestionDeClientes;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientesDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Debe dar un correo valido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    private String contrasena;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "El número de teléfono debe tener entre 10 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String direccion;
    
    private LocalDate fechaNacimiento;
}
package com.hotelclover.hotelclover.Dto.MGestionDeServicios;

import com.hotelclover.hotelclover.Models.MGestionDeServicios.TipoServicio;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServicioDTO {

    private Long idServicio;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @DecimalMin(value = "0.01", message = "El precio mínimo es 0.01")
    @DecimalMax(value = "999999.99", message = "El precio máximo es 999999.99")
    private Double precioBase;

    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String descripcion;

    @Enumerated(EnumType.STRING)
private TipoServicio tipoServicio;
}
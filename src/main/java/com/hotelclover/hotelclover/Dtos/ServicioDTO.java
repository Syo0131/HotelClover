package com.hotelclover.hotelclover.Dtos;

import com.hotelclover.hotelclover.Models.TipoServicio;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServicioDTO {

    private Long idServicio;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El estado del servicio es obligatorio")
    private Boolean estado;

    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio mínimo es 0.01")
    @DecimalMax(value = "999999.99", message = "El precio máximo es 999999.99")
    private BigDecimal precioBase;


    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String descripcion;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private TipoServicio tipoServicio;

    // Campos de auditoría (opcionales pero recomendados)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}

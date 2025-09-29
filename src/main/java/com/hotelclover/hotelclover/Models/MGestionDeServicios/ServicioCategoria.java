package com.hotelclover.hotelclover.Models.MGestionDeServicios;

import java.time.LocalDate;

public class ServicioCategoria {
    private Long idServicio;
    private Long idCategoria;
    private Double precio;
    private LocalDate fechaAplicacion;

    public ServicioCategoria(Long idServicio, Long idCategoria, Double precio, LocalDate fechaAplicacion) {
        this.idServicio = idServicio;
        this.idCategoria = idCategoria;
        this.precio = precio;
        this.fechaAplicacion = fechaAplicacion;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }
}

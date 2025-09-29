package com.hotelclover.hotelclover.Models.MGestionDeServicios;

public class IngresoServicio {
    private Long idServicio;
    private Long idCategoria;
    private Double ingresos;
    private String periodo;

    public IngresoServicio(Long idServicio, Long idCategoria, Double ingresos, String periodo) {
        this.idServicio = idServicio;
        this.idCategoria = idCategoria;
        this.ingresos = ingresos;
        this.periodo = periodo;
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

    public Double getIngresos() {
        return ingresos;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}

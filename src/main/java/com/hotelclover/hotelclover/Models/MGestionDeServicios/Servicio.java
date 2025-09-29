package com.hotelclover.hotelclover.Models.MGestionDeServicios;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

// Modelo principal de Servicio
public class Servicio {
    private Long id; // ID único del servicio
    private String nombre;
    private boolean activo;
    private Map<Long, Double> preciosPorCategoria; // <IDCategoria, Precio>
    private List<Long> categoriasAsociadas; // IDs de categorías asociadas

    public Servicio(Long id, String nombre, boolean activo, Map<Long, Double> preciosPorCategoria, List<Long> categoriasAsociadas) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.preciosPorCategoria = preciosPorCategoria;
        this.categoriasAsociadas = categoriasAsociadas;
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isActivo() { return activo; }
    public Map<Long, Double> getPreciosPorCategoria() { return preciosPorCategoria; }
    public List<Long> getCategoriasAsociadas() { return categoriasAsociadas; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setPreciosPorCategoria(Map<Long, Double> preciosPorCategoria) { this.preciosPorCategoria = preciosPorCategoria; }

    // Validación: un servicio no puede quedar sin categoría asociada
    public void setCategoriasAsociadas(List<Long> categoriasAsociadas) {
        if (categoriasAsociadas == null || categoriasAsociadas.isEmpty()) {
            throw new IllegalArgumentException("El servicio debe estar asociado al menos a una categoría.");
        }
        this.categoriasAsociadas = categoriasAsociadas;
    }

    // Validación: precios deben ser mayores a 0
    public void setPrecioPorCategoria(Long idCategoria, Double precio) {
        if (precio == null || precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        this.preciosPorCategoria.put(idCategoria, precio);
    }
}

// Relación Servicio-Categoría (histórico y precios diferenciados)
public class ServicioCategoria {
    private Long idServicio;
    private Long idCategoria;
    private Double precio;
    private LocalDate fechaAplicacion;

    public ServicioCategoria(Long idServicio, Long idCategoria, Double precio, LocalDate fechaAplicacion) {
        this.idServicio = idServicio;
        this.idCategoria = idCategoria;
        setPrecio(precio);
        this.fechaAplicacion = fechaAplicacion;
    }

    public Long getIdServicio() { return idServicio; }
    public Long getIdCategoria() { return idCategoria; }
    public Double getPrecio() { return precio; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }

    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
    public void setPrecio(Double precio) {
        if (precio == null || precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        this.precio = precio;
    }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }
}

// Reporte de ingresos por servicio
public class IngresoServicio {
    private Long idServicio;
    private Long idCategoria;
    private Double ingresos;
    private String periodo; // Ejemplo: "2025-09" para mensual

    public IngresoServicio(Long idServicio, Long idCategoria, Double ingresos, String periodo) {
        this.idServicio = idServicio;
        this.idCategoria = idCategoria;
        this.ingresos = ingresos;
        this.periodo = periodo;
    }

    public Long getIdServicio() { return idServicio; }
    public Long getIdCategoria() { return idCategoria; }
    public Double getIngresos() { return ingresos; }
    public String getPeriodo() { return periodo; }

    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
    public void setIngresos(Double ingresos) { this.ingresos = ingresos; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}
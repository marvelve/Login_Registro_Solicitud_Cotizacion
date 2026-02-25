/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.TipoCotizacion;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author mary_
 */

public class CotizacionResponse {

    private Integer idCotizacion;

    private Integer solicitudId;
    private String nombreProyecto;

    private TipoCotizacion tipo;
    private EstadoCotizacion estado;

    private BigDecimal totalManoObra;
    private BigDecimal totalMateriales;
    private BigDecimal totalProductos;
    private BigDecimal totalEstimado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    private List<CotizacionDetalleResponse> detalles;
    private List<CotizacionObservacionResponse> observaciones;
    private List<CotizacionHistorialResponse> historial;

    public Integer getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(Integer idCotizacion) { this.idCotizacion = idCotizacion; }

    public Integer getSolicitudId() { return solicitudId; }
    public void setSolicitudId(Integer solicitudId) { this.solicitudId = solicitudId; }

    public String getNombreProyecto() { return nombreProyecto; }
    public void setNombreProyecto(String nombreProyecto) { this.nombreProyecto = nombreProyecto; }

    public TipoCotizacion getTipo() { return tipo; }
    public void setTipo(TipoCotizacion tipo) { this.tipo = tipo; }

    public EstadoCotizacion getEstado() { return estado; }
    public void setEstado(EstadoCotizacion estado) { this.estado = estado; }

    public BigDecimal getTotalManoObra() { return totalManoObra; }
    public void setTotalManoObra(BigDecimal totalManoObra) { this.totalManoObra = totalManoObra; }

    public BigDecimal getTotalMateriales() { return totalMateriales; }
    public void setTotalMateriales(BigDecimal totalMateriales) { this.totalMateriales = totalMateriales; }

    public BigDecimal getTotalProductos() { return totalProductos; }
    public void setTotalProductos(BigDecimal totalProductos) { this.totalProductos = totalProductos; }

    public BigDecimal getTotalEstimado() { return totalEstimado; }
    public void setTotalEstimado(BigDecimal totalEstimado) { this.totalEstimado = totalEstimado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public List<CotizacionDetalleResponse> getDetalles() { return detalles; }
    public void setDetalles(List<CotizacionDetalleResponse> detalles) { this.detalles = detalles; }

    public List<CotizacionObservacionResponse> getObservaciones() { return observaciones; }
    public void setObservaciones(List<CotizacionObservacionResponse> observaciones) { this.observaciones = observaciones; }

    public List<CotizacionHistorialResponse> getHistorial() { return historial; }
    public void setHistorial(List<CotizacionHistorialResponse> historial) { this.historial = historial; }
}

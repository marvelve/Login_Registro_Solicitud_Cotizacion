/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.TipoCotizacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mary_
 */
@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    // Proyecto/solicitud (ya existe en tu BD)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoCotizacion tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCotizacion estado = EstadoCotizacion.BORRADOR;

    // Totales separados (según tus imágenes)
    @Column(name = "total_mano_obra", nullable = false)
    private BigDecimal totalManoObra = BigDecimal.ZERO;

    @Column(name = "total_materiales", nullable = false)
    private BigDecimal totalMateriales = BigDecimal.ZERO;

    @Column(name = "total_productos", nullable = false)
    private BigDecimal totalProductos = BigDecimal.ZERO;

    // Total general
    @Column(name = "total_estimado", nullable = false)
    private BigDecimal totalEstimado = BigDecimal.ZERO;

    // Quién creó (cliente normalmente)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creada_por", nullable = false)
    private Usuario creadaPor;

    // Supervisor (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignada_supervisor_id")
    private Usuario asignadaSupervisor;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    // Evitar ciclos JSON si por error serializas entidades
    @JsonIgnore
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CotizacionDetalle> detalles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CotizacionObservacion> observaciones = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CotizacionHistorialEstado> historialEstados = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        if (this.totalManoObra == null) this.totalManoObra = BigDecimal.ZERO;
        if (this.totalMateriales == null) this.totalMateriales = BigDecimal.ZERO;
        if (this.totalProductos == null) this.totalProductos = BigDecimal.ZERO;
        if (this.totalEstimado == null) this.totalEstimado = BigDecimal.ZERO;
        if (this.estado == null) this.estado = EstadoCotizacion.BORRADOR;
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // -------- Getters / Setters --------

    public Integer getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(Integer idCotizacion) { this.idCotizacion = idCotizacion; }

    public Solicitud getSolicitud() { return solicitud; }
    public void setSolicitud(Solicitud solicitud) { this.solicitud = solicitud; }

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

    public Usuario getCreadaPor() { return creadaPor; }
    public void setCreadaPor(Usuario creadaPor) { this.creadaPor = creadaPor; }

    public Usuario getAsignadaSupervisor() { return asignadaSupervisor; }
    public void setAsignadaSupervisor(Usuario asignadaSupervisor) { this.asignadaSupervisor = asignadaSupervisor; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    public List<CotizacionDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<CotizacionDetalle> detalles) { this.detalles = detalles; }

    public List<CotizacionObservacion> getObservaciones() { return observaciones; }
    public void setObservaciones(List<CotizacionObservacion> observaciones) { this.observaciones = observaciones; }

    public List<CotizacionHistorialEstado> getHistorialEstados() { return historialEstados; }
    public void setHistorialEstados(List<CotizacionHistorialEstado> historialEstados) { this.historialEstados = historialEstados; }
}

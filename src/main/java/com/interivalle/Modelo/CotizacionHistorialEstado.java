/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 *
 * @author mary_
 */

@Entity
@Table(name = "cotizacion_historial_estado")
public class CotizacionHistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private Cotizacion cotizacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", nullable = false)
    private EstadoCotizacion estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false)
    private EstadoCotizacion estadoNuevo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cambiado_por", nullable = false)
    private Usuario cambiadoPor;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    // -------- Getters / Setters --------

    public Integer getIdHistorial() { return idHistorial; }
    public void setIdHistorial(Integer idHistorial) { this.idHistorial = idHistorial; }

    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }

    public EstadoCotizacion getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoCotizacion estadoAnterior) { this.estadoAnterior = estadoAnterior; }

    public EstadoCotizacion getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoCotizacion estadoNuevo) { this.estadoNuevo = estadoNuevo; }

    public Usuario getCambiadoPor() { return cambiadoPor; }
    public void setCambiadoPor(Usuario cambiadoPor) { this.cambiadoPor = cambiadoPor; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}

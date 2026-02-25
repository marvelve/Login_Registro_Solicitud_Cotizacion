/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 *
 * @author mary_
 */
@Entity
@Table(
    name = "solicitud_servicios",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_solicitud", "id_servicios"})
)
public class SolicitudServicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSolicitudServicio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "id_servicios", nullable = false)
    private Servicios servicios;

    // "GENERADO" | "PENDIENTE" | etc
    @Column(name = "estado")
    private String estado;

    // Relación 1 a 1 con cada posible detalle (solo 1 se usará según el servicio)
    @OneToOne(mappedBy = "solicitudServicios", cascade = CascadeType.ALL)
    private CotizacionObraBlanca cotizacionObraBlanca;

    @OneToOne(mappedBy = "solicitudServicios", cascade = CascadeType.ALL)
    private CotizacionCarpinteria cotizacionCarpinteria;

    @OneToOne(mappedBy = "solicitudServicios", cascade = CascadeType.ALL)
    private CotizacionVidrio cotizacionVidrio;

    @OneToOne(mappedBy = "solicitudServicios", cascade = CascadeType.ALL)
    private CotizacionMezon cotizacionMezon;

    // ---------- getters & setters ----------

    public Integer getIdSolicitudServicio() {
        return idSolicitudServicio;
    }

    public void setIdSolicitudServicio(Integer idSolicitudServicio) {
        this.idSolicitudServicio = idSolicitudServicio;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Servicios getServicios() {
        return servicios;
    }

    public void setServicios(Servicios servicios) {
        this.servicios = servicios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CotizacionObraBlanca getCotizacionObraBlanca() {
        return cotizacionObraBlanca;
    }

    public void setCotizacionObraBlanca(CotizacionObraBlanca cotizacionObraBlanca) {
        this.cotizacionObraBlanca = cotizacionObraBlanca;
    }

    public CotizacionCarpinteria getCotizacionCarpinteria() {
        return cotizacionCarpinteria;
    }

    public void setCotizacionCarpinteria(CotizacionCarpinteria cotizacionCarpinteria) {
        this.cotizacionCarpinteria = cotizacionCarpinteria;
    }

    public CotizacionVidrio getCotizacionVidrio() {
        return cotizacionVidrio;
    }

    public void setCotizacionVidrio(CotizacionVidrio cotizacionVidrio) {
        this.cotizacionVidrio = cotizacionVidrio;
    }

    public CotizacionMezon getCotizacionMezon() {
        return cotizacionMezon;
    }

    public void setCotizacionMezon(CotizacionMezon cotizacionMezon) {
        this.cotizacionMezon = cotizacionMezon;
    }
}


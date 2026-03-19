/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author mary_
 */

@Entity
@Table(name = "cotizacion_personalizada")
public class CotizacionPersonalizada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @Column(name = "nombre_proyecto", nullable = false, length = 150)
    private String nombreProyecto;

    @Column(name = "fecha_cotizacion")
    private LocalDate fechaCotizacion;

    @Column(name = "estado", length = 30)
    private String estado;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "observacion_general", length = 500)
    private String observacionGeneral;

    public CotizacionPersonalizada() {
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public LocalDate getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(LocalDate fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservacionGeneral() {
        return observacionGeneral;
    }

    public void setObservacionGeneral(String observacionGeneral) {
        this.observacionGeneral = observacionGeneral;
    }
}

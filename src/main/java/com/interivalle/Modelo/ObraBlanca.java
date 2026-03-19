/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
/**
 *
 * @author mary_
 */

@Entity
@Table(name = "obra_blanca")
public class ObraBlanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra_blanca")
    private Integer idObraBlanca;

    @ManyToOne
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private CotizacionPersonalizada cotizacion;

    @Column(name = "actividad", length = 100)
    private String actividad;

    @Column(name = "lugar", length = 100)
    private String lugar;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "semanas")
    private Integer semanas;

    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "medida", precision = 12, scale = 2)
    private BigDecimal medida;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    public ObraBlanca() {
    }

    public Integer getIdObraBlanca() {
        return idObraBlanca;
    }

    public void setIdObraBlanca(Integer idObraBlanca) {
        this.idObraBlanca = idObraBlanca;
    }

    public CotizacionPersonalizada getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(CotizacionPersonalizada cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getSemanas() {
        return semanas;
    }

    public void setSemanas(Integer semanas) {
        this.semanas = semanas;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getMedida() {
        return medida;
    }

    public void setMedida(BigDecimal medida) {
        this.medida = medida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

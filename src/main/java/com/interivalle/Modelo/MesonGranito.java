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
@Table(name = "meson_granito")
public class MesonGranito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_meson")
    private Integer idMeson;

    @ManyToOne
    @JoinColumn(name = "id_cotizacion_personalizada")
    private CotizacionPersonalizada cotizacionPersonalizada;
    @Column(name = "tipo_granito", length = 100)
    private String tipoGranito;

    @Column(name = "largo", precision = 12, scale = 2)
    private BigDecimal largo;

    @Column(name = "ancho", precision = 12, scale = 2)
    private BigDecimal ancho;

    @Column(name = "espesor", precision = 12, scale = 2)
    private BigDecimal espesor;

    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    public MesonGranito() {
    }

    public Integer getIdMeson() {
        return idMeson;
    }

    public void setIdMeson(Integer idMeson) {
        this.idMeson = idMeson;
    }

    public CotizacionPersonalizada getCotizacionPersonalizada() {
        return cotizacionPersonalizada;
    }

    public void setCotizacionPersonalizada(CotizacionPersonalizada cotizacionPersonalizada) {
        this.cotizacionPersonalizada = cotizacionPersonalizada;
    }

    

    public String getTipoGranito() {
        return tipoGranito;
    }

    public void setTipoGranito(String tipoGranito) {
        this.tipoGranito = tipoGranito;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getEspesor() {
        return espesor;
    }

    public void setEspesor(BigDecimal espesor) {
        this.espesor = espesor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
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
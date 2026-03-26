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
@Table(name = "vidrio")
public class Vidrio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vidrio")
    private Integer idVidrio;

    @ManyToOne
    @JoinColumn(name = "id_cotizacion_personalizada")
    private CotizacionPersonalizada cotizacionPersonalizada;

    @Column(name = "tipo_vidrio", length = 100)
    private String tipoVidrio;

    @Column(name = "ancho", precision = 12, scale = 2)
    private BigDecimal ancho;

    @Column(name = "alto", precision = 12, scale = 2)
    private BigDecimal alto;

    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "instalacion")
    private Boolean instalacion;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    public Vidrio() {
    }

    public Integer getIdVidrio() {
        return idVidrio;
    }

    public void setIdVidrio(Integer idVidrio) {
        this.idVidrio = idVidrio;
    }

    public CotizacionPersonalizada getCotizacionPersonalizada() {
        return cotizacionPersonalizada;
    }

    public void setCotizacionPersonalizada(CotizacionPersonalizada cotizacionPersonalizada) {
        this.cotizacionPersonalizada = cotizacionPersonalizada;
    }

    public String getTipoVidrio() {
        return tipoVidrio;
    }

    public void setTipoVidrio(String tipoVidrio) {
        this.tipoVidrio = tipoVidrio;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getAlto() {
        return alto;
    }

    public void setAlto(BigDecimal alto) {
        this.alto = alto;
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

    
    public Boolean getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Boolean instalacion) {
        this.instalacion = instalacion;
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

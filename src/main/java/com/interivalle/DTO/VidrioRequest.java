/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;
import java.math.BigDecimal;
/**
 *
 * @author mary_
 */


public class VidrioRequest {

    private Integer idCotizacion;
    private String tipoVidrio;
    private BigDecimal ancho;
    private BigDecimal alto;
    private Integer cantidad;
    private Boolean instalacion;
    private BigDecimal precioUnitario;
    private String descripcion;

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
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

    public Boolean getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Boolean instalacion) {
        this.instalacion = instalacion;
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
}
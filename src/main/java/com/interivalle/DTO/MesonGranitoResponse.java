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



public class MesonGranitoResponse {

    private Integer idMeson;
    private String tipoGranito;
    private BigDecimal largo;
    private BigDecimal ancho;
    private BigDecimal espesor;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private String descripcion;
    private BigDecimal subtotal;

    public Integer getIdMeson() {
        return idMeson;
    }

    public void setIdMeson(Integer idMeson) {
        this.idMeson = idMeson;
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

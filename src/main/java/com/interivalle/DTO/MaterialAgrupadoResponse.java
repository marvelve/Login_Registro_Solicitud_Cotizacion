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

public class MaterialAgrupadoResponse {

    private Integer idDetalle;
    private BigDecimal cantidad;
    private String material;
    private BigDecimal precioMaterial;
    private Integer semana;

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getPrecioMaterial() {
        return precioMaterial;
    }

    public void setPrecioMaterial(BigDecimal precioMaterial) {
        this.precioMaterial = precioMaterial;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }
}
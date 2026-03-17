/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;


import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author mary_
 */

public class CotizacionActividadResponse {

    private String actividadMaterial;
    private Integer semana;
    private BigDecimal valorManoObra;
    private BigDecimal totalMateriales;
    private BigDecimal totalProductos;
    private BigDecimal totalActividad;
    private List<CotizacionDetalleResponse> items;

    public String getActividadMaterial() {
        return actividadMaterial;
    }

    public void setActividadMaterial(String actividadMaterial) {
        this.actividadMaterial = actividadMaterial;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public BigDecimal getValorManoObra() {
        return valorManoObra;
    }

    public void setValorManoObra(BigDecimal valorManoObra) {
        this.valorManoObra = valorManoObra;
    }

    public BigDecimal getTotalMateriales() {
        return totalMateriales;
    }

    public void setTotalMateriales(BigDecimal totalMateriales) {
        this.totalMateriales = totalMateriales;
    }

    public BigDecimal getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(BigDecimal totalProductos) {
        this.totalProductos = totalProductos;
    }

    public BigDecimal getTotalActividad() {
        return totalActividad;
    }

    public void setTotalActividad(BigDecimal totalActividad) {
        this.totalActividad = totalActividad;
    }

    public List<CotizacionDetalleResponse> getItems() {
        return items;
    }

    public void setItems(List<CotizacionDetalleResponse> items) {
        this.items = items;
    }
}
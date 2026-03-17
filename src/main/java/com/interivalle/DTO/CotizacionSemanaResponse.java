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

public class CotizacionSemanaResponse {

    private Integer semana;
    private BigDecimal totalManoObra;
    private BigDecimal totalMateriales;
    private BigDecimal totalProductos;
    private BigDecimal totalSemana;
    private List<CotizacionDetalleResponse> items;

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public BigDecimal getTotalManoObra() {
        return totalManoObra;
    }

    public void setTotalManoObra(BigDecimal totalManoObra) {
        this.totalManoObra = totalManoObra;
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

    public BigDecimal getTotalSemana() {
        return totalSemana;
    }

    public void setTotalSemana(BigDecimal totalSemana) {
        this.totalSemana = totalSemana;
    }

    public List<CotizacionDetalleResponse> getItems() {
        return items;
    }

    public void setItems(List<CotizacionDetalleResponse> items) {
        this.items = items;
    }
}
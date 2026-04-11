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

public class ActividadAgrupadaResponse {

    private String actividad;
    private BigDecimal precioActividad;
    private List<MaterialAgrupadoResponse> materiales;

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public BigDecimal getPrecioActividad() {
        return precioActividad;
    }

    public void setPrecioActividad(BigDecimal precioActividad) {
        this.precioActividad = precioActividad;
    }

    public List<MaterialAgrupadoResponse> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialAgrupadoResponse> materiales) {
        this.materiales = materiales;
    }
}

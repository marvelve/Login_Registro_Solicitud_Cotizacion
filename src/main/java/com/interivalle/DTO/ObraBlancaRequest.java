/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 *
 * @author mary_
 */

public class ObraBlancaRequest {

    private Integer idCotizacionPersonalizada;

    @NotNull(message = "El id de la cotización es obligatorio")
    private Integer idCotizacion;

    @NotNull(message = "La actividad es obligatoria")
    private Integer idActividad;

    private String actividad;

    @NotBlank(message = "El lugar es obligatorio")
    private String lugar;

    private String unidad;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    private Integer semanas;

    private BigDecimal precioUnitario;

    private BigDecimal medida;

    private String descripcion;

    public Integer getIdCotizacionPersonalizada() {
        return idCotizacionPersonalizada;
    }

    public void setIdCotizacionPersonalizada(Integer idCotizacionPersonalizada) {
        this.idCotizacionPersonalizada = idCotizacionPersonalizada;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
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
}

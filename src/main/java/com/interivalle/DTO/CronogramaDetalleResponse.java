/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author mary_
 */

public class CronogramaDetalleResponse {

    private Integer idCronogramaDetalle;
    private String servicio;
    private String actividad;
    private String descripcion;
    private Integer semana;
    private LocalDate fechaInicioSemana;
    private LocalDate fechaFinSemana;
    private String trabajadorAsignado;
    private String estadoActividad;
    private BigDecimal porcentaje;
    private String novedades;

    public Integer getIdCronogramaDetalle() {
        return idCronogramaDetalle;
    }

    public void setIdCronogramaDetalle(Integer idCronogramaDetalle) {
        this.idCronogramaDetalle = idCronogramaDetalle;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public LocalDate getFechaInicioSemana() {
        return fechaInicioSemana;
    }

    public void setFechaInicioSemana(LocalDate fechaInicioSemana) {
        this.fechaInicioSemana = fechaInicioSemana;
    }

    public LocalDate getFechaFinSemana() {
        return fechaFinSemana;
    }

    public void setFechaFinSemana(LocalDate fechaFinSemana) {
        this.fechaFinSemana = fechaFinSemana;
    }

    public String getTrabajadorAsignado() {
        return trabajadorAsignado;
    }

    public void setTrabajadorAsignado(String trabajadorAsignado) {
        this.trabajadorAsignado = trabajadorAsignado;
    }

    public String getEstadoActividad() {
        return estadoActividad;
    }

    public void setEstadoActividad(String estadoActividad) {
        this.estadoActividad = estadoActividad;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNovedades() {
        return novedades;
    }

    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }
}

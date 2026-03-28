/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author mary_
 */

public class CronogramaResponse {

    private Integer idCronograma;
    private Integer idCotizacion;
    private String proyecto;
    private String estadoCronograma;
    private LocalDate fechaInicio;
    private LocalDate fechaInicioPlanificada;
    private LocalDate fechaFinEstimada;
    private Integer totalSemanas;
    private List<CronogramaDetalleResponse> detalles;

    public Integer getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getEstadoCronograma() {
        return estadoCronograma;
    }

    public void setEstadoCronograma(String estadoCronograma) {
        this.estadoCronograma = estadoCronograma;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaInicioPlanificada() {
        return fechaInicioPlanificada;
    }

    public void setFechaInicioPlanificada(LocalDate fechaInicioPlanificada) {
        this.fechaInicioPlanificada = fechaInicioPlanificada;
    }

    public LocalDate getFechaFinEstimada() {
        return fechaFinEstimada;
    }

    public void setFechaFinEstimada(LocalDate fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }

    public Integer getTotalSemanas() {
        return totalSemanas;
    }

    public void setTotalSemanas(Integer totalSemanas) {
        this.totalSemanas = totalSemanas;
    }

    public List<CronogramaDetalleResponse> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CronogramaDetalleResponse> detalles) {
        this.detalles = detalles;
    }
}

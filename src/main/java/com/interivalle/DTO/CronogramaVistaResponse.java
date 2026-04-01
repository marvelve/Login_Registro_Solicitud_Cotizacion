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


public class CronogramaVistaResponse {

    private Integer idCronograma;
    private Integer idCotizacion;
    private String nombreProyecto;
    private String estadoCronograma;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer avanceGeneral;
    private List<SemanaCronogramaDTO> semanas;
    private List<CronogramaDetalleVistaDTO> detalles;

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

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
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

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getAvanceGeneral() {
        return avanceGeneral;
    }

    public void setAvanceGeneral(Integer avanceGeneral) {
        this.avanceGeneral = avanceGeneral;
    }

    public List<SemanaCronogramaDTO> getSemanas() {
        return semanas;
    }

    public void setSemanas(List<SemanaCronogramaDTO> semanas) {
        this.semanas = semanas;
    }

    public List<CronogramaDetalleVistaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CronogramaDetalleVistaDTO> detalles) {
        this.detalles = detalles;
    }
}
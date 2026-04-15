/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import java.time.LocalDate;
/**
 *
 * @author mary_
 */



public class CronogramaListResponse {

    private Integer id;
    private Integer idCronograma;
    private Integer idCotizacion;
    private String nombreProyecto;
    private String nombreCliente;
    private String estadoCronograma;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer avanceGeneral;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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
}

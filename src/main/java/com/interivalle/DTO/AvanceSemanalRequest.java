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

public class AvanceSemanalRequest {

    private Integer idCronograma;
    private Integer numeroSemana;
    private String titulo;
    private String descripcion;
    private String observaciones;
    private BigDecimal porcentajeSemana;
    private BigDecimal porcentajeGeneral;

    public Integer getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public Integer getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(Integer numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getPorcentajeSemana() {
        return porcentajeSemana;
    }

    public void setPorcentajeSemana(BigDecimal porcentajeSemana) {
        this.porcentajeSemana = porcentajeSemana;
    }

    public BigDecimal getPorcentajeGeneral() {
        return porcentajeGeneral;
    }

    public void setPorcentajeGeneral(BigDecimal porcentajeGeneral) {
        this.porcentajeGeneral = porcentajeGeneral;
    }
}
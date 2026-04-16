/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.interivalle.Modelo.enums.EstadoActividadCronograma;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author mary_
 */

@Entity
@Table(name = "cronograma_detalle")
public class CronogramaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cronograma_detalle")
    private Integer idCronogramaDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cronograma", nullable = false)
    private Cronograma cronograma;

    @Column(name = "servicio", nullable = false, length = 120)
    private String servicio;

    @Column(name = "actividad", nullable = false, length = 200)
    private String actividad;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "semana", nullable = false)
    private Integer semana;

    @Column(name = "fecha_inicio_semana", nullable = false)
    private LocalDate fechaInicioSemana;

    @Column(name = "fecha_fin_semana", nullable = false)
    private LocalDate fechaFinSemana;

    @Column(name = "trabajador_asignado", length = 150)
    private String trabajadorAsignado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_actividad", nullable = false, length = 20)
    private EstadoActividadCronograma estadoActividad;

    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "novedades", length = 1000)
    private String novedades;
    
    
            

    public Integer getIdCronogramaDetalle() {
        return idCronogramaDetalle;
    }

    public void setIdCronogramaDetalle(Integer idCronogramaDetalle) {
        this.idCronogramaDetalle = idCronogramaDetalle;
    }

    public Cronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
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

    public EstadoActividadCronograma getEstadoActividad() {
        return estadoActividad;
    }

    public void setEstadoActividad(EstadoActividadCronograma estadoActividad) {
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
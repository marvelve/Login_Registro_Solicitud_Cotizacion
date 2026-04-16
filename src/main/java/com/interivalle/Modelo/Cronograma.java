/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.interivalle.Modelo.enums.EstadoCronograma;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mary_
 */


@Entity
@Table(name = "cronograma")
public class Cronograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cronograma")
    private Integer idCronograma;

    @OneToOne
    @JoinColumn(name = "id_cotizacion", nullable = false, unique = true)
    private Cotizacion cotizacion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_inicio_planificada", nullable = false)
    private LocalDate fechaInicioPlanificada;

    @Column(name = "fecha_fin_estimada", nullable = false)
    private LocalDate fechaFinEstimada;

    @Column(name = "total_semanas", nullable = false)
    private Integer totalSemanas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cronograma", nullable = false, length = 20)
    private EstadoCronograma estadoCronograma;

    @OneToMany(mappedBy = "cronograma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CronogramaDetalle> detalles = new ArrayList<>();
    
    private BigDecimal avanceGeneral;
    private String estado;

    public Integer getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
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

    public EstadoCronograma getEstadoCronograma() {
        return estadoCronograma;
    }

    public void setEstadoCronograma(EstadoCronograma estadoCronograma) {
        this.estadoCronograma = estadoCronograma;
    }

    public List<CronogramaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CronogramaDetalle> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getAvanceGeneral() {
        return avanceGeneral;
    }

    public void setAvanceGeneral(BigDecimal avanceGeneral) {
        this.avanceGeneral = avanceGeneral;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}

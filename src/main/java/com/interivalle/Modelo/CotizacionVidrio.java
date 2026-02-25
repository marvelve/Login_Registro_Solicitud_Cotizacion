/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
/**
 *
 * @author mary_
 */
@Entity
@Table(name = "cotizacion_vidrio")
public class CotizacionVidrio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCotizacionVidrio;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_solicitud_servicio", nullable = false, unique = true)
    private SolicitudServicios solicitudServicios;

    @Column(name = "cantidad_banos", nullable = false)
    private Integer cantidadBanos;

    // "PLATEADO" | "NEGRO"
    @Column(name = "color", nullable = false)
    private String color;

    // "BATIENTE" | "CORREDIZA"
    @Column(name = "apertura", nullable = false)
    private String apertura;

    // getters & setters

    public Integer getIdCotizacionVidrio() {
        return idCotizacionVidrio;
    }

    public void setIdCotizacionVidrio(Integer idCotizacionVidrio) {
        this.idCotizacionVidrio = idCotizacionVidrio;
    }

    public SolicitudServicios getSolicitudServicio() {
        return solicitudServicios;
    }

    public void setSolicitudServicio(SolicitudServicios solicitudServicios) {
        this.solicitudServicios = solicitudServicios;
    }

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getApertura() {
        return apertura;
    }

    public void setApertura(String apertura) {
        this.apertura = apertura;
    }
}


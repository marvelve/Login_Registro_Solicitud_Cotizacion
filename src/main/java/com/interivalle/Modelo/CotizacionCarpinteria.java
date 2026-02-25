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
@Table(name = "cotizacion_carpinteria")
public class CotizacionCarpinteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCotizacionCarpinteria;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_solicitud_servicio", nullable = false, unique = true)
    private SolicitudServicios solicitudServicios;

    @Column(name = "medida_apto_interna", nullable = false)
    private Double medidaAptoInterna;

    @Column(name = "cantidad_closet", nullable = false)
    private Integer cantidadCloset;

    @Column(name = "cantidad_puertas", nullable = false)
    private Integer cantidadPuertas;

    @Column(name = "cantidad_banos", nullable = false)
    private Integer cantidadBanos;

    // getters & setters

    public Integer getIdCotizacionCarpinteria() {
        return idCotizacionCarpinteria;
    }

    public void setIdCotizacionCarpinteria(Integer idCotizacionCarpinteria) {
        this.idCotizacionCarpinteria = idCotizacionCarpinteria;
    }

    public SolicitudServicios getSolicitudServicios() {
        return solicitudServicios;
    }

    public void setSolicitudServicios(SolicitudServicios solicitudServicios) {
        this.solicitudServicios = solicitudServicios;
    }

    public Double getMedidaAptoInterna() {
        return medidaAptoInterna;
    }

    public void setMedidaAptoInterna(Double medidaAptoInterna) {
        this.medidaAptoInterna = medidaAptoInterna;
    }

    public Integer getCantidadCloset() {
        return cantidadCloset;
    }

    public void setCantidadCloset(Integer cantidadCloset) {
        this.cantidadCloset = cantidadCloset;
    }

    public Integer getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(Integer cantidadPuertas) {
        this.cantidadPuertas = cantidadPuertas;
    }

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }
}


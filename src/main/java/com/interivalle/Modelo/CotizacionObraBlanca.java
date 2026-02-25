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
@Table(name = "cotizacion_obra_blanca")
public class CotizacionObraBlanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCotizacionObraBlanca;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_solicitud_servicio", nullable = false, unique = true)
    private SolicitudServicios solicitudServicios;

    @Column(name = "medida_apto_interna", nullable = false)
    private Double medidaAptoInterna;

    @Column(name = "cantidad_banos", nullable = false)
    private Integer cantidadBanos;

    // "SI" | "NO"  (también podrías usar Boolean)
    @Column(name = "division_pared", nullable = false)
    private Boolean divisionPared;

    // "ESTUCO" | "PANEL"
    @Column(name = "tipo_cielo", nullable = false)
    private String tipoCielo;

    // getters & setters

    public Integer getIdCotizacionObraBlanca() {
        return idCotizacionObraBlanca;
    }

    public void setIdCotizacionObraBlanca(Integer idCotizacionObraBlanca) {
        this.idCotizacionObraBlanca = idCotizacionObraBlanca;
    }

    public SolicitudServicios getSolicitudServicio() {
        return solicitudServicios;
    }

    public void setSolicitudServicio(SolicitudServicios solicitudServicios) {
        this.solicitudServicios = solicitudServicios;
    }

    public Double getMedidaAptoInterna() {
        return medidaAptoInterna;
    }

    public void setMedidaAptoInterna(Double medidaAptoInterna) {
        this.medidaAptoInterna = medidaAptoInterna;
    }

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public Boolean getDivisionPared() {
        return divisionPared;
    }

    public void setDivisionPared(Boolean divisionPared) {
        this.divisionPared = divisionPared;
    }


    public String getTipoCielo() {
        return tipoCielo;
    }

    public void setTipoCielo(String tipoCielo) {
        this.tipoCielo = tipoCielo;
    }
}


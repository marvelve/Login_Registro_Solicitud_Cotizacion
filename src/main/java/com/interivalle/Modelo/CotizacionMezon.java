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
@Table(name = "cotizacion_mezon")
public class CotizacionMezon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCotizacionMezon;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_solicitud_servicio", nullable = false, unique = true)
    private SolicitudServicios solicitudServicios;

    @Column(name = "mezon_cocina", nullable = false)
    private Boolean mezonCocina = false;

    @Column(name = "mezon_barra", nullable = false)
    private Boolean mezonBarra = false;

    @Column(name = "mezon_bano", nullable = false)
    private Boolean mezonBano = false;

    // getters & setters

    public Integer getIdCotizacionMezon() {
        return idCotizacionMezon;
    }

    public void setIdCotizacionMezon(Integer idCotizacionMezon) {
        this.idCotizacionMezon = idCotizacionMezon;
    }

    public SolicitudServicios getSolicitudServicio() {
        return solicitudServicios;
    }

    public void setSolicitudServicio(SolicitudServicios solicitudServicio) {
        this.solicitudServicios = solicitudServicio;
    }

    public Boolean getMezonCocina() {
        return mezonCocina;
    }

    public void setMezonCocina(Boolean mezonCocina) {
        this.mezonCocina = mezonCocina;
    }

    public Boolean getMezonBarra() {
        return mezonBarra;
    }

    public void setMezonBarra(Boolean mezonBarra) {
        this.mezonBarra = mezonBarra;
    }

    public Boolean getMezonBano() {
        return mezonBano;
    }

    public void setMezonBano(Boolean mezonBano) {
        this.mezonBano = mezonBano;
    }
}


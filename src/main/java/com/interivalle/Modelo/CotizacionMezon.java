/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

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
    @Column(name = "id_cotizacion_mezon")
    private Integer idCotizacionMezon;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cotizacion", nullable = false, unique = true)
    private Cotizacion cotizacion;

    @Column(name = "mezon_cocina", nullable = false)
    private Boolean mezonCocina;

    @Column(name = "mezon_barra", nullable = false)
    private Boolean mezonBarra;

    @Column(name = "mezon_lavamanos", nullable = false)
    private Boolean mezonLavamanos;

    public Integer getIdCotizacionMezon() {
        return idCotizacionMezon;
    }

    public void setIdCotizacionMezon(Integer idCotizacionMezon) {
        this.idCotizacionMezon = idCotizacionMezon;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
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

    public Boolean getMezonLavamanos() {
        return mezonLavamanos;
    }

    public void setMezonLavamanos(Boolean mezonLavamanos) {
        this.mezonLavamanos = mezonLavamanos;
    }
}
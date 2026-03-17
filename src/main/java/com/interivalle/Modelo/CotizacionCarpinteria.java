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
@Table(name = "cotizacion_carpinteria")
public class CotizacionCarpinteria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion_carpinteria")
    private Integer idCotizacionCarpinteria;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cotizacion", nullable = false, unique = true)
    private Cotizacion cotizacion;

    @Column(name = "cantidad_closet", nullable = false)
    private Integer cantidadCloset;

    @Column(name = "cantidad_puertas", nullable = false)
    private Integer cantidadPuertas;

    @Column(name = "mueble_alto_cocina", nullable = false)
    private Boolean muebleAltoCocina;

    @Column(name = "mueble_bajo_cocina", nullable = false)
    private Boolean muebleBajoCocina;

    @Column(name = "cantidad_mueble_bano", nullable = false)
    private Integer cantidadMuebleBano;

    public Integer getIdCotizacionCarpinteria() {
        return idCotizacionCarpinteria;
    }

    public void setIdCotizacionCarpinteria(Integer idCotizacionCarpinteria) {
        this.idCotizacionCarpinteria = idCotizacionCarpinteria;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
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

    public Boolean getMuebleAltoCocina() {
        return muebleAltoCocina;
    }

    public void setMuebleAltoCocina(Boolean muebleAltoCocina) {
        this.muebleAltoCocina = muebleAltoCocina;
    }

    public Boolean getMuebleBajoCocina() {
        return muebleBajoCocina;
    }

    public void setMuebleBajoCocina(Boolean muebleBajoCocina) {
        this.muebleBajoCocina = muebleBajoCocina;
    }

    public Integer getCantidadMuebleBano() {
        return cantidadMuebleBano;
    }

    public void setCantidadMuebleBano(Integer cantidadMuebleBano) {
        this.cantidadMuebleBano = cantidadMuebleBano;
    }
}

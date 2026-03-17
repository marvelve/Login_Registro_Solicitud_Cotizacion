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
@Table(name = "cotizacion_mano_obra")

public class CotizacionManoObra {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion_mano_obra")
    private Integer idCotizacionManoObra;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cotizacion", nullable = false, unique = true)
    private Cotizacion cotizacion;

    @Column(name = "medida_area_privada", nullable = false)
    private Double medidaAreaPrivada;

    @Column(name = "cantidad_banos", nullable = false)
    private Integer cantidadBanos;

    @Column(name = "tipo_cielo", nullable = false, length = 30)
    private String tipoCielo;

    @Column(name = "division_pared", nullable = false)
    private Boolean divisionPared;

    public Integer getIdCotizacionManoObra() {
        return idCotizacionManoObra;
    }

    public void setIdCotizacionManoObra(Integer idCotizacionManoObra) {
        this.idCotizacionManoObra = idCotizacionManoObra;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Double getMedidaAreaPrivada() {
        return medidaAreaPrivada;
    }

    public void setMedidaAreaPrivada(Double medidaAreaPrivada) {
        this.medidaAreaPrivada = medidaAreaPrivada;
    }

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public String getTipoCielo() {
        return tipoCielo;
    }

    public void setTipoCielo(String tipoCielo) {
        this.tipoCielo = tipoCielo;
    }

    public Boolean getDivisionPared() {
        return divisionPared;
    }

    public void setDivisionPared(Boolean divisionPared) {
        this.divisionPared = divisionPared;
    }
}

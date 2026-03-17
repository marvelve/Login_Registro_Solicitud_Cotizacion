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
@Table(name = "cotizacion_vidrio")
public class CotizacionVidrio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion_vidrio")
    private Integer idCotizacionVidrio;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cotizacion", nullable = false, unique = true)
    private Cotizacion cotizacion;

    @Column(name = "cantidad_banos", nullable = false)
    private Integer cantidadBanos;

    @Column(name = "tipo_apertura", nullable = false, length = 30)
    private String tipoApertura;

    @Column(name = "color_accesorios", nullable = false, length = 30)
    private String colorAccesorios;

    public Integer getIdCotizacionVidrio() {
        return idCotizacionVidrio;
    }

    public void setIdCotizacionVidrio(Integer idCotizacionVidrio) {
        this.idCotizacionVidrio = idCotizacionVidrio;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public String getTipoApertura() {
        return tipoApertura;
    }

    public void setTipoApertura(String tipoApertura) {
        this.tipoApertura = tipoApertura;
    }

    public String getColorAccesorios() {
        return colorAccesorios;
    }

    public void setColorAccesorios(String colorAccesorios) {
        this.colorAccesorios = colorAccesorios;
    }
}

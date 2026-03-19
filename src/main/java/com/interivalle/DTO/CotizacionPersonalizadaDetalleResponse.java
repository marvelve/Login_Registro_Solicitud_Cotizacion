/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author mary_
 */
public class CotizacionPersonalizadaDetalleResponse {

    private Integer idCotizacion;
    private Integer idSolicitud;
    private Integer idUsuario;
    private String nombreProyecto;
    private LocalDate fechaCotizacion;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String observacionGeneral;

    private List<ObraBlancaResponse> obraBlanca;
    private List<CarpinteriaResponse> carpinteria;
    private List<VidrioResponse> vidrio;
    private List<MesonGranitoResponse> mesonGranito;

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public LocalDate getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(LocalDate fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservacionGeneral() {
        return observacionGeneral;
    }

    public void setObservacionGeneral(String observacionGeneral) {
        this.observacionGeneral = observacionGeneral;
    }

    public List<ObraBlancaResponse> getObraBlanca() {
        return obraBlanca;
    }

    public void setObraBlanca(List<ObraBlancaResponse> obraBlanca) {
        this.obraBlanca = obraBlanca;
    }

    public List<CarpinteriaResponse> getCarpinteria() {
        return carpinteria;
    }

    public void setCarpinteria(List<CarpinteriaResponse> carpinteria) {
        this.carpinteria = carpinteria;
    }

    public List<VidrioResponse> getVidrio() {
        return vidrio;
    }

    public void setVidrio(List<VidrioResponse> vidrio) {
        this.vidrio = vidrio;
    }

    public List<MesonGranitoResponse> getMesonGranito() {
        return mesonGranito;
    }

    public void setMesonGranito(List<MesonGranitoResponse> mesonGranito) {
        this.mesonGranito = mesonGranito;
    }
}

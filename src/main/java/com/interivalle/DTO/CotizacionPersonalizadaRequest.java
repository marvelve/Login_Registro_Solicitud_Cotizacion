/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import java.util.List;

/**
 *
 * @author mary_
 */
public class CotizacionPersonalizadaRequest {
    private Integer idCotizacionPersonalizada;
    private Integer idCotizacion;
    private Integer idSolicitud;
    private String nombreProyecto;
    private String observacionGeneral;
    private List<ObraBlancaRequest> obraBlanca;

    
    public Integer getIdCotizacionPersonalizada() {
        return idCotizacionPersonalizada;
    }

    public void setIdCotizacionPersonalizada(Integer idCotizacionPersonalizada) {
        this.idCotizacionPersonalizada = idCotizacionPersonalizada;
    }

    
    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getObservacionGeneral() {
        return observacionGeneral;
    }

    public void setObservacionGeneral(String observacionGeneral) {
        this.observacionGeneral = observacionGeneral;
    }

    public List<ObraBlancaRequest> getObraBlanca() { return obraBlanca; }
    public void setObraBlanca(List<ObraBlancaRequest> obraBlanca) { this.obraBlanca = obraBlanca; }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }
    
    
}

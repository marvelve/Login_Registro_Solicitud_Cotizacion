
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

public class SolicitudResponse {

    private Integer idSolicitud;
    private String tipoSolicitud;
    private String estado;
    private String nombreProyecto;
    private List<SolicitudServicioItem> solicitudServicios;;

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public List<SolicitudServicioItem> getSolicitudServicios() {
        return solicitudServicios;
    }

    public void setSolicitudServicios(List<SolicitudServicioItem> solicitudServicios) {
        this.solicitudServicios = solicitudServicios;
    }


}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

/**
 *
 * @author mary_
 */

public class SolicitudServicioItem {
    private Integer idSolicitudServicio; // id del detalle (tabla puente)
    private Integer idServicio;          // id del servicio (1,2,3,4)
    private String nombreServicio;       // nombre
    private String estado;               // PENDIENTE / GENERADO

    public Integer getIdSolicitudServicio() { return idSolicitudServicio; }
    public void setIdSolicitudServicio(Integer idSolicitudServicio) { this.idSolicitudServicio = idSolicitudServicio; }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

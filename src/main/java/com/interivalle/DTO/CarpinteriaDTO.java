/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

/**
 *
 * @author mary_
 */

public class CarpinteriaDTO {

    private Integer idSolicitudServicio;

    private Double medidaAptoInterna;
    private Integer cantidadCloset;
    private Integer cantidadPuertas;
    private Integer cantidadBanos;

    public Integer getIdSolicitudServicio() {
        return idSolicitudServicio;
    }

    public void setIdSolicitudServicio(Integer idSolicitudServicio) {
        this.idSolicitudServicio = idSolicitudServicio;
    }

    public Double getMedidaAptoInterna() {
        return medidaAptoInterna;
    }

    public void setMedidaAptoInterna(Double medidaAptoInterna) {
        this.medidaAptoInterna = medidaAptoInterna;
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

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }
}


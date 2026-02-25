/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

/**
 *
 * @author mary_
 */
public class ObraBlancaDTO {

    private Integer idSolicitudServicio;

    private Double medidaAptoInterna;
    private Integer cantidadBanos;
    private Boolean divisionPared;
    private String tipoCielo;

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

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public Boolean getDivisionPared() {
        return divisionPared;
    }

    public void setDivisionPared(Boolean divisionPared) {
        this.divisionPared = divisionPared;
    }


    public String getTipoCielo() {
        return tipoCielo;
    }

    public void setTipoCielo(String tipoCielo) {
        this.tipoCielo = tipoCielo;
    }
}


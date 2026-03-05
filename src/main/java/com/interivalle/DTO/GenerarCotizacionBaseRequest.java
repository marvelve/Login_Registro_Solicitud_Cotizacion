/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.constraints.NotNull;
/**
 *
 * @author mary_
 */



public class GenerarCotizacionBaseRequest {

    @NotNull
    private Integer solicitudId;

    // Campos compartidos / base
    private Double medidaAptoInterna;

    // Ejemplos (ajusta a tus forms reales)
    private Boolean divisionPared;
    private String tipoCielo;

    private Integer cantidadCloset;
    private Integer cantidadPuertas;

    public Integer getSolicitudId() { return solicitudId; }
    public void setSolicitudId(Integer solicitudId) { this.solicitudId = solicitudId; }

    public Double getMedidaAptoInterna() { return medidaAptoInterna; }
    public void setMedidaAptoInterna(Double medidaAptoInterna) { this.medidaAptoInterna = medidaAptoInterna; }

    public Boolean getDivisionPared() { return divisionPared; }
    public void setDivisionPared(Boolean divisionPared) { this.divisionPared = divisionPared; }

    public String getTipoCielo() { return tipoCielo; }
    public void setTipoCielo(String tipoCielo) { this.tipoCielo = tipoCielo; }

    public Integer getCantidadCloset() { return cantidadCloset; }
    public void setCantidadCloset(Integer cantidadCloset) { this.cantidadCloset = cantidadCloset; }

    public Integer getCantidadPuertas() { return cantidadPuertas; }
    public void setCantidadPuertas(Integer cantidadPuertas) { this.cantidadPuertas = cantidadPuertas; }
}
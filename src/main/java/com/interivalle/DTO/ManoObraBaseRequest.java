/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * @author mary_
 */
public class ManoObraBaseRequest {

    @NotNull(message = "La medida del área privada es obligatoria")
    @Min(value = 1, message = "La medida del área privada debe ser mayor a 0")
    private Double medidaAreaPrivada;

    @NotNull(message = "La cantidad de baños es obligatoria")
    @Min(value = 1, message = "La cantidad de baños debe ser mayor a 0")
    private Integer cantidadBanos;

    @NotBlank(message = "El tipo de cielo es obligatorio")
    private String tipoCielo; // DRYWALL o ESTUCO

    @NotNull(message = "Debe indicar si tiene división en pared")
    private Boolean divisionPared;
    
    private BigDecimal MetrosCuadradosPanelYeso;
    private Integer CantidadPoyos;
    private Integer CantidadPuntosElectricos;
    private BigDecimal MetrosCuadradosMuro;
    private BigDecimal MetrosCuadradosCielo;
    private BigDecimal MetrosCuadradosTaparTuberias;

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

    public BigDecimal getMetrosCuadradosPanelYeso() {
        return MetrosCuadradosPanelYeso;
    }

    public void setMetrosCuadradosPanelYeso(BigDecimal MetrosCuadradosPanelYeso) {
        this.MetrosCuadradosPanelYeso = MetrosCuadradosPanelYeso;
    }

    public Integer getCantidadPoyos() {
        return CantidadPoyos;
    }

    public void setCantidadPoyos(Integer CantidadPoyos) {
        this.CantidadPoyos = CantidadPoyos;
    }

    public Integer getCantidadPuntosElectricos() {
        return CantidadPuntosElectricos;
    }

    public void setCantidadPuntosElectricos(Integer CantidadPuntosElectricos) {
        this.CantidadPuntosElectricos = CantidadPuntosElectricos;
    }

    public BigDecimal getMetrosCuadradosMuro() {
        return MetrosCuadradosMuro;
    }

    public void setMetrosCuadradosMuro(BigDecimal MetrosCuadradosMuro) {
        this.MetrosCuadradosMuro = MetrosCuadradosMuro;
    }

    public BigDecimal getMetrosCuadradosCielo() {
        return MetrosCuadradosCielo;
    }

    public void setMetrosCuadradosCielo(BigDecimal MetrosCuadradosCielo) {
        this.MetrosCuadradosCielo = MetrosCuadradosCielo;
    }

    public BigDecimal getMetrosCuadradosTaparTuberias() {
        return MetrosCuadradosTaparTuberias;
    }

    public void setMetrosCuadradosTaparTuberias(BigDecimal MetrosCuadradosTaparTuberias) {
        this.MetrosCuadradosTaparTuberias = MetrosCuadradosTaparTuberias;
    }
    
    
}


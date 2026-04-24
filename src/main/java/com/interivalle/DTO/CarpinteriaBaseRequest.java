/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * @author mary_
 */

public class CarpinteriaBaseRequest {

    @NotNull(message = "La cantidad de closet es obligatoria")
    @Min(value = 0, message = "La cantidad de closet no puede ser negativa")
    private Integer cantidadCloset;

    @NotNull(message = "La cantidad de puertas es obligatoria")
    @Min(value = 0, message = "La cantidad de puertas no puede ser negativa")
    private Integer cantidadPuertas;

    @NotNull(message = "Debe indicar la medida del mueble alto cocina")
    private BigDecimal  muebleAltoCocina;

    @NotNull(message = "Debe indicar la medida del mueble bajo cocina")
    private BigDecimal  muebleBajoCocina;

    @Min(value = 0, message = "La cantidad de baños no puede ser negativa")
    private Integer cantidadBanos;
    
    @NotNull(message = "La cantidad de muebles de baño es obligatoria")
    @Min(value = 0, message = "La cantidad de muebles de baño no puede ser negativa")
    private Integer cantidadMuebleBajoBano;
    
    @NotNull(message = "La cantidad de muebles de baño es obligatoria")
    @Min(value = 0, message = "La cantidad de muebles de baño no puede ser negativa")
    private Integer cantidadMuebleAltoBano;

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

    public BigDecimal getMuebleAltoCocina() {
        return muebleAltoCocina;
    }

    public void setMuebleAltoCocina(BigDecimal muebleAltoCocina) {
        this.muebleAltoCocina = muebleAltoCocina;
    }

    public BigDecimal getMuebleBajoCocina() {
        return muebleBajoCocina;
    }

    public void setMuebleBajoCocina(BigDecimal muebleBajoCocina) {
        this.muebleBajoCocina = muebleBajoCocina;
    }

    public Integer getCantidadMuebleBajoBano() {
        return cantidadMuebleBajoBano;
    }

    public void setCantidadMuebleBajoBano(Integer cantidadMuebleBajoBano) {
        this.cantidadMuebleBajoBano = cantidadMuebleBajoBano;
    }

    public Integer getCantidadMuebleAltoBano() {
        return cantidadMuebleAltoBano;
    }

    public void setCantidadMuebleAltoBano(Integer cantidadMuebleAltoBano) {
        this.cantidadMuebleAltoBano = cantidadMuebleAltoBano;
    }

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    
}
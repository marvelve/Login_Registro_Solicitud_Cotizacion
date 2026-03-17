/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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

    @NotNull(message = "Debe indicar si tiene mueble alto cocina")
    private Boolean muebleAltoCocina;

    @NotNull(message = "Debe indicar si tiene mueble bajo cocina")
    private Boolean muebleBajoCocina;

    @NotNull(message = "La cantidad de muebles de baño es obligatoria")
    @Min(value = 0, message = "La cantidad de muebles de baño no puede ser negativa")
    private Integer cantidadMuebleBano;

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

    public Boolean getMuebleAltoCocina() {
        return muebleAltoCocina;
    }

    public void setMuebleAltoCocina(Boolean muebleAltoCocina) {
        this.muebleAltoCocina = muebleAltoCocina;
    }

    public Boolean getMuebleBajoCocina() {
        return muebleBajoCocina;
    }

    public void setMuebleBajoCocina(Boolean muebleBajoCocina) {
        this.muebleBajoCocina = muebleBajoCocina;
    }

    public Integer getCantidadMuebleBano() {
        return cantidadMuebleBano;
    }

    public void setCantidadMuebleBano(Integer cantidadMuebleBano) {
        this.cantidadMuebleBano = cantidadMuebleBano;
    }
}
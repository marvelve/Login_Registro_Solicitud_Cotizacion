/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author mary_
 */

public class VidrioBaseRequest {

    @NotNull(message = "La cantidad de baños es obligatoria")
    @Min(value = 1, message = "La cantidad de baños debe ser mayor a 0")
    private Integer cantidadBanos;

    @NotBlank(message = "Debe indicar si es corrediza o batiente")
    private String tipoApertura; // CORREDIZA o BATIENTE

    @NotBlank(message = "Debe indicar el color de accesorios")
    private String colorAccesorios; // NEGROS o PLATEADOS

    public Integer getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(Integer cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public String getTipoApertura() {
        return tipoApertura;
    }

    public void setTipoApertura(String tipoApertura) {
        this.tipoApertura = tipoApertura;
    }

    public String getColorAccesorios() {
        return colorAccesorios;
    }

    public void setColorAccesorios(String colorAccesorios) {
        this.colorAccesorios = colorAccesorios;
    }
}


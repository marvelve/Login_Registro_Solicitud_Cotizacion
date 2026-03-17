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

public class MezonBaseRequest {

     @NotNull(message = "Debe indicar si desea mesón de cocina")
    private Boolean mezonCocina;

    @NotNull(message = "Debe indicar si desea mesón barra")
    private Boolean mezonBarra;

    @NotNull(message = "Debe indicar si desea mesón lavamanos")
    private Boolean mezonLavamanos;

    public Boolean getMezonCocina() {
        return mezonCocina;
    }

    public void setMezonCocina(Boolean mezonCocina) {
        this.mezonCocina = mezonCocina;
    }

    public Boolean getMezonBarra() {
        return mezonBarra;
    }

    public void setMezonBarra(Boolean mezonBarra) {
        this.mezonBarra = mezonBarra;
    }

    public Boolean getMezonLavamanos() {
        return mezonLavamanos;
    }

    public void setMezonLavamanos(Boolean mezonLavamanos) {
        this.mezonLavamanos = mezonLavamanos;
    }
}
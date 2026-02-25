/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

/**
 *
 * @author mary_
 */

public class MezonDTO {

    private Integer idSolicitudServicio;

    private Boolean mezonCocina;
    private Boolean mezonBarra;
    private Boolean mezonBano;

    public Integer getIdSolicitudServicio() {
        return idSolicitudServicio;
    }

    public void setIdSolicitudServicio(Integer idSolicitudServicio) {
        this.idSolicitudServicio = idSolicitudServicio;
    }

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

    public Boolean getMezonBano() {
        return mezonBano;
    }

    public void setMezonBano(Boolean mezonBano) {
        this.mezonBano = mezonBano;
    }
}


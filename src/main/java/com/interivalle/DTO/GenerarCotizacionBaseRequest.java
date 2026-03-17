/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
/**
 *
 * @author mary_
 */



public class GenerarCotizacionBaseRequest {

    @NotNull(message = "La solicitud es obligatoria")
    private Integer solicitudId;

    @Valid
    private ManoObraBaseRequest manoObra;

    @Valid
    private CarpinteriaBaseRequest carpinteria;

    @Valid
    private VidrioBaseRequest vidrio;

    @Valid
    private MezonBaseRequest mezon;

    public Integer getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Integer solicitudId) {
        this.solicitudId = solicitudId;
    }

    public ManoObraBaseRequest getManoObra() {
        return manoObra;
    }

    public void setManoObra(ManoObraBaseRequest manoObra) {
        this.manoObra = manoObra;
    }

    public CarpinteriaBaseRequest getCarpinteria() {
        return carpinteria;
    }

    public void setCarpinteria(CarpinteriaBaseRequest carpinteria) {
        this.carpinteria = carpinteria;
    }

    public VidrioBaseRequest getVidrio() {
        return vidrio;
    }

    public void setVidrio(VidrioBaseRequest vidrio) {
        this.vidrio = vidrio;
    }

    public MezonBaseRequest getMezon() {
        return mezon;
    }

    public void setMezon(MezonBaseRequest mezon) {
        this.mezon = mezon;
    }
}
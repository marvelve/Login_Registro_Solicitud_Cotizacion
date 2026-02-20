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


public class EstadoUpdate {
    @NotNull
    private Boolean estadoUsuario; // true/false

    public Boolean getEstadoUsuario() { return estadoUsuario; }
    public void setEstadoUsuario(Boolean estadoUsuario) { this.estadoUsuario = estadoUsuario; }
}


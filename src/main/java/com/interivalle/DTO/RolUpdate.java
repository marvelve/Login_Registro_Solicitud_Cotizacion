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
public class RolUpdate {
    @NotNull
    private Integer idRol; // 1 ADMIN, 2 SUPERVISOR, 3 CLIENTE

    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
}


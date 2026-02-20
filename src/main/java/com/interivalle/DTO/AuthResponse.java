/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

/**
 *
 * @author mary_
 */

public class AuthResponse {

    private String token;
    private String tipo = "Bearer";
    private String correoUsuario;
    private Integer idRol;

    public AuthResponse(String token, String correoUsuario, Integer idRol) {
        this.token = token;
        this.correoUsuario = correoUsuario;
        this.idRol = idRol;
    }

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
    public String getCorreoUsuario() { return correoUsuario; }
    public Integer getIdRol() { return idRol; }
}


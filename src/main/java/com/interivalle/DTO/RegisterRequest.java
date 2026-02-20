/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    package com.interivalle.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 *
 * @author mary_
 */
public class RegisterRequest {

    @NotBlank
    private String nombreUsuario;

    @NotBlank @Email
    private String correoUsuario;

    @NotBlank @Size(min = 6)
    private String contrasenaUsuario;

    private Integer idRol;
    @NotBlank
    private String celularUsuario;
    @NotBlank
    private String ciudadUsuario;

    public String getCelularUsuario() { return celularUsuario; }
    public void setCelularUsuario(String celularUsuario) { this.celularUsuario = celularUsuario; }

    public String getCiudadUsuario() { return ciudadUsuario; }
    public void setCiudadUsuario(String ciudadUsuario) { this.ciudadUsuario = ciudadUsuario; }


    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public String getContrasenaUsuario() { return contrasenaUsuario; }
    public void setContrasenaUsuario(String contrasenaUsuario) { this.contrasenaUsuario = contrasenaUsuario; }

    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
}
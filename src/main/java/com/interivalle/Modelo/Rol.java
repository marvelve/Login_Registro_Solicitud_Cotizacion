/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.interivalle.Modelo;

/**
 * @author mary_
 * Clase que representa la entidad Rol.
 * Mapea la tabla 'rol' en la base de datos.
 */
import jakarta.persistence.*;

@Entity
public class Rol {

    // Identificador único autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    Integer idRol;

    // Nombre del rol (ej: ADMIN, CLIENTE, SUPERVISOR)
    @Column(nullable = false, unique = true, length = 50)
    String nombreRol;

    // Descripcion opcional del rol
    String descripcionRol;

    // Constructores
    public Rol() {}

    public Rol(String nombreRol, String descripcionRol) {
        this.nombreRol = nombreRol;
        this.descripcionRol = descripcionRol;
    }

    // Getters y Setters
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }

    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }

    public String getDescripcionRol() { return descripcionRol; }
    public void setDescripcionRol(String descripcionRol) { this.descripcionRol = descripcionRol; }
}


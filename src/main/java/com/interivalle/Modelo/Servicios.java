/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import jakarta.persistence.*;
/**
 *
 * @author mary_
 */
@Entity
@Table(name = "servicios")
public class Servicios {

    // ------------------ ATRIBUTOS ------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicios")
    private Integer idServicios;

    @Column(name = "nombre_servicio", nullable = false, unique = true)
    private String nombreServicio;

    @Column(name = "activo")
    private Boolean activo = true;

    // ------------------ CONSTRUCTORES ------------------

    public Servicios() {
        // Constructor vacío requerido por JPA
    }

    public Servicios(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    // ------------------ GETTERS Y SETTERS ------------------

    public Integer getIdServicio() {
        return idServicios;
    }

    public void setIdServicio(Integer idServicios) {
        this.idServicios = idServicios;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}


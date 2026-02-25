/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mary_
 */

@Entity
@Table(name = "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_proyecto_usuario", nullable = false)
    private String nombreProyectoUsuario;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    // "COTIZACION_BASE" | "VISITA_TECNICA"
    @Column(name = "tipo_solicitud", nullable = false)
    private String tipoSolicitud;

    // "BORRADOR" | "ENVIADA" | "APROBADA" | "RECHAZADA"
    @Column(name = "estado", nullable = false)
    private String estado;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudServicios> serviciosSeleccionados = new ArrayList<>();

    // Si aún vas a manejar VisitaTecnica en otra tabla:
   // @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
   // private VisitaTecnica visitaTecnica;

    // ---------- getters & setters ----------

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreProyectoUsuario() {
        return nombreProyectoUsuario;
    }

    public void setNombreProyectoUsuario(String nombreProyectoUsuario) {
        this.nombreProyectoUsuario = nombreProyectoUsuario;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<SolicitudServicios> getServiciosSeleccionados() {
        return serviciosSeleccionados;
    }

    public void setServiciosSeleccionados(List<SolicitudServicios> serviciosSeleccionados) {
        this.serviciosSeleccionados = serviciosSeleccionados;
    }

}


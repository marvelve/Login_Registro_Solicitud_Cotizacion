/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import com.interivalle.Modelo.enums.TipoObservacion;
import java.time.LocalDateTime;
/**
 *
 * @author mary_
 */

public class CotizacionObservacionResponse {

    private Integer idObservacion;
    private TipoObservacion tipo;
    private String mensaje;
    private String usuarioNombre;
    private LocalDateTime fecha;

    public Integer getIdObservacion() { return idObservacion; }
    public void setIdObservacion(Integer idObservacion) { this.idObservacion = idObservacion; }

    public TipoObservacion getTipo() { return tipo; }
    public void setTipo(TipoObservacion tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}

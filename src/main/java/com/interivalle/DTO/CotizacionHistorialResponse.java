/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import com.interivalle.Modelo.enums.EstadoCotizacion;
import java.time.LocalDateTime;
/**
 *
 * @author mary_
 */

public class CotizacionHistorialResponse {

    private Integer idHistorial;
    private EstadoCotizacion estadoAnterior;
    private EstadoCotizacion estadoNuevo;
    private String usuarioNombre;
    private LocalDateTime fecha;

    public Integer getIdHistorial() { return idHistorial; }
    public void setIdHistorial(Integer idHistorial) { this.idHistorial = idHistorial; }

    public EstadoCotizacion getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoCotizacion estadoAnterior) { this.estadoAnterior = estadoAnterior; }

    public EstadoCotizacion getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoCotizacion estadoNuevo) { this.estadoNuevo = estadoNuevo; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}

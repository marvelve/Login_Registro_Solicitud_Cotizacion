/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import com.interivalle.Modelo.enums.TipoItemCotizacion;
import java.math.BigDecimal;
/**
 *
 * @author mary_
 */
public class CotizacionDetalleResponse {

    private Integer idDetalle;
    private Integer servicioId;
    private String nombreServicio;

    private TipoItemCotizacion tipoItem;
    private String categoria;
    private Integer semana;
    private String descripcion;
    private String actividadMaterial;

    private BigDecimal cantidad;
    private String unidad;
    private BigDecimal precioUnitarioVenta;
    private BigDecimal subtotalVenta;

    private BigDecimal precioUnitarioProveedor;
    private BigDecimal subtotalProveedor;

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public TipoItemCotizacion getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItemCotizacion tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitarioVenta() {
        return precioUnitarioVenta;
    }

    public void setPrecioUnitarioVenta(BigDecimal precioUnitarioVenta) {
        this.precioUnitarioVenta = precioUnitarioVenta;
    }

    public BigDecimal getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(BigDecimal subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }

    public BigDecimal getPrecioUnitarioProveedor() {
        return precioUnitarioProveedor;
    }

    public void setPrecioUnitarioProveedor(BigDecimal precioUnitarioProveedor) {
        this.precioUnitarioProveedor = precioUnitarioProveedor;
    }

    public BigDecimal getSubtotalProveedor() {
        return subtotalProveedor;
    }

    public void setSubtotalProveedor(BigDecimal subtotalProveedor) {
        this.subtotalProveedor = subtotalProveedor;
    }

    public String getActividadMaterial() {
        return actividadMaterial;
    }

    public void setActividadMaterial(String actividadMaterial) {
        this.actividadMaterial = actividadMaterial;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import com.interivalle.Modelo.enums.TipoCotizacion;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author mary_
 */

public class CrearCotizacionRequest {

    @NotNull
    private Integer solicitudId;

    @NotNull
    private TipoCotizacion tipo;

    @NotNull
    private List<DetalleItem> detalles;

    public Integer getSolicitudId() { return solicitudId; }
    public void setSolicitudId(Integer solicitudId) { this.solicitudId = solicitudId; }

    public TipoCotizacion getTipo() { return tipo; }
    public void setTipo(TipoCotizacion tipo) { this.tipo = tipo; }

    public List<DetalleItem> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleItem> detalles) { this.detalles = detalles; }

    public static class DetalleItem {

        @NotNull
        private Integer servicioId;

        @NotNull
        private TipoItemCotizacion tipoItem;

        private String categoria;
        private String semana;
        private String descripcion;

        @NotNull
        private BigDecimal cantidad;

        @NotNull
        private BigDecimal precioUnitario;

        public Integer getServicioId() { return servicioId; }
        public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

        public TipoItemCotizacion getTipoItem() { return tipoItem; }
        public void setTipoItem(TipoItemCotizacion tipoItem) { this.tipoItem = tipoItem; }

        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }

        public String getSemana() { return semana; }
        public void setSemana(String semana) { this.semana = semana; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public BigDecimal getCantidad() { return cantidad; }
        public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    }
}

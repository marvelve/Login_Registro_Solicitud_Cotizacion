/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.interivalle.Modelo.enums.TipoItemCotizacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
/**
 *
 * @author mary_
 */

@Entity
@Table(name = "cotizacion_detalle")
public class CotizacionDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private Cotizacion cotizacion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicios servicio;

    // MANO_OBRA / MATERIAL / PRODUCTO
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item", nullable = false)
    private TipoItemCotizacion tipoItem;

    // Ej: "PANEL YESO", "CARPINTERIA", "DIVISIONES EN VIDRIO", "MESON GRANITO"
    @Column(name = "categoria", length = 100)
    private String categoria;

    // Solo aplica a Obra Blanca si quieres (SEMANA 1, SEMANA 2...)
    @Column(name = "semana", length = 50)
    private String semana;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // Cantidad decimal (en vidrio y mesón hay 1.20, 1.50, etc.)
    @Column(name = "cantidad", nullable = false, precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    protected void onCreate() {
        if (cantidad == null) cantidad = BigDecimal.ZERO;
        if (precioUnitario == null) precioUnitario = BigDecimal.ZERO;
        if (subtotal == null) subtotal = BigDecimal.ZERO;
    }

    // -------- Getters / Setters --------

    public Integer getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Integer idDetalle) { this.idDetalle = idDetalle; }

    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }

    public Servicios getServicio() { return servicio; }
    public void setServicio(Servicios servicio) { this.servicio = servicio; }

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

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}

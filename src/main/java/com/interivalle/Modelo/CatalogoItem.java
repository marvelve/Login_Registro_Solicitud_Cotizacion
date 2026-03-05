/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import com.interivalle.Modelo.enums.TipoItemCotizacion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author mary_
 */


@Entity
@Table(name = "catalogo_item")
public class CatalogoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catalogo_item")
    private Integer idCatalogoItem;

    // Relación con servicios (Obra Blanca, Carpintería, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio")
    private Servicios servicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item", nullable = false)
    private TipoItemCotizacion tipoItem;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "nombre_item")
    private String nombreItem;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "modo_precio")
    private String modoPrecio; // FIJO | FORMULA | EXTERNO

    @Column(name = "precio_unitario_venta")
    private BigDecimal precioUnitarioVenta;

    @Column(name = "precio_unitario_proveedor")
    private BigDecimal precioUnitarioProveedor;

    @Column(name = "precio_subtotal_venta")
    private BigDecimal precioSubtotalVenta;

    @Column(name = "formula_code")
    private String formulaCode;

    @Column(name = "params_json", columnDefinition = "TEXT")
    private String paramsJson;

    @Column(name = "vigente_desde")
    private LocalDate vigenteDesde;

    @Column(name = "vigente_hasta")
    private LocalDate vigenteHasta;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "creado_por")
    private Integer creadoPor;
    
    @Column(name = "semana", nullable = false)
    private Integer semana;

    // ================= GETTERS & SETTERS =================

    public Integer getIdCatalogoItem() {
        return idCatalogoItem;
    }

    public void setIdCatalogoItem(Integer idCatalogoItem) {
        this.idCatalogoItem = idCatalogoItem;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
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

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getModoPrecio() {
        return modoPrecio;
    }

    public void setModoPrecio(String modoPrecio) {
        this.modoPrecio = modoPrecio;
    }

    public BigDecimal getPrecioUnitarioVenta() {
        return precioUnitarioVenta;
    }

    public void setPrecioUnitarioVenta(BigDecimal precioUnitarioVenta) {
        this.precioUnitarioVenta = precioUnitarioVenta;
    }

    public BigDecimal getPrecioUnitarioProveedor() {
        return precioUnitarioProveedor;
    }

    public void setPrecioUnitarioProveedor(BigDecimal precioUnitarioProveedor) {
        this.precioUnitarioProveedor = precioUnitarioProveedor;
    }

    public BigDecimal getPrecioSubtotalVenta() {
        return precioSubtotalVenta;
    }

    public void setPrecioSubtotalVenta(BigDecimal precioSubtotalVenta) {
        this.precioSubtotalVenta = precioSubtotalVenta;
    }

    public String getFormulaCode() {
        return formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    public LocalDate getVigenteDesde() {
        return vigenteDesde;
    }

    public void setVigenteDesde(LocalDate vigenteDesde) {
        this.vigenteDesde = vigenteDesde;
    }

    public LocalDate getVigenteHasta() {
        return vigenteHasta;
    }

    public void setVigenteHasta(LocalDate vigenteHasta) {
        this.vigenteHasta = vigenteHasta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Integer creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }
    
    
}

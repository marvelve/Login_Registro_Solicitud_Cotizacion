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

    // Relación con servicio: Obra Blanca, Carpintería, Vidrio, Mesón, etc.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio")
    private Servicios servicio;

    // ACTIVIDAD | MATERIAL | PRODUCTO
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item", nullable = false, length = 30)
    private TipoItemCotizacion tipoItem;

    // Código interno único para evitar depender del nombre visible
    // Ejemplo: ACT_DETALLE_PINTURA, MAT_SUPERBOARD_6MM
    @Column(name = "codigo", nullable = false, unique = true, length = 100)
    private String codigo;

    // Grupo o familia visual
    // Ejemplo: MANO DE OBRA, MATERIALES, ELECTRICOS
    @Column(name = "categoria", length = 100)
    private String categoria;

    // Nombre visible
    @Column(name = "nombre_item", nullable = false, length = 150)
    private String nombreItem;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // unidad: M2, UND, ML, GLOBAL, KIT, etc.
    @Column(name = "unidad", length = 50)
    private String unidad;

    // FIJO | FORMULA | EXTERNO
    @Column(name = "modo_precio", length = 30)
    private String modoPrecio;

    // Precio de venta unitario vigente
    @Column(name = "precio_unitario_venta", precision = 12, scale = 2)
    private BigDecimal precioUnitarioVenta;

    // Precio de proveedor unitario vigente
    @Column(name = "precio_unitario_proveedor", precision = 12, scale = 2)
    private BigDecimal precioUnitarioProveedor;

    // Código de fórmula para cálculo genérico desde backend
    // Ejemplo: FIJO, AREA_PRIVADA_X_FACTOR_X_PRECIO, CANTIDAD_BANOS_X_PRECIO
    @Column(name = "formula_code", length = 80)
    private String formulaCode;

    // Parámetros flexibles para fórmulas
    // Ejemplo: {"factor":3,"campoEntrada":"medidaAreaPrivada"}
    @Column(name = "params_json", columnDefinition = "TEXT")
    private String paramsJson;

    // Factor principal para simplificar fórmulas frecuentes
    @Column(name = "factor", precision = 12, scale = 4)
    private BigDecimal factor;

    // Orden para mostrar en tablas o generar cotización
    @Column(name = "orden")
    private Integer orden;

    // Semana sugerida para actividades o materiales
    @Column(name = "semana")
    private Integer semana;

    // Vigencia del precio/configuración
    @Column(name = "vigente_desde")
    private LocalDate vigenteDesde;

    @Column(name = "vigente_hasta")
    private LocalDate vigenteHasta;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "creado_por")
    private Integer creadoPor;

    // ===================== GETTERS & SETTERS =====================

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
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
}
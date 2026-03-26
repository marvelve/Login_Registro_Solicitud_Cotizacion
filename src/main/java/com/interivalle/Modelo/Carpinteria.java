/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 *
 * @author mary_
 */

@Entity
@Table(name = "carpinteria")
public class Carpinteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carpinteria")
    private Integer idCarpinteria;

    @ManyToOne
    @JoinColumn(name = "id_cotizacion_personalizada")
    private CotizacionPersonalizada cotizacionPersonalizada;

    @Column(name = "tipo_mueble", length = 100)
    private String tipoMueble;

    @Column(name = "material", length = 100)
    private String material;

    @Column(name = "largo", precision = 12, scale = 2)
    private BigDecimal largo;

    @Column(name = "ancho", precision = 12, scale = 2)
    private BigDecimal ancho;

    @Column(name = "alto", precision = 12, scale = 2)
    private BigDecimal alto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    public Carpinteria() {
    }

    public Integer getIdCarpinteria() {
        return idCarpinteria;
    }

    public void setIdCarpinteria(Integer idCarpinteria) {
        this.idCarpinteria = idCarpinteria;
    }

    public CotizacionPersonalizada getCotizacionPersonalizada() {
        return cotizacionPersonalizada;
    }

    public void setCotizacionPersonalizada(CotizacionPersonalizada cotizacionPersonalizada) {
        this.cotizacionPersonalizada = cotizacionPersonalizada;
    }

    public String getTipoMueble() {
        return tipoMueble;
    }

    public void setTipoMueble(String tipoMueble) {
        this.tipoMueble = tipoMueble;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getAlto() {
        return alto;
    }

    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

package com.interivalle.DTO;

import java.math.BigDecimal;
import java.util.List;

public class CotizacionVistaCompletaResponse {

    private Integer idCotizacion;
    private String nombreProyecto;
    private String estado;

    private BigDecimal totalManoObra;
    private BigDecimal totalMateriales;
    private BigDecimal totalProductos;
    private BigDecimal totalEstimadoBase;

    private BigDecimal totalAdicionales;
    private BigDecimal totalGeneral;

    private List<CotizacionDetalleResponse> detalleBase;
    private CotizacionPersonalizadaDetalleResponse personalizada;

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotalManoObra() {
        return totalManoObra;
    }

    public void setTotalManoObra(BigDecimal totalManoObra) {
        this.totalManoObra = totalManoObra;
    }

    public BigDecimal getTotalMateriales() {
        return totalMateriales;
    }

    public void setTotalMateriales(BigDecimal totalMateriales) {
        this.totalMateriales = totalMateriales;
    }

    public BigDecimal getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(BigDecimal totalProductos) {
        this.totalProductos = totalProductos;
    }

    public BigDecimal getTotalEstimadoBase() {
        return totalEstimadoBase;
    }

    public void setTotalEstimadoBase(BigDecimal totalEstimadoBase) {
        this.totalEstimadoBase = totalEstimadoBase;
    }

    public BigDecimal getTotalAdicionales() {
        return totalAdicionales;
    }

    public void setTotalAdicionales(BigDecimal totalAdicionales) {
        this.totalAdicionales = totalAdicionales;
    }

    public BigDecimal getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(BigDecimal totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    public List<CotizacionDetalleResponse> getDetalleBase() {
        return detalleBase;
    }

    public void setDetalleBase(List<CotizacionDetalleResponse> detalleBase) {
        this.detalleBase = detalleBase;
    }

    public CotizacionPersonalizadaDetalleResponse getPersonalizada() {
        return personalizada;
    }

    public void setPersonalizada(CotizacionPersonalizadaDetalleResponse personalizada) {
        this.personalizada = personalizada;
    }
}
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
@Table(name = "actividad_material")
public class ActividadMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad_material")
    private Integer idActividadMaterial;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_catalogo_id", nullable = false)
    private CatalogoItem actividad;   // tipo_item = ACTIVIDAD

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "material_catalogo_id", nullable = false)
    private CatalogoItem material;    // tipo_item = MATERIAL

    @Column(name = "semana", nullable = false)
    private Integer semana;

    @Column(name = "cantidad", nullable = false, precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Integer getIdActividadMaterial() { return idActividadMaterial; }
    public void setIdActividadMaterial(Integer idActividadMaterial) { this.idActividadMaterial = idActividadMaterial; }

    public CatalogoItem getActividad() { return actividad; }
    public void setActividad(CatalogoItem actividad) { this.actividad = actividad; }

    public CatalogoItem getMaterial() { return material; }
    public void setMaterial(CatalogoItem material) { this.material = material; }

    public Integer getSemana() { return semana; }
    public void setSemana(Integer semana) { this.semana = semana; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}

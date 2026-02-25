/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo.enums;

/**
 *
 * @author mary_
 * MANO_OBRA -> Actividades de construcción
 * MATERIAL -> Insumos de obra blanca
 * PRODUCTO -> Carpintería, vidrio, granito, etc.
 */
public enum TipoItemCotizacion {

    MANO_OBRA,
    MATERIAL,
    PRODUCTO;

    public static TipoItemCotizacion fromString(String value) {
        for (TipoItemCotizacion tipo : TipoItemCotizacion.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoItemCotizacion inválido: " + value);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo.enums;

/**
 *
 * @author mary_
 */

/**
 * BASE -> Cotización inicial generada por el cliente
 * PERSONALIZADA -> Cotización ajustada o modificada
 */
public enum TipoCotizacion {

    BASE,
    PERSONALIZADA;

    /**
     * Convierte texto a enum de forma segura
     */
    public static TipoCotizacion fromString(String value) {
        for (TipoCotizacion tipo : TipoCotizacion.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoCotizacion inválido: " + value);
    }
}
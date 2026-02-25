/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo.enums;

/**
 *
 * @author mary_
 * Tipo de observación en la cotización
 */
public enum TipoObservacion {

    APROBACION,
    RECHAZO,
    COMENTARIO;

    public static TipoObservacion fromString(String value) {
        for (TipoObservacion tipo : TipoObservacion.values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoObservacion inválido: " + value);
    }
}

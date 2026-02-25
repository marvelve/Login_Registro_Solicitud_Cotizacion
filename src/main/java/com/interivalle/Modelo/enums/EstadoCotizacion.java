/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Modelo.enums;

/**
 *
 * @author mary_
 * Estados posibles de una cotización
 */
public enum EstadoCotizacion {

    BORRADOR,
    ENVIADA,
    APROBADA,
    RECHAZADA,
    EN_REVISION;

    /**
     * Validar transiciones de estado permitidas
     */
    public boolean puedeCambiarA(EstadoCotizacion nuevoEstado) {

        if (this == BORRADOR && nuevoEstado == ENVIADA) return true;

        if (this == ENVIADA &&
                (nuevoEstado == APROBADA ||
                 nuevoEstado == RECHAZADA ||
                 nuevoEstado == EN_REVISION)) return true;

        if (this == EN_REVISION &&
                (nuevoEstado == APROBADA ||
                 nuevoEstado == RECHAZADA)) return true;

        return false;
    }

    /**
     * Conversión segura desde String
     */
    public static EstadoCotizacion fromString(String value) {
        for (EstadoCotizacion estado : EstadoCotizacion.values()) {
            if (estado.name().equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("EstadoCotizacion inválido: " + value);
    }
}

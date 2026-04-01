/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.DTO;

import java.time.LocalDate;
/**
 *
 * @author mary_
 */

public class SemanaCronogramaDTO {

    private Integer numero;
    private LocalDate inicio;
    private LocalDate fin;

    public SemanaCronogramaDTO() {
    }

    public SemanaCronogramaDTO(Integer numero, LocalDate inicio, LocalDate fin) {
        this.numero = numero;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
}

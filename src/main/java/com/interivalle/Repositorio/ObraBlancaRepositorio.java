/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.ObraBlanca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mary_
 */
public interface ObraBlancaRepositorio extends JpaRepository<ObraBlanca, Integer> {
    List<ObraBlanca> findByCotizacionIdCotizacion(Integer idCotizacion);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.CotizacionVidrio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface CotizacionVidrioRepositorio extends JpaRepository<CotizacionVidrio, Integer> {
    Optional<CotizacionVidrio> findByCotizacionIdCotizacion(Integer idCotizacion);
}

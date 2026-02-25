/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.CotizacionObservacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface CotizacionObservacionRepositorio extends JpaRepository<CotizacionObservacion, Integer> {

    // Observaciones de una cotización (ordenadas)
    List<CotizacionObservacion> findByCotizacion_IdCotizacionOrderByFechaAsc(Integer idCotizacion);

    List<CotizacionObservacion> findByCotizacion_IdCotizacionOrderByFechaDesc(Integer idCotizacion);
}

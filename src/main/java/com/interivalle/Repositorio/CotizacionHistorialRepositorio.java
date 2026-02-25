/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.CotizacionHistorialEstado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */


public interface CotizacionHistorialRepositorio extends JpaRepository<CotizacionHistorialEstado, Integer> {

    // Historial de cambios de estado (ordenado)
    List<CotizacionHistorialEstado> findByCotizacion_IdCotizacionOrderByFechaAsc(Integer idCotizacion);

    List<CotizacionHistorialEstado> findByCotizacion_IdCotizacionOrderByFechaDesc(Integer idCotizacion);
}

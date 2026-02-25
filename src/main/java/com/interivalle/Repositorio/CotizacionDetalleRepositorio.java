/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.CotizacionDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface CotizacionDetalleRepositorio extends JpaRepository<CotizacionDetalle, Integer> {

    // Traer detalles por cotización
    List<CotizacionDetalle> findByCotizacion_IdCotizacion(Integer idCotizacion);

    // Útil si quieres ordenar desde BD (opcional)
    List<CotizacionDetalle> findByCotizacion_IdCotizacionOrderByServicio_NombreServicioAsc(Integer idCotizacion);
}

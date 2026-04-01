/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.ObraBlanca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mary_
 */
public interface ObraBlancaRepositorio extends JpaRepository<ObraBlanca, Integer> {
    
    // Consultar actividades adicionales por cabecera personalizada
    List<ObraBlanca> findByCotizacionPersonalizada_IdCotizacionPersonalizada(Integer idCotizacionPersonalizada);

    // Consultar actividades adicionales por cotización base, pasando por la cabecera personalizada
    List<ObraBlanca> findByCotizacionPersonalizada_Cotizacion_IdCotizacion(Integer idCotizacion);

    // Validar si ya existe la misma actividad en el mismo lugar para la misma cotización base
    boolean existsByCotizacionPersonalizada_Cotizacion_IdCotizacionAndIdActividadAndLugarIgnoreCase(
            Integer idCotizacion,
            Integer idActividad,
            String lugar
    );

    // Buscar exactamente esa actividad en ese lugar dentro de la cotización
    Optional<ObraBlanca> findByCotizacionPersonalizada_Cotizacion_IdCotizacionAndIdActividadAndLugarIgnoreCase(
            Integer idCotizacion,
            Integer idActividad,
            String lugar
    );

    // Listar actividades por cotización base ordenadas
    List<ObraBlanca> findByCotizacionPersonalizada_Cotizacion_IdCotizacionOrderByIdObraBlancaAsc(Integer idCotizacion);
    
        boolean existsByCotizacionPersonalizada_Cotizacion_IdCotizacionAndIdActividadAndLugarNormalizado(
            Integer idCotizacion,
            Integer idActividad,
            String lugarNormalizado
        );

        Optional<ObraBlanca> findByCotizacionPersonalizada_Cotizacion_IdCotizacionAndIdActividadAndLugarNormalizado(
                Integer idCotizacion,
                Integer idActividad,
                String lugarNormalizado
        );

}

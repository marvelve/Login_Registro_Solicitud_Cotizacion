/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;
import com.interivalle.Modelo.CotizacionPersonalizada;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface CotizacionPersonalizadaRepositorio extends JpaRepository<CotizacionPersonalizada, Integer> {
   // Consultar cotizaciones personalizadas por usuario
    List<CotizacionPersonalizada> findByUsuario_IdUsuario(Integer idUsuario);

    // Consultar cabecera personalizada a partir de la cotización base
    Optional<CotizacionPersonalizada> findByCotizacion_IdCotizacion(Integer idCotizacion);

    // Consultar una cabecera personalizada por su id propio
    Optional<CotizacionPersonalizada> findByIdCotizacionPersonalizada(Integer id);

    Optional<CotizacionPersonalizada> findTopByCotizacion_IdCotizacionOrderByIdCotizacionPersonalizadaDesc(Integer idCotizacion);
    
    
}
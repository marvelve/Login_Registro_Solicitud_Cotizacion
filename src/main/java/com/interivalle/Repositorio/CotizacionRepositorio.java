/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.TipoCotizacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface CotizacionRepositorio extends JpaRepository<Cotizacion, Integer> {

    // Listar cotizaciones de un cliente (por usuario dueño de la solicitud)
    List<Cotizacion> findBySolicitud_Usuario_IdUsuario(Integer idUsuario);

    // Filtros opcionales
    List<Cotizacion> findBySolicitud_Usuario_IdUsuarioAndEstado(Integer idUsuario, EstadoCotizacion estado);

    List<Cotizacion> findBySolicitud_Usuario_IdUsuarioAndTipo(Integer idUsuario, TipoCotizacion tipo);

    List<Cotizacion> findBySolicitud_Usuario_IdUsuarioAndEstadoAndTipo(
            Integer idUsuario,
            EstadoCotizacion estado,
            TipoCotizacion tipo
    );
    
    Optional<Cotizacion> findByIdCotizacionAndSolicitud_Usuario_IdUsuario(
            Integer idCotizacion,
            Integer idUsuario
    );
}

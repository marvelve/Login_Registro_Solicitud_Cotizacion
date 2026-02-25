/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.SolicitudServicios;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author mary_
 */

public interface SolicitudServiciosRepositorio extends JpaRepository<SolicitudServicios, Integer> {

    List<SolicitudServicios> findBySolicitud_IdSolicitud(Integer idSolicitud);

    @Query("""
        SELECT COUNT(ss) 
        FROM SolicitudServicios ss
        WHERE ss.solicitud.usuario.correoUsuario = :correo
          AND ss.solicitud.nombreProyectoUsuario = :proyecto
          AND ss.solicitud.tipoSolicitud = :tipo
          AND ss.servicios.idServicios = :idServicios
    """)
    long existeServicioEnProyecto(
        @Param("correo") String correo,
        @Param("proyecto") String proyecto,
        @Param("tipo") String tipo,
        @Param("idServicios") Integer idServicios
    );

    Optional<SolicitudServicios> findByIdSolicitudServicio(Integer idSolicitudServicio);
}


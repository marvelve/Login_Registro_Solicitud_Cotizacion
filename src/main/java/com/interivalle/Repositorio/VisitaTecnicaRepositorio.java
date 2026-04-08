/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.VisitaTecnica;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mary_
 */

@Repository
public interface VisitaTecnicaRepositorio extends JpaRepository<VisitaTecnica, Integer> {

    Optional<VisitaTecnica> findBySolicitud_IdSolicitud(Integer idSolicitud);

    List<VisitaTecnica> findByEstadoVisita(String estadoVisita);
    
    boolean existsBySolicitud_Usuario_CorreoUsuarioAndSolicitud_NombreProyectoUsuarioAndFechaVisitaAndHoraVisita(
            String correoUsuario,
            String nombreProyectoUsuario,
            LocalDate fechaVisita,
            LocalTime horaVisita
    );
    
    boolean existsBySolicitud_Usuario_CorreoUsuarioAndFechaVisitaAndHoraVisita(
        String correoUsuario,
        LocalDate fechaVisita,
        LocalTime horaVisita
    );
    
    Optional<VisitaTecnica> findBySolicitud_Usuario_CorreoUsuarioAndSolicitud_NombreProyectoUsuario(
        String correoUsuario,
        String nombreProyectoUsuario
    );

}
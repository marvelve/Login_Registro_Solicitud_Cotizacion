/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.Notificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */


public interface NotificacionRepositorio extends JpaRepository<Notificacion, Integer> {

    List<Notificacion> findByUsuarioDestino_IdUsuarioOrderByFechaCreacionDesc(Integer idUsuario);

    List<Notificacion> findByUsuarioDestino_IdUsuarioAndLeidaFalseOrderByFechaCreacionDesc(Integer idUsuario);

    long countByUsuarioDestino_IdUsuarioAndLeidaFalse(Integer idUsuario);
}
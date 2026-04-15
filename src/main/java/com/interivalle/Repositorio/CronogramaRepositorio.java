/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.Cronograma;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author mary_
 */


@Repository
public interface CronogramaRepositorio extends JpaRepository<Cronograma, Integer> {
    Optional<Cronograma> findByCotizacion_IdCotizacion(Integer idCotizacion);

    List<Cronograma> findAllByOrderByIdCronogramaDesc();

    List<Cronograma> findByCotizacion_Solicitud_Usuario_IdUsuarioOrderByIdCronogramaDesc(Integer idUsuario);
}

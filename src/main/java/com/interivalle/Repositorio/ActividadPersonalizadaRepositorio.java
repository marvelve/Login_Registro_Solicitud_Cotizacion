/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.ActividadPersonalizada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author mary_
 */

@Repository
public interface ActividadPersonalizadaRepositorio extends JpaRepository<ActividadPersonalizada, Integer> {

    List<ActividadPersonalizada> findByEstadoTrue();

    List<ActividadPersonalizada> findByServicios_IdServiciosAndEstadoTrue(Integer idServicios);
}

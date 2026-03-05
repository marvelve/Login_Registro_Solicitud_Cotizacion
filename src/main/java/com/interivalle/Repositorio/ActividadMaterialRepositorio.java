/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.ActividadMaterial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */
public interface ActividadMaterialRepositorio extends JpaRepository<ActividadMaterial, Integer> {

    //List<ActividadMaterial> findByActividad_IdCatalogoItemAndSemanaAndActivoTrue(Integer actividadId, String semana);
    //List<ActividadMaterial> findByActividad_IdCatalogoItemAndActivoTrue(Integer actividadId);
    
    List<ActividadMaterial> findByActividad_IdCatalogoItemAndActivoTrue(Integer idActividad);

    List<ActividadMaterial> findByActividad_IdCatalogoItemAndSemanaAndActivoTrue(
            Integer idActividad,
            Integer semana
    );
}

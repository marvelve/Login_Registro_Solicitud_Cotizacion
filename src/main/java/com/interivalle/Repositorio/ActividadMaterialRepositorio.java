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

    // Todos los materiales activos asociados a una actividad
    List<ActividadMaterial> findByActividad_IdCatalogoItemAndActivoTrue(Integer idActividad);

    // Todos los materiales activos asociados a una actividad y semana
    List<ActividadMaterial> findByActividad_IdCatalogoItemAndSemanaAndActivoTrue(
            Integer idActividad,
            Integer semana
    );

    // Todas las relaciones activas de una semana
    List<ActividadMaterial> findBySemanaAndActivoTrue(Integer semana);

    // Todas las relaciones activas de un material
    List<ActividadMaterial> findByMaterial_IdCatalogoItemAndActivoTrue(Integer idMaterial);

    // Todas las relaciones activas de una actividad y un material
    List<ActividadMaterial> findByActividad_IdCatalogoItemAndMaterial_IdCatalogoItemAndActivoTrue(
            Integer idActividad,
            Integer idMaterial
    );
}

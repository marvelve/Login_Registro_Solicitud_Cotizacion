/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.CatalogoItem;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface CatalogoItemRepositorio extends JpaRepository<CatalogoItem, Integer> {

    List<CatalogoItem> findByServicio_IdServiciosAndTipoItemAndActivoTrue(
            Integer idServicio,
            TipoItemCotizacion tipoItem
    );

    List<CatalogoItem> findByTipoItemAndActivoTrue(TipoItemCotizacion tipoItem);

    // Items vigentes hoy
    List<CatalogoItem> findByActivoTrueAndVigenteDesdeLessThanEqualAndVigenteHastaIsNull(
            LocalDate fecha
    );
}

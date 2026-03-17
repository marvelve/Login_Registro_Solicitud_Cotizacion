/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.CatalogoItem;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mary_
 */

public interface CatalogoItemRepositorio extends JpaRepository<CatalogoItem, Integer> {

    List<CatalogoItem> findByServicio_IdServiciosAndActivoTrue(Integer idServicio);

    List<CatalogoItem> findByServicio_IdServiciosAndTipoItemAndActivoTrue(
            Integer idServicio,
            TipoItemCotizacion tipoItem
    );

    List<CatalogoItem> findByTipoItemAndActivoTrue(TipoItemCotizacion tipoItem);

    Optional<CatalogoItem> findByCodigoAndActivoTrue(String codigo);

    @Query("""
        SELECT c
        FROM CatalogoItem c
        WHERE c.activo = true
          AND c.vigenteDesde <= :fecha
          AND (c.vigenteHasta IS NULL OR c.vigenteHasta >= :fecha)
    """)
    List<CatalogoItem> buscarVigentes(@Param("fecha") LocalDate fecha);

    @Query("""
        SELECT c
        FROM CatalogoItem c
        WHERE c.servicio.idServicios = :idServicio
          AND c.tipoItem = :tipoItem
          AND c.activo = true
          AND c.vigenteDesde <= :fecha
          AND (c.vigenteHasta IS NULL OR c.vigenteHasta >= :fecha)
        ORDER BY c.orden ASC, c.nombreItem ASC
    """)
    List<CatalogoItem> buscarVigentesPorServicioYTipo(
            @Param("idServicio") Integer idServicio,
            @Param("tipoItem") TipoItemCotizacion tipoItem,
            @Param("fecha") LocalDate fecha
    );

    @Query("""
        SELECT c
        FROM CatalogoItem c
        WHERE c.codigo = :codigo
          AND c.activo = true
          AND c.vigenteDesde <= :fecha
          AND (c.vigenteHasta IS NULL OR c.vigenteHasta >= :fecha)
    """)
    Optional<CatalogoItem> buscarVigentePorCodigo(
            @Param("codigo") String codigo,
            @Param("fecha") LocalDate fecha
    );
}

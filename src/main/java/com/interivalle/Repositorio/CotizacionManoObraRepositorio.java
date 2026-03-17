/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

/**
 *
 * @author mary_
 */
import com.interivalle.Modelo.CotizacionManoObra;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionManoObraRepositorio extends JpaRepository<CotizacionManoObra, Integer> {
    Optional<CotizacionManoObra> findByCotizacionIdCotizacion(Integer idCotizacion);
}

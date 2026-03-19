/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.Carpinteria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mary_
 */
public interface CarpinteriaRepositorio extends JpaRepository<Carpinteria, Integer> {
   List<Carpinteria> findByCotizacionIdCotizacion (Integer IdCotizacion);
}

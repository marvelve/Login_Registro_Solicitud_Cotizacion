/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.ComentarioAvance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */

public interface ComentarioAvanceRepositorio extends JpaRepository<ComentarioAvance, Integer> {

    List<ComentarioAvance> findByAvanceSemanal_IdAvanceOrderByFechaComentarioAsc(Integer idAvance);
}
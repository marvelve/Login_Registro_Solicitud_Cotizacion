/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interivalle.Repositorio;

import com.interivalle.Modelo.AvanceSemanal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author mary_
 */
public interface AvanceSemanalRepositorio extends JpaRepository<AvanceSemanal, Integer> {

    List<AvanceSemanal> findByCronograma_IdCronogramaOrderByNumeroSemanaAsc(Integer idCronograma);

    Optional<AvanceSemanal> findByCronograma_IdCronogramaAndNumeroSemana(Integer idCronograma, Integer numeroSemana);

}

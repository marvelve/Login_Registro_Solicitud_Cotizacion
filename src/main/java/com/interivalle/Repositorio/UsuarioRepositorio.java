/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.interivalle.Repositorio;
/**
 *
 * @author mary_
 * Capa de persistencia (repositorio JPA)
 * Interfaz que extiende JpaRepository para acceder a la base de datos.
 * Proporciona métodos CRUD automáticos y permite definir consultas personalizadas.
 */

import com.interivalle.Modelo.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    // Método personalizado para buscar un usuario por su correo
    Optional<Usuario> findByCorreoUsuario(String correoUsuario);
    // Verifica si ya existe un usuario con ese correo
    boolean existsByCorreoUsuario(String correoUsuario);
    
    List<Usuario> findByEstadoUsuario(Boolean estadoUsuario);

}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Security;

/**
 *
 * @author mary_
 */
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.UsuarioRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario u = usuarioRepo.findByCorreoUsuario(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convertimos idRol a ROLE_...
        String role = switch (u.getIdRol()) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_SUPERVISOR";
            default -> "ROLE_CLIENTE";
        };

        return new org.springframework.security.core.userdetails.User(
                u.getCorreoUsuario(),
                u.getContrasenaUsuario(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}


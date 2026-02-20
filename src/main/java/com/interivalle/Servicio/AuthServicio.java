/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.*;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.*;
import com.interivalle.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author mary_
 */
@Service
public class AuthServicio {
    

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public void register(RegisterRequest dto) {

        if (usuarioRepo.existsByCorreoUsuario(dto.getCorreoUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario u = new Usuario();
        u.setNombreUsuario(dto.getNombreUsuario());
        u.setCorreoUsuario(dto.getCorreoUsuario());
        u.setContrasenaUsuario(passwordEncoder.encode(dto.getContrasenaUsuario()));
        u.setCelularUsuario(dto.getCelularUsuario());
        u.setCiudadUsuario(dto.getCiudadUsuario());


        // si no mandan idRol -> queda 3 (CLIENTE)
        if (dto.getIdRol() != null) {
            u.setIdRol(dto.getIdRol());
        }

        usuarioRepo.save(u);
    }

    public AuthResponse login(LoginRequest dto) {

        Usuario u = usuarioRepo.findByCorreoUsuario(dto.getCorreoUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña inválidos"));

        boolean ok = passwordEncoder.matches(dto.getContrasenaUsuario(), u.getContrasenaUsuario());
        if (!ok) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña inválidos");
        }

        String token = jwtService.generarToken(u.getCorreoUsuario(), u.getIdRol());
        return new AuthResponse(token, u.getCorreoUsuario(), u.getIdRol());
    }
}


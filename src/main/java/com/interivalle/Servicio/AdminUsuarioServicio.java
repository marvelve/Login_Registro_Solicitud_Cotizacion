/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.*;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 *
 * @author mary_
 */

@Service
public class AdminUsuarioServicio {

    private final UsuarioRepositorio usuarioRepo;
   // @Autowired
   // private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminUsuarioServicio(UsuarioRepositorio usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }
    
    public List<Usuario> listarPorEstado(Boolean estado) {
    return usuarioRepo.findByEstadoUsuario(estado);
}

        public Usuario crearUsuario(UsuarioCreateRequest dto) {

        Optional<Usuario> existente = usuarioRepo.findByCorreoUsuario(dto.getCorreoUsuario());
        if (existente.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "El correo ya está registrado"
            );
        }
       /* Rol rol = rolRepositorio.findById(dto.getIdRol())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Rol no encontrado"
            ));*/

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setCorreoUsuario(dto.getCorreoUsuario());
        usuario.setContrasenaUsuario(passwordEncoder.encode(dto.getContrasenaUsuario()));
        usuario.setCelularUsuario(dto.getCelularUsuario());
        usuario.setCiudadUsuario(dto.getCiudadUsuario());
        // si no mandan idRol -> queda 3 (CLIENTE)
        if (dto.getIdRol() == null) {
             usuario.setIdRol(3);
        } else {
            usuario.setIdRol(dto.getIdRol());
            }
       // usuario.setRol(rol);

        // si manejas estado por defecto
        usuario.setEstadoUsuario(true);

        return usuarioRepo.save(usuario);
    }
    
    
    
    

    public Usuario buscarPorId(Integer id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public Usuario actualizarDatos(Integer id, UsuarioUpdate dto) {
        Usuario u = buscarPorId(id);
        if (dto.getNombreUsuario() != null) {
        u.setNombreUsuario(dto.getNombreUsuario());
        }

        if (dto.getCelularUsuario() != null) {
        u.setCelularUsuario(dto.getCelularUsuario());
        }

        if (dto.getCiudadUsuario() != null) {
        u.setCiudadUsuario(dto.getCiudadUsuario());
        }

        return usuarioRepo.save(u);
    }

    public Usuario cambiarRol(Integer id, RolUpdate dto) {
        if (dto.getIdRol() < 1 || dto.getIdRol() > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idRol inválido (1=ADMIN,2=SUPERVISOR,3=CLIENTE)");
        }
        Usuario u = buscarPorId(id);
        u.setIdRol(dto.getIdRol());
        return usuarioRepo.save(u);
    }

    public Usuario cambiarEstado(Integer id, EstadoUpdate dto) {
        Usuario u = buscarPorId(id);
        u.setEstadoUsuario(dto.getEstadoUsuario());
        return usuarioRepo.save(u);
    }

    // Mapper simple a DTO (para no devolver contraseña)
    public UsuarioResponse toResponseDTO(Usuario u) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNombreUsuario(u.getNombreUsuario());
        dto.setCorreoUsuario(u.getCorreoUsuario());
        dto.setCelularUsuario(u.getCelularUsuario());
        dto.setCiudadUsuario(u.getCiudadUsuario());
        dto.setIdRol(u.getIdRol());
        dto.setEstadoUsuario(u.getEstadoUsuario());
        dto.setFechaRegistroUsuario(u.getFechaRegistroUsuario());
        return dto;
    }
}


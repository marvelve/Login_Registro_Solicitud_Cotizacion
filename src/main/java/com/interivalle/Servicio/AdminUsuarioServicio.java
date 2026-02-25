/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.*;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.UsuarioRepositorio;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author mary_
 */

@Service
public class AdminUsuarioServicio {

    private final UsuarioRepositorio usuarioRepo;

    public AdminUsuarioServicio(UsuarioRepositorio usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }
    
    public List<Usuario> listarPorEstado(Boolean estado) {
    return usuarioRepo.findByEstadoUsuario(estado);
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


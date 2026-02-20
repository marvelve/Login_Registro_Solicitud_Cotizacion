/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.*;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Servicio.AdminUsuarioServicio;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/admin/usuarios")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUsuarioControler {

    private final AdminUsuarioServicio adminService;

    public AdminUsuarioControler(AdminUsuarioServicio adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<UsuarioResponse> listar(@RequestParam(required = false) Boolean estado) {

    List<Usuario> usuarios;

    if (estado != null) {
        usuarios = adminService.listarPorEstado(estado);
    } else {
        usuarios = adminService.listarTodos();
    }

    return usuarios.stream()
            .map(adminService::toResponseDTO)
            .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public UsuarioResponse detalle(@PathVariable Integer id) {
        Usuario u = adminService.buscarPorId(id);
        return adminService.toResponseDTO(u);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioUpdate dto) {
        Usuario u = adminService.actualizarDatos(id, dto);
        return adminService.toResponseDTO(u);
    }

    @PutMapping("/{id}/rol")
    public UsuarioResponse cambiarRol(@PathVariable Integer id, @Valid @RequestBody RolUpdate dto) {
        Usuario u = adminService.cambiarRol(id, dto);
        return adminService.toResponseDTO(u);
    }

    @PutMapping("/{id}/estado")
    public UsuarioResponse cambiarEstado(@PathVariable Integer id, @Valid @RequestBody EstadoUpdate dto) {
        Usuario u = adminService.cambiarEstado(id, dto);
        return adminService.toResponseDTO(u);
    }
}


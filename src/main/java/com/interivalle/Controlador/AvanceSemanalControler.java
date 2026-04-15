/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.AvanceSemanalRequest;
import com.interivalle.DTO.AvanceSemanalResponse;
import com.interivalle.DTO.ComentarioAvanceRequest;
import com.interivalle.DTO.ComentarioAvanceResponse;
import com.interivalle.Modelo.AvanceSemanal;
import com.interivalle.Modelo.ComentarioAvance;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.UsuarioRepositorio;
import com.interivalle.Servicio.AvanceSemanalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/avances")
public class AvanceSemanalControler {

    @Autowired
    private AvanceSemanalService avanceService;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR')")
    @PostMapping
    public AvanceSemanalResponse registrar(@RequestBody AvanceSemanalRequest req, Authentication auth) {
        Integer idUsuario = obtenerIdUsuario(auth);
        return avanceService.registrarAvance(req, idUsuario);
    }

   @GetMapping("/cronograma/{idCronograma}")
    public List<AvanceSemanalResponse> listarPorCronograma(@PathVariable Integer idCronograma) {
        return avanceService.listarPorCronograma(idCronograma);
    }

    @PostMapping("/comentarios")
    public ComentarioAvanceResponse comentar(@RequestBody ComentarioAvanceRequest req, Authentication auth) {
        Integer idUsuario = obtenerIdUsuario(auth);
        return avanceService.comentarAvance(req, idUsuario);
    }

    @GetMapping("/{idAvance}/comentarios")
    public List<ComentarioAvanceResponse> listarComentarios(@PathVariable Integer idAvance) {
        return avanceService.listarComentarios(idAvance);
    }

        private Integer obtenerIdUsuario(Authentication auth) {
            String correo = auth.getName();

            Usuario usuario = usuarioRepositorio.findByCorreoUsuario(correo)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado con correo: " + correo
                ));

            return usuario.getIdUsuario();
        }
}

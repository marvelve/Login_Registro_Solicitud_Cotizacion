/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.CotizacionResponse;
import com.interivalle.DTO.CotizacionVistaCompletaResponse;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.UsuarioRepositorio;
import com.interivalle.Servicio.CotizacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class CotizacionControler {

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @GetMapping
    public List<CotizacionResponse> listarTodas(Authentication authentication) {
        String correo = authentication.getName();

        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Integer idRol = usuario.getIdRol();

        if (idRol != 1 && idRol != 2) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "No tiene permisos para listar todas las cotizaciones"
            );
        }

        return cotizacionService.listarTodas();
    }

    @GetMapping("/{idCotizacion}")
    public CotizacionResponse verDetalleGeneral(
            @PathVariable Integer idCotizacion,
            Authentication authentication) {

        String correo = authentication.getName();

        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Integer idRol = usuario.getIdRol();

        if (idRol != 1 && idRol != 2) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "No tiene permisos para ver esta cotización"
            );
        }

        return cotizacionService.verDetalleAdminSupervisor(idCotizacion);
    }

    @GetMapping("/{idCotizacion}/vista-completa")
    public CotizacionVistaCompletaResponse vistaCompletaGeneral(
            @PathVariable Integer idCotizacion,
            Authentication authentication) {

        String correo = authentication.getName();

        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Integer idRol = usuario.getIdRol();

        if (idRol != 1 && idRol != 2) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "No tiene permisos para ver esta cotización"
            );
        }

        return cotizacionService.obtenerVistaCompletaAdminSupervisor(idCotizacion);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.*;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Servicio.SolicitudService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudControler {

    @Autowired
    private SolicitudService service;

    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(@RequestBody CrearSolicitud dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearSolicitud(dto));
    }

    @PutMapping("/{idSolicitud}/generar")
    public ResponseEntity<SolicitudResponse> generarCotizacion(@PathVariable Integer idSolicitud) {
        return ResponseEntity.ok(service.generarCotizacion(idSolicitud));
    }

    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudResponse> obtener(@PathVariable Integer idSolicitud) {
        return ResponseEntity.ok(service.obtenerSolicitud(idSolicitud));
    }

    @GetMapping
    public List<SolicitudResponse> listarSolicitudes(
        @RequestParam(required = false) String correoUsuario) {

        if (correoUsuario != null && !correoUsuario.isBlank()) {
            return service.listarPorCorreoUsuario(correoUsuario);
        }

        return service.listarTodas();
    }
    
    @PutMapping("/{idSolicitud}/reprogramar")
    public ResponseEntity<SolicitudResponse> reprogramarVisita(
            @PathVariable Integer idSolicitud,
            @RequestBody ReprogramarVisitaRequest req) {

        return ResponseEntity.ok(service.reprogramarVisita(idSolicitud, req));
    }
}



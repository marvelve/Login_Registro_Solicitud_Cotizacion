/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.*;
import com.interivalle.Servicio.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // PASO 1: Crear solicitud (radio + checklist + proyecto)
    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(@RequestBody CrearSolicitud dto) {
        return ResponseEntity.ok(service.crearSolicitud(dto));
    }

    // Obtener solicitud para saber servicios seleccionados (pantalla 2)
    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudResponse> obtener(@PathVariable Integer idSolicitud) {
        return ResponseEntity.ok(service.obtenerSolicitud(idSolicitud));
    }

    // Finalizar solicitud
    @PatchMapping("/{idSolicitud}/enviar")
    public ResponseEntity<String> enviar(@PathVariable Integer idSolicitud) {
        service.enviarSolicitud(idSolicitud);
        return ResponseEntity.ok("Solicitud enviada");
    }
}


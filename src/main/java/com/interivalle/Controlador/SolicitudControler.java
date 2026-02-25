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

    // PASO 2: guardar formularios (1 endpoint por servicio)
    @PostMapping("/obra-blanca")
    public ResponseEntity<String> guardarObraBlanca(@RequestBody ObraBlancaDTO dto) {
        service.guardarObraBlanca(dto);
        return ResponseEntity.ok("Obra Blanca guardada");
    }

    @PostMapping("/carpinteria")
    public ResponseEntity<String> guardarCarpinteria(@RequestBody CarpinteriaDTO dto) {
        service.guardarCarpinteria(dto);
        return ResponseEntity.ok("Carpintería guardada");
    }

    @PostMapping("/vidrio")
    public ResponseEntity<String> guardarVidrio(@RequestBody VidrioDTO dto) {
        service.guardarVidrio(dto);
        return ResponseEntity.ok("Vidrio guardado");
    }

    @PostMapping("/mezon")
    public ResponseEntity<String> guardarMezon(@RequestBody MezonDTO dto) {
        service.guardarMezon(dto);
        return ResponseEntity.ok("Mesón guardado");
    }

    // Finalizar solicitud
    @PatchMapping("/{idSolicitud}/enviar")
    public ResponseEntity<String> enviar(@PathVariable Integer idSolicitud) {
        service.enviarSolicitud(idSolicitud);
        return ResponseEntity.ok("Solicitud enviada");
    }
}


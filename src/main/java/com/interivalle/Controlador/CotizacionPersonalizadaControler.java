/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.CotizacionPersonalizadaDetalleResponse;
import com.interivalle.DTO.CotizacionPersonalizadaRequest;
import com.interivalle.DTO.CotizacionPersonalizadaResponse;
import com.interivalle.Servicio.CotizacionPersonalizadaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/cotizaciones-personalizadas")
@CrossOrigin(origins = "*")
public class CotizacionPersonalizadaControler {

    @Autowired
    private CotizacionPersonalizadaService cotizacionService;

    // CREAR COTIZACION
    @PostMapping
    public CotizacionPersonalizadaResponse crear(@RequestBody CotizacionPersonalizadaRequest req) {
        return cotizacionService.crear(req);
    }

    // LISTAR TODAS
    @GetMapping
    public List<CotizacionPersonalizadaResponse> listarTodas() {
        return cotizacionService.listarTodas();
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public CotizacionPersonalizadaResponse obtenerPorId(@PathVariable Integer id) {
        return cotizacionService.obtenerPorId(id);
    }

    // LISTAR POR USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public List<CotizacionPersonalizadaResponse> listarPorUsuario(@PathVariable Integer idUsuario) {
        return cotizacionService.listarPorUsuario(idUsuario);
    }

    // CAMBIAR ESTADO
    @PutMapping("/{id}/estado")
    public CotizacionPersonalizadaResponse cambiarEstado(@PathVariable Integer id,
                                                         @RequestParam String estado) {
        return cotizacionService.cambiarEstado(id, estado);
    }

    // RECALCULAR TOTAL
    @PutMapping("/{id}/recalcular")
    public CotizacionPersonalizadaResponse recalcular(@PathVariable Integer id) {
        return cotizacionService.recalcular(id);
    }
    
    @GetMapping("/{id}/detalle")
    public CotizacionPersonalizadaDetalleResponse obtenerDetalle(@PathVariable Integer id) {
    return cotizacionService.obtenerDetalle(id);
}
}

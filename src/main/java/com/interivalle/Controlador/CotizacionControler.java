/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.CrearCotizacionRequest;
import com.interivalle.DTO.CotizacionResponse;
import com.interivalle.DTO.ObservacionRequest;
import com.interivalle.Servicio.CotizacionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 *  POST   /api/cliente/cotizaciones
 *  GET    /api/cliente/cotizaciones
 *  GET    /api/cotizaciones/{id}
 *  PUT    /api/cliente/cotizaciones/{id}/enviar
 *  PUT    /api/cliente/cotizaciones/{id}/aprobar
 *  PUT    /api/cliente/cotizaciones/{id}/rechazar
 *
 */
@RestController
@RequestMapping("/api")
public class CotizacionControler {

    @Autowired
    private CotizacionService service;

    private Integer getUserIdFromHeader(String xUserId) {
        try {
            return Integer.valueOf(xUserId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Header X-USER-ID inválido. Debe ser un número.");
        }
    }

    // -----------------------------
    // Crear cotización (cliente)
    // -----------------------------
    @PostMapping("/cliente/cotizaciones")
    public CotizacionResponse crearCotizacion(
            @RequestHeader("X-USER-ID") String xUserId,
            @Valid @RequestBody CrearCotizacionRequest req
    ) {
        Integer idUsuario = getUserIdFromHeader(xUserId);
        return service.crearCotizacion(idUsuario, req);
    }

    // -----------------------------
    // Listar cotizaciones (cliente)
    // -----------------------------
    @GetMapping("/cliente/cotizaciones")
    public List<CotizacionResponse> listarCotizaciones(
            @RequestHeader("X-USER-ID") String xUserId
    ) {
        Integer idUsuario = getUserIdFromHeader(xUserId);
        return service.listarPorCliente(idUsuario);
    }

    // -----------------------------
    // Ver cotización por ID (cliente)
    // -----------------------------
    @GetMapping("/cotizaciones/{id}")
    public CotizacionResponse verCotizacion(
            @RequestHeader("X-USER-ID") String xUserId,
            @PathVariable Integer id
    ) {
        Integer idUsuario = getUserIdFromHeader(xUserId);
        return service.verDetalle(idUsuario, id);
    }

    // -----------------------------
    // Enviar cotización
    // BORRADOR -> ENVIADA
    // -----------------------------
    @PutMapping("/cliente/cotizaciones/{id}/enviar")
    public CotizacionResponse enviarCotizacion(
            @RequestHeader("X-USER-ID") String xUserId,
            @PathVariable Integer id
    ) {
        Integer idUsuario = getUserIdFromHeader(xUserId);
        return service.enviar(idUsuario, id);
    }

    // -----------------------------
    // Aprobar cotización
    // ENVIADA/EN_REVISION -> APROBADA
    // -----------------------------
    @PutMapping("/cliente/cotizaciones/{id}/aprobar")
    public CotizacionResponse aprobarCotizacion(
            @RequestHeader("X-USER-ID") String xUserId,
            @PathVariable Integer id,
            @Valid @RequestBody ObservacionRequest req
    ) {
        Integer idUsuario = getUserIdFromHeader(xUserId);
        return service.aprobar(idUsuario, id, req);
    }

    // -----------------------------
    // Rechazar cotización
    // ENVIADA/EN_REVISION -> RECHAZADA
    // -----------------------------
    @PutMapping("/cliente/cotizaciones/{id}/rechazar")
    public CotizacionResponse rechazarCotizacion(
            @RequestHeader("X-USER-ID") String xUserId,
            @PathVariable Integer id,
            @Valid @RequestBody ObservacionRequest req
    ) {
        Integer idUsuario = getUserIdFromHeader(xUserId);
        return service.rechazar(idUsuario, id, req);
    }
}

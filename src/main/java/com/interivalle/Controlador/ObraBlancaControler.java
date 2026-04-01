/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.ObraBlancaRequest;
import com.interivalle.DTO.ObraBlancaResponse;
import com.interivalle.Servicio.ObraBlancaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/obra-blanca")
@CrossOrigin(origins = "*")
public class ObraBlancaControler {

    @Autowired
    private ObraBlancaService obraBlancaService;

    // GUARDAR
    @PostMapping
    public ObraBlancaResponse guardar(@Valid @RequestBody ObraBlancaRequest req) {
        return obraBlancaService.guardar(req);
    }

    // LISTAR POR COTIZACION BASE
    @GetMapping("/cotizacion/{idCotizacion}")
    public List<ObraBlancaResponse> listarPorCotizacion(@PathVariable Integer idCotizacion) {
        return obraBlancaService.listarPorCotizacion(idCotizacion);
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ObraBlancaResponse obtenerPorId(@PathVariable Integer id) {
        return obraBlancaService.obtenerPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ObraBlancaResponse actualizar(@PathVariable Integer id, @Valid @RequestBody ObraBlancaRequest req) {
        return obraBlancaService.actualizar(id, req);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        obraBlancaService.eliminar(id);
    }
}

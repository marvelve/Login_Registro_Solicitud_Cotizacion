/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.VidrioRequest;
import com.interivalle.Modelo.Vidrio;
import com.interivalle.Servicio.VidrioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/vidrio")
@CrossOrigin(origins = "*")
public class VidrioControler {

    @Autowired
    private VidrioService vidrioService;

    @PostMapping
    public Vidrio guardar(@RequestBody VidrioRequest req) {
        return vidrioService.guardar(req);
    }

    @GetMapping("/cotizacion/{idCotizacion}")
    public List<Vidrio> listarPorCotizacion(@PathVariable Integer idCotizacion) {
        return vidrioService.listarPorCotizacion(idCotizacion);
    }

    @GetMapping("/{id}")
    public Vidrio obtenerPorId(@PathVariable Integer id) {
        return vidrioService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Vidrio actualizar(@PathVariable Integer id, @RequestBody VidrioRequest req) {
        return vidrioService.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        vidrioService.eliminar(id);
    }
}

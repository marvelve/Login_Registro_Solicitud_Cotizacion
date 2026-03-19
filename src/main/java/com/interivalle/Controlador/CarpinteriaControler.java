/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.CarpinteriaRequest;
import com.interivalle.Modelo.Carpinteria;
import com.interivalle.Servicio.CarpinteriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/carpinteria")
@CrossOrigin(origins = "*")
public class CarpinteriaControler {

    @Autowired
    private CarpinteriaService carpinteriaService;

    @PostMapping
    public Carpinteria guardar(@RequestBody CarpinteriaRequest req) {
        return carpinteriaService.guardar(req);
    }

    @GetMapping("/cotizacion/{idCotizacion}")
    public List<Carpinteria> listarPorCotizacion(@PathVariable Integer idCotizacion) {
        return carpinteriaService.listarPorCotizacion(idCotizacion);
    }

    @GetMapping("/{id}")
    public Carpinteria obtenerPorId(@PathVariable Integer id) {
        return carpinteriaService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Carpinteria actualizar(@PathVariable Integer id, @RequestBody CarpinteriaRequest req) {
        return carpinteriaService.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        carpinteriaService.eliminar(id);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.MesonGranitoRequest;
import com.interivalle.Modelo.MesonGranito;
import com.interivalle.Servicio.MesonGranitoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/meson-granito")
@CrossOrigin(origins = "*")
public class MesonGranitoControler {


    @Autowired
    private MesonGranitoService mesonGranitoService;

    @PostMapping
    public MesonGranito guardar(@RequestBody MesonGranitoRequest req) {
        return mesonGranitoService.guardar(req);
    }

    @GetMapping("/cotizacion/{idCotizacion}")
    public List<MesonGranito> listarPorCotizacion(@PathVariable Integer idCotizacion) {
        return mesonGranitoService.listarPorCotizacion(idCotizacion);
    }

    @GetMapping("/{id}")
    public MesonGranito obtenerPorId(@PathVariable Integer id) {
        return mesonGranitoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public MesonGranito actualizar(@PathVariable Integer id, @RequestBody MesonGranitoRequest req) {
        return mesonGranitoService.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        mesonGranitoService.eliminar(id);
    }
}

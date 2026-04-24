/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.ActualizarPrecioCatalogoRequest;
import com.interivalle.DTO.CatalogoItemResponse;
import com.interivalle.Servicio.CatalogoItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/catalogo-items")
@CrossOrigin(origins = "*")
public class CatalogoItemControler {

    @Autowired
    private CatalogoItemService service;

    @GetMapping
    public List<CatalogoItemResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public CatalogoItemResponse obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}/precio")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CatalogoItemResponse actualizarPrecio(
            @PathVariable Integer id,
            @RequestBody ActualizarPrecioCatalogoRequest dto
    ) {
        return service.actualizarPrecio(id, dto);
    }
}

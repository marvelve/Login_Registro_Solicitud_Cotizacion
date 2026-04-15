/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.CronogramaListResponse;
import com.interivalle.DTO.CronogramaResponse;
import com.interivalle.DTO.CronogramaVistaResponse;
import com.interivalle.Servicio.CronogramaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/cliente/cronogramas")
@CrossOrigin(origins = "http://localhost:5173")
public class CronogramaControler {

    @Autowired
    private CronogramaService cronogramaService;
    
      @GetMapping("/cotizacion/{idCotizacion}")
    public CronogramaVistaResponse obtenerVistaPorCotizacion(@PathVariable Integer idCotizacion) {
        return cronogramaService.obtenerVistaPorCotizacion(idCotizacion);
    }

    @GetMapping
    public List<CronogramaListResponse> listarCronogramas(Authentication auth) {
        String correoUsuario = auth.getName();
        return cronogramaService.listarCronogramasPorUsuario(correoUsuario);
    }
}

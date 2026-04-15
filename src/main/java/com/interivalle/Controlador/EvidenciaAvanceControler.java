/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.EvidenciaAvanceRequest;
import com.interivalle.DTO.EvidenciaAvanceResponse;
import com.interivalle.Servicio.EvidenciaAvanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mary_
 */

@RestController
@RequestMapping("/api/evidencias")
public class EvidenciaAvanceControler {

    @Autowired
    private EvidenciaAvanceService evidenciaService;

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR')")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public EvidenciaAvanceResponse guardar(
            @RequestParam("idAvance") Integer idAvance,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam("archivo") MultipartFile archivo
    ) {
        return evidenciaService.guardarEvidencia(idAvance, descripcion, archivo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR','CLIENTE')")
    @GetMapping("/avance/{idAvance}")
    public List<EvidenciaAvanceResponse> listarPorAvance(@PathVariable Integer idAvance) {
        return evidenciaService.listarPorAvance(idAvance);
    }
}

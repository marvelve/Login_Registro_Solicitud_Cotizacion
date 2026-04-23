/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.DTO.NotificacionResponse;
import com.interivalle.Servicio.NotificacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */


@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionControler {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<NotificacionResponse> listarTodas() {
        return notificacionService.listarMisNotificaciones();
    }

    @GetMapping("/no-leidas")
    public List<NotificacionResponse> listarNoLeidas() {
        return notificacionService.listarMisNoLeidas();
    }

    @GetMapping("/contador")
    public long contarNoLeidas() {
        return notificacionService.contarMisNoLeidas();
    }

    @PutMapping("/{id}/leer")
    public void marcarComoLeida(@PathVariable Integer id) {
        notificacionService.marcarComoLeida(id);
    }

    @PutMapping("/leer-todas")
    public void marcarTodasComoLeidas() {
        notificacionService.marcarTodasComoLeidas();
    }
}
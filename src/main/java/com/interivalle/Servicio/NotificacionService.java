/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.NotificacionResponse;
import com.interivalle.Modelo.Notificacion;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Modelo.enums.ModuloNotificacion;
import com.interivalle.Modelo.enums.TipoNotificacion;
import com.interivalle.Repositorio.NotificacionRepositorio;
import com.interivalle.Repositorio.UsuarioRepositorio;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author mary_
 */

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepositorio notificacionRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    public void crearNotificacion(
            Usuario usuarioDestino,
            TipoNotificacion tipo,
            ModuloNotificacion modulo,
            String titulo,
            String mensaje,
            Integer idReferencia
    ) {
        if (usuarioDestino == null) {
            return;
        }

        Notificacion n = new Notificacion();
        n.setUsuarioDestino(usuarioDestino);
        n.setTipo(tipo);
        n.setModulo(modulo);
        n.setTitulo(titulo);
        n.setMensaje(mensaje);
        n.setIdReferencia(idReferencia);
        n.setLeida(false);
        n.setFechaCreacion(LocalDateTime.now());

        notificacionRepo.save(n);
    }

    public void crearNotificacionParaVarios(
            List<Usuario> usuariosDestino,
            TipoNotificacion tipo,
            ModuloNotificacion modulo,
            String titulo,
            String mensaje,
            Integer idReferencia
    ) {
        if (usuariosDestino == null || usuariosDestino.isEmpty()) {
            return;
        }

        List<Notificacion> lista = new ArrayList<>();

        for (Usuario usuario : usuariosDestino) {
            if (usuario == null) continue;

            Notificacion n = new Notificacion();
            n.setUsuarioDestino(usuario);
            n.setTipo(tipo);
            n.setModulo(modulo);
            n.setTitulo(titulo);
            n.setMensaje(mensaje);
            n.setIdReferencia(idReferencia);
            n.setLeida(false);
            n.setFechaCreacion(LocalDateTime.now());

            lista.add(n);
        }

        if (!lista.isEmpty()) {
            notificacionRepo.saveAll(lista);
        }
    }

    public List<NotificacionResponse> listarMisNotificaciones() {
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        List<Notificacion> lista = notificacionRepo
                .findByUsuarioDestino_IdUsuarioOrderByFechaCreacionDesc(usuarioActual.getIdUsuario());

        List<NotificacionResponse> respuesta = new ArrayList<>();

        for (Notificacion n : lista) {
            respuesta.add(toResponse(n));
        }

        return respuesta;
    }

    public List<NotificacionResponse> listarMisNoLeidas() {
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        List<Notificacion> lista = notificacionRepo
                .findByUsuarioDestino_IdUsuarioAndLeidaFalseOrderByFechaCreacionDesc(usuarioActual.getIdUsuario());

        List<NotificacionResponse> respuesta = new ArrayList<>();

        for (Notificacion n : lista) {
            respuesta.add(toResponse(n));
        }

        return respuesta;
    }

    public long contarMisNoLeidas() {
        Usuario usuarioActual = obtenerUsuarioAutenticado();
        return notificacionRepo.countByUsuarioDestino_IdUsuarioAndLeidaFalse(usuarioActual.getIdUsuario());
    }

    public void marcarComoLeida(Integer idNotificacion) {
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        Notificacion n = notificacionRepo.findById(idNotificacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificación no encontrada"));

        if (!n.getUsuarioDestino().getIdUsuario().equals(usuarioActual.getIdUsuario())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso para modificar esta notificación");
        }

        if (Boolean.FALSE.equals(n.getLeida())) {
            n.setLeida(true);
            n.setFechaLectura(LocalDateTime.now());
            notificacionRepo.save(n);
        }
    }

    public void marcarTodasComoLeidas() {
        Usuario usuarioActual = obtenerUsuarioAutenticado();

        List<Notificacion> lista = notificacionRepo
                .findByUsuarioDestino_IdUsuarioAndLeidaFalseOrderByFechaCreacionDesc(usuarioActual.getIdUsuario());

        for (Notificacion n : lista) {
            n.setLeida(true);
            n.setFechaLectura(LocalDateTime.now());
        }

        if (!lista.isEmpty()) {
            notificacionRepo.saveAll(lista);
        }
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        return usuarioRepo.findByCorreoUsuario(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private NotificacionResponse toResponse(Notificacion n) {
        NotificacionResponse r = new NotificacionResponse();
        r.setIdNotificacion(n.getIdNotificacion());
        r.setTipo(n.getTipo());
        r.setModulo(n.getModulo());
        r.setTitulo(n.getTitulo());
        r.setMensaje(n.getMensaje());
        r.setIdReferencia(n.getIdReferencia());
        r.setLeida(n.getLeida());
        r.setFechaCreacion(n.getFechaCreacion());
        r.setFechaLectura(n.getFechaLectura());
        return r;
    }
}
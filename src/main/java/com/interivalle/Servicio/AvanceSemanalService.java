/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.AvanceSemanalRequest;
import com.interivalle.DTO.AvanceSemanalResponse;
import com.interivalle.DTO.ComentarioAvanceRequest;
import com.interivalle.DTO.ComentarioAvanceResponse;
import com.interivalle.Modelo.AvanceSemanal;
import com.interivalle.Modelo.ComentarioAvance;
import com.interivalle.Modelo.Cronograma;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.AvanceSemanalRepositorio;
import com.interivalle.Repositorio.ComentarioAvanceRepositorio;
import com.interivalle.Repositorio.CronogramaRepositorio;
import com.interivalle.Repositorio.UsuarioRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mary_
 */

@Service
public class AvanceSemanalService {

    @Autowired
    private AvanceSemanalRepositorio avanceRepo;

    @Autowired
    private ComentarioAvanceRepositorio comentarioRepo;

    @Autowired
    private CronogramaRepositorio cronogramaRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

   public AvanceSemanalResponse registrarAvance(AvanceSemanalRequest req, Integer idUsuario) {
    if (req == null || req.getIdCronograma() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cronograma es obligatorio");
    }

    if (req.getNumeroSemana() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La semana es obligatoria");
    }

    Cronograma cronograma = cronogramaRepo.findById(req.getIdCronograma())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado"));

    AvanceSemanal avance = new AvanceSemanal();
    avance.setCronograma(cronograma);
    avance.setNumeroSemana(req.getNumeroSemana());
    avance.setFechaRegistro(LocalDateTime.now());
    avance.setTitulo(req.getTitulo());
    avance.setDescripcion(req.getDescripcion());
    avance.setObservaciones(req.getObservaciones());
    avance.setPorcentajeSemana(req.getPorcentajeSemana());
    avance.setPorcentajeGeneral(req.getPorcentajeGeneral());
    avance.setRegistradoPor(idUsuario);
    avance.setEstado("REGISTRADO");

    AvanceSemanal guardado = avanceRepo.save(avance);

    return mapToResponse(guardado);
    }

   public List<AvanceSemanalResponse> listarPorCronograma(Integer idCronograma) {
    return avanceRepo.findByCronograma_IdCronogramaOrderByNumeroSemanaAsc(idCronograma)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

   public ComentarioAvanceResponse comentarAvance(ComentarioAvanceRequest req, Integer idUsuario) {
    if (req == null || req.getIdAvance() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El avance es obligatorio");
    }

    if (req.getComentario() == null || req.getComentario().trim().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El comentario es obligatorio");
    }

    AvanceSemanal avance = avanceRepo.findById(req.getIdAvance())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avance no encontrado"));

    Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    ComentarioAvance comentario = new ComentarioAvance();
    comentario.setAvanceSemanal(avance);
    comentario.setUsuario(usuario);
    comentario.setComentario(req.getComentario().trim());
    comentario.setFechaComentario(LocalDateTime.now());

    ComentarioAvance guardado = comentarioRepo.save(comentario);

    return mapComentarioToResponse(guardado);
    }

   public List<ComentarioAvanceResponse> listarComentarios(Integer idAvance) {
    return comentarioRepo.findByAvanceSemanal_IdAvanceOrderByFechaComentarioAsc(idAvance)
            .stream()
            .map(this::mapComentarioToResponse)
            .toList();
}   
    
    private AvanceSemanalResponse mapToResponse(AvanceSemanal avance) {
        AvanceSemanalResponse res = new AvanceSemanalResponse();
        res.setIdAvance(avance.getIdAvance());
        res.setIdCronograma(avance.getCronograma().getIdCronograma());
        res.setNumeroSemana(avance.getNumeroSemana());
        res.setFechaRegistro(avance.getFechaRegistro());
        res.setTitulo(avance.getTitulo());
        res.setDescripcion(avance.getDescripcion());
        res.setObservaciones(avance.getObservaciones());
        res.setPorcentajeSemana(avance.getPorcentajeSemana());
        res.setPorcentajeGeneral(avance.getPorcentajeGeneral());
        res.setRegistradoPor(avance.getRegistradoPor());
        res.setEstado(avance.getEstado());
        return res;
    }
    
    private ComentarioAvanceResponse mapComentarioToResponse(ComentarioAvance comentario) {
    ComentarioAvanceResponse res = new ComentarioAvanceResponse();
    res.setIdComentario(comentario.getIdComentario());
    res.setIdAvance(comentario.getAvanceSemanal().getIdAvance());
    res.setIdUsuario(comentario.getUsuario().getIdUsuario());
    res.setNombreUsuario(comentario.getUsuario().getNombreUsuario());
    res.setComentario(comentario.getComentario());
    res.setFechaComentario(comentario.getFechaComentario());
    return res;
    }
}

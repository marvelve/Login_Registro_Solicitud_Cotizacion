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
import com.interivalle.Modelo.enums.ModuloNotificacion;
import com.interivalle.Modelo.enums.TipoNotificacion;
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
    
    @Autowired
    private NotificacionService notificacionService;

    public AvanceSemanalResponse registrarAvance(AvanceSemanalRequest req, Integer idUsuario) {
    if (req == null || req.getIdCronograma() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cronograma es obligatorio");
    }

    if (req.getNumeroSemana() == null || req.getNumeroSemana() <= 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La semana es obligatoria");
    }

    if (req.getPorcentajeSemana() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El porcentaje semanal es obligatorio");
    }

    System.out.println("REQ porcentajeSemana = " + req.getPorcentajeSemana());
    if (req.getPorcentajeSemana().compareTo(java.math.BigDecimal.ZERO) < 0
            || req.getPorcentajeSemana().compareTo(new java.math.BigDecimal("100")) > 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El porcentaje semanal debe estar entre 0 y 100");
    }

    Cronograma cronograma = cronogramaRepo.findById(req.getIdCronograma())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado"));

    if (cronograma.getTotalSemanas() == null || cronograma.getTotalSemanas() <= 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cronograma no tiene total de semanas definido");
    }

    if (req.getNumeroSemana() > cronograma.getTotalSemanas()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "La semana ingresada supera el total de semanas del cronograma");
    }

    // Buscar si ya existe avance para esa semana
    AvanceSemanal avance = avanceRepo
            .findByCronograma_IdCronogramaAndNumeroSemana(req.getIdCronograma(), req.getNumeroSemana())
            .orElse(new AvanceSemanal());

    avance.setCronograma(cronograma);
    avance.setNumeroSemana(req.getNumeroSemana());
    avance.setFechaRegistro(LocalDateTime.now());
    avance.setTitulo(req.getTitulo());
    avance.setDescripcion(req.getDescripcion());
    avance.setObservaciones(req.getObservaciones());
    avance.setPorcentajeSemana(req.getPorcentajeSemana());
    avance.setRegistradoPor(idUsuario);
    avance.setEstado(determinarEstadoSemana(req.getPorcentajeSemana()));

    // Calcular porcentaje general del proyecto
    java.math.BigDecimal sumaPorcentajes = calcularSumaPorcentajesConSemanaActual(
            cronograma.getIdCronograma(),
            req.getNumeroSemana(),
            req.getPorcentajeSemana()
    );

    java.math.BigDecimal porcentajeGeneral = sumaPorcentajes.divide(
            java.math.BigDecimal.valueOf(cronograma.getTotalSemanas()),
            2,
            java.math.RoundingMode.HALF_UP
    );

    if (porcentajeGeneral.compareTo(new java.math.BigDecimal("100")) > 0) {
        porcentajeGeneral = new java.math.BigDecimal("100.00");
    }

    avance.setPorcentajeGeneral(porcentajeGeneral);

    // Actualizar cronograma
    cronograma.setAvanceGeneral(porcentajeGeneral);
    cronograma.setEstado(determinarEstadoCronograma(porcentajeGeneral));
    cronogramaRepo.save(cronograma);

    AvanceSemanal guardado = avanceRepo.save(avance);

    // CREAR NOTIFICACIÓN AL CLIENTE
    if (cronograma.getCotizacion() != null
            && cronograma.getCotizacion().getSolicitud() != null
            && cronograma.getCotizacion().getSolicitud().getUsuario() != null) {

        Usuario cliente = cronograma.getCotizacion().getSolicitud().getUsuario();

        String nombreProyecto = cronograma.getCotizacion().getSolicitud().getNombreProyectoUsuario();
        String titulo = "Nuevo avance registrado";
        String mensaje = "Se registró un nuevo avance para el proyecto '" + nombreProyecto
                + "', correspondiente a la semana " + guardado.getNumeroSemana() + ".";

        notificacionService.crearNotificacion(
                cliente,
                TipoNotificacion.AVANCE_REGISTRADO,
                ModuloNotificacion.AVANCE,
                titulo,
                mensaje,
                guardado.getIdAvance()
        );
    }
    
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
    
    private java.math.BigDecimal calcularSumaPorcentajesConSemanaActual(
        Integer idCronograma,
        Integer numeroSemanaActual,
        java.math.BigDecimal porcentajeSemanaActual) {

    List<AvanceSemanal> avances = avanceRepo.findByCronograma_IdCronogramaOrderByNumeroSemanaAsc(idCronograma);

    java.math.BigDecimal suma = java.math.BigDecimal.ZERO;
    boolean actualizada = false;

    for (AvanceSemanal a : avances) {
        if (a.getNumeroSemana().equals(numeroSemanaActual)) {
            suma = suma.add(porcentajeSemanaActual);
            actualizada = true;
        } else {
            suma = suma.add(a.getPorcentajeSemana() != null
                    ? a.getPorcentajeSemana()
                    : java.math.BigDecimal.ZERO);
        }
    }

    if (!actualizada) {
        suma = suma.add(porcentajeSemanaActual);
    }

    return suma;
}

private String determinarEstadoSemana(java.math.BigDecimal porcentajeSemana) {
    if (porcentajeSemana == null || porcentajeSemana.compareTo(java.math.BigDecimal.ZERO) == 0) {
        return "PENDIENTE";
    }
    if (porcentajeSemana.compareTo(new java.math.BigDecimal("100")) < 0) {
        return "EN_PROCESO";
    }
    return "COMPLETADA";
}

    private String determinarEstadoCronograma(java.math.BigDecimal porcentajeGeneral) {
        if (porcentajeGeneral == null || porcentajeGeneral.compareTo(java.math.BigDecimal.ZERO) == 0) {
            return "PENDIENTE";
        }
        if (porcentajeGeneral.compareTo(new java.math.BigDecimal("100")) < 0) {
            return "EN_PROCESO";
        }
        return "FINALIZADO";
    }
}

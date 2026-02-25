/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.*;
import com.interivalle.Modelo.*;
import com.interivalle.Repositorio.*;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author mary_
 */
@Service
public class SolicitudService {

    @Autowired private UsuarioRepositorio usuarioRepo;
    @Autowired private SolicitudRepositorio solicitudRepo;
    @Autowired private ServiciosRepositorio serviciosRepo;
    @Autowired private SolicitudServiciosRepositorio solicitudServicioRepo;

    @Autowired private ObraBlancaRepositorio obraBlancaRepo;
    @Autowired private CarpinteriaRepositorio carpinteriaRepo;
    @Autowired private VidrioRepositorio vidrioRepo;
    @Autowired private MezonRepositorio mezonRepo;


    // Crear solicitud
    @Transactional
public SolicitudResponse crearSolicitud(CrearSolicitud dto) {

    if (dto.getCorreoUsuario() == null || dto.getCorreoUsuario().isBlank()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "correoUsuario es requerido");
    }
    if (dto.getNombreProyecto() == null || dto.getNombreProyecto().isBlank()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombreProyecto es requerido");
    }
    if (dto.getTipoSolicitud() == null || dto.getTipoSolicitud().isBlank()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tipoSolicitud es requerido");
    }

    // Validar servicios SOLO para cotización base
    if ("COTIZACION_BASE".equalsIgnoreCase(dto.getTipoSolicitud())) {
        if (dto.getServicios() == null || dto.getServicios().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe seleccionar al menos un servicio");
        }
    }

    Usuario usuario = usuarioRepo.findByCorreoUsuario(dto.getCorreoUsuario())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    // Crear cabecera
    Solicitud solicitud = new Solicitud();
    solicitud.setUsuario(usuario);
    solicitud.setNombreProyectoUsuario(dto.getNombreProyecto().trim());
    solicitud.setTipoSolicitud(dto.getTipoSolicitud().trim());
    solicitud.setEstado("BORRADOR");
    solicitud.setFechaSolicitud(LocalDate.now());

    solicitud = solicitudRepo.save(solicitud);

    // Crear detalles (solo en COTIZACION_BASE)
    if ("COTIZACION_BASE".equalsIgnoreCase(dto.getTipoSolicitud())) {

        for (Integer idServicios : dto.getServicios()) {

            long existe = solicitudServicioRepo.existeServicioEnProyecto(
                dto.getCorreoUsuario(),
                solicitud.getNombreProyectoUsuario(),
                solicitud.getTipoSolicitud(),
                idServicios
            );

            if (existe > 0) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una solicitud para este proyecto con el servicio ID: " + idServicios
                );
            }

            Servicios servicio = serviciosRepo.findById(idServicios)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Servicio no encontrado: " + idServicios
                ));

            SolicitudServicios ss = new SolicitudServicios();
            ss.setSolicitud(solicitud);
            ss.setServicios(servicio);
            ss.setEstado("PENDIENTE");

            solicitudServicioRepo.save(ss);
        }
    }

    // Armar respuesta con detalles (solicitudServicios)
    List<SolicitudServicios> detalles = solicitudServicioRepo.findBySolicitud_IdSolicitud(solicitud.getIdSolicitud());

    List<SolicitudServicioItem> items = detalles.stream().map(ss -> {
        SolicitudServicioItem it = new SolicitudServicioItem();
        it.setIdSolicitudServicio(ss.getIdSolicitudServicio());
        it.setIdServicio(ss.getServicios().getIdServicio()); // si tu campo en Servicios es idServicios, ajusta aquí
        it.setNombreServicio(ss.getServicios().getNombreServicio());
        it.setEstado(ss.getEstado());
        return it;
    }).collect(Collectors.toList());

    SolicitudResponse resp = new SolicitudResponse();
    resp.setIdSolicitud(solicitud.getIdSolicitud());
    resp.setTipoSolicitud(solicitud.getTipoSolicitud());
    resp.setEstado(solicitud.getEstado());
    resp.setNombreProyecto(solicitud.getNombreProyectoUsuario());
    resp.setSolicitudServicios(items);   //lo que necesita /formularios

    return resp;
}

public SolicitudResponse obtenerSolicitud(Integer idSolicitud) {

    Solicitud solicitud = solicitudRepo.findById(idSolicitud)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

    List<SolicitudServicios> detalles = solicitudServicioRepo.findBySolicitud_IdSolicitud(idSolicitud);

    List<SolicitudServicioItem> items = detalles.stream().map(ss -> {
        SolicitudServicioItem it = new SolicitudServicioItem();
        it.setIdSolicitudServicio(ss.getIdSolicitudServicio());
        it.setIdServicio(ss.getServicios().getIdServicio()); // ajusta si es idServicios
        it.setNombreServicio(ss.getServicios().getNombreServicio());
        it.setEstado(ss.getEstado());
        return it;
    }).collect(Collectors.toList());

    SolicitudResponse resp = new SolicitudResponse();
    resp.setIdSolicitud(solicitud.getIdSolicitud());
    resp.setTipoSolicitud(solicitud.getTipoSolicitud());
    resp.setEstado(solicitud.getEstado());
    resp.setNombreProyecto(solicitud.getNombreProyectoUsuario());
    resp.setSolicitudServicios(items);  

    return resp;
}

    @Transactional
    public void guardarObraBlanca(ObraBlancaDTO dto) {
        SolicitudServicios ss = solicitudServicioRepo.findById(dto.getIdSolicitudServicio())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SolicitudServicio no existe"));

        // Puedes validar que el servicio sea el correcto (opcional)
        // if (!ss.getServicio().getNombre().equalsIgnoreCase("Mano de Obra Blanca")) ...

        CotizacionObraBlanca ob = new CotizacionObraBlanca();
        ob.setSolicitudServicio(ss);
        ob.setMedidaAptoInterna(dto.getMedidaAptoInterna());
        ob.setCantidadBanos(dto.getCantidadBanos());
        ob.setDivisionPared(dto.getDivisionPared());
        ob.setTipoCielo(dto.getTipoCielo());

        obraBlancaRepo.save(ob);
        ss.setEstado("GENERADO");
        solicitudServicioRepo.save(ss);
    }

    @Transactional
    public void guardarCarpinteria(CarpinteriaDTO dto) {
        SolicitudServicios ss = solicitudServicioRepo.findById(dto.getIdSolicitudServicio())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SolicitudServicio no existe"));

        CotizacionCarpinteria c = new CotizacionCarpinteria();
        c.setSolicitudServicios(ss);
        c.setMedidaAptoInterna(dto.getMedidaAptoInterna());
        c.setCantidadCloset(dto.getCantidadCloset());
        c.setCantidadPuertas(dto.getCantidadPuertas());
        c.setCantidadBanos(dto.getCantidadBanos());

        carpinteriaRepo.save(c);
        ss.setEstado("GENERADO");
        solicitudServicioRepo.save(ss);
    }

    @Transactional
    public void guardarVidrio(VidrioDTO dto) {
        SolicitudServicios ss = solicitudServicioRepo.findById(dto.getIdSolicitudServicio())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SolicitudServicio no existe"));

        CotizacionVidrio v = new CotizacionVidrio();
        v.setSolicitudServicio(ss);
        v.setCantidadBanos(dto.getCantidadBanos());
        v.setColor(dto.getColor());
        v.setApertura(dto.getApertura());

        vidrioRepo.save(v);
        ss.setEstado("GENERADO");
        solicitudServicioRepo.save(ss);
    }

    @Transactional
    public void guardarMezon(MezonDTO dto) {
        SolicitudServicios ss = solicitudServicioRepo.findById(dto.getIdSolicitudServicio())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SolicitudServicio no existe"));

        CotizacionMezon m = new CotizacionMezon();
        m.setSolicitudServicio(ss);
        m.setMezonCocina(Boolean.TRUE.equals(dto.getMezonCocina()));
        m.setMezonBarra(Boolean.TRUE.equals(dto.getMezonBarra()));
        m.setMezonBano(Boolean.TRUE.equals(dto.getMezonBano()));

        mezonRepo.save(m);
        ss.setEstado("GENERADO");
        solicitudServicioRepo.save(ss);
    }

 
    // Finalizar / Enviar solicitud
    
    @Transactional
    public void enviarSolicitud(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepo.findById(idSolicitud)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        // Validación: todos los servicios deben estar GENERADO
        List<SolicitudServicios> detalles = solicitudServicioRepo.findBySolicitud_IdSolicitud(idSolicitud);
        boolean falta = detalles.stream().anyMatch(d -> !"GENERADO".equalsIgnoreCase(d.getEstado()));

        if (falta) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Faltan formularios por completar (al menos un servicio está PENDIENTE)"
            );
        }

        solicitud.setEstado("ENVIADA");
        solicitudRepo.save(solicitud);
    }
}


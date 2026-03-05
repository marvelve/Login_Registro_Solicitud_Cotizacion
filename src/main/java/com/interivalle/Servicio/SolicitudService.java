package com.interivalle.Servicio;

import com.interivalle.DTO.CrearSolicitud;
import com.interivalle.DTO.SolicitudResponse;
import com.interivalle.DTO.SolicitudServicioItem;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Modelo.SolicitudServicios;
import com.interivalle.Modelo.Servicios;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.SolicitudRepositorio;
import com.interivalle.Repositorio.SolicitudServiciosRepositorio;
import com.interivalle.Repositorio.ServiciosRepositorio;
import com.interivalle.Repositorio.UsuarioRepositorio;

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

        Solicitud solicitud = new Solicitud();
        solicitud.setUsuario(usuario);
        solicitud.setNombreProyectoUsuario(dto.getNombreProyecto().trim());
        solicitud.setTipoSolicitud(dto.getTipoSolicitud().trim());
        solicitud.setEstado("BORRADOR");
        solicitud.setFechaSolicitud(LocalDate.now());

        solicitud = solicitudRepo.save(solicitud);

        // Crear detalles (solo en COTIZACION_BASE)
        if ("COTIZACION_BASE".equalsIgnoreCase(dto.getTipoSolicitud())) {

            for (Integer idServicio : dto.getServicios()) {

                long existe = solicitudServicioRepo.existeServicioEnProyecto(
                    dto.getCorreoUsuario(),
                    solicitud.getNombreProyectoUsuario(),
                    solicitud.getTipoSolicitud(),
                    idServicio
                );

                if (existe > 0) {
                    throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Ya existe una solicitud para este proyecto con el servicio ID: " + idServicio
                    );
                }

                Servicios servicio = serviciosRepo.findById(idServicio)
                    .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Servicio no encontrado: " + idServicio
                    ));

                SolicitudServicios ss = new SolicitudServicios();
                ss.setSolicitud(solicitud);
                ss.setServicios(servicio);
                ss.setEstado("PENDIENTE");

                solicitudServicioRepo.save(ss);
            }
        }

        return buildResponseFromSolicitud(solicitud.getIdSolicitud());
    }

    public SolicitudResponse obtenerSolicitud(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepo.findById(idSolicitud)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        return buildResponseFromSolicitud(solicitud.getIdSolicitud());
    }

    @Transactional
    public void enviarSolicitud(Integer idSolicitud) {
        Solicitud solicitud = solicitudRepo.findById(idSolicitud)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        // OJO: si ya no guardas formularios por servicio, esta validación no tiene sentido.
        // Puedes: (a) eliminarla, (b) exigir que estén "PENDIENTE" -> "SELECCIONADO", etc.
        // Aquí la dejo como estaba (GENERO/pendiente), pero probablemente debes ajustar.
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

    private SolicitudResponse buildResponseFromSolicitud(Integer idSolicitud) {

        Solicitud solicitud = solicitudRepo.findById(idSolicitud)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        List<SolicitudServicios> detalles = solicitudServicioRepo.findBySolicitud_IdSolicitud(idSolicitud);

        List<SolicitudServicioItem> items = detalles.stream().map(ss -> {
            SolicitudServicioItem it = new SolicitudServicioItem();
            it.setIdSolicitudServicio(ss.getIdSolicitudServicio());
            it.setIdServicio(ss.getServicios().getIdServicio());
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
}
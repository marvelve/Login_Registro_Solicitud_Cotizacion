/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.CrearCronogramaRequest;
import com.interivalle.DTO.CronogramaDetalleVistaDTO;
import com.interivalle.DTO.CronogramaListResponse;
import com.interivalle.DTO.CronogramaResponse;
import com.interivalle.DTO.CronogramaVistaResponse;
import com.interivalle.DTO.SemanaCronogramaDTO;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.CotizacionDetalle;
import com.interivalle.Modelo.Cronograma;
import com.interivalle.Modelo.CronogramaDetalle;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Modelo.enums.EstadoActividadCronograma;
import com.interivalle.Modelo.enums.EstadoCronograma;
import com.interivalle.Repositorio.CotizacionRepositorio;
import com.interivalle.Repositorio.CronogramaDetalleRepositorio;
import com.interivalle.Repositorio.CronogramaRepositorio;
import com.interivalle.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author mary_
 */


@Service
public class CronogramaService {

    @Autowired
    private CronogramaRepositorio cronogramaRepo;

    @Autowired
    private CronogramaDetalleRepositorio cronogramaDetalleRepo;

    @Autowired
    private CotizacionRepositorio cotizacionRepo;
    
    @Autowired
    private UsuarioRepositorio usuarioRepo;

    public CronogramaVistaResponse obtenerVistaPorCotizacion(Integer idCotizacion) {

        Cotizacion cotizacion = cotizacionRepo.findById(idCotizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        Cronograma cronograma = cronogramaRepo.findByCotizacion_IdCotizacion(idCotizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado para la cotización"));

        List<CronogramaDetalle> detalles = cronogramaDetalleRepo.findByCronograma_IdCronogramaOrderBySemanaAsc(cronograma.getIdCronograma());

        CronogramaVistaResponse response = new CronogramaVistaResponse();
        response.setIdCronograma(cronograma.getIdCronograma());
        response.setIdCotizacion(cotizacion.getIdCotizacion());
        response.setEstadoCronograma(cronograma.getEstadoCronograma()!= null ? cronograma.getEstadoCronograma().name() : "EN_PROCESO");
        response.setFechaInicio(cronograma.getFechaInicio());
        response.setFechaFin(cronograma.getFechaFinEstimada());

        String nombreProyecto = obtenerNombreProyecto(cotizacion);
        response.setNombreProyecto(nombreProyecto);

        response.setSemanas(generarSemanas(cronograma.getFechaInicio(), detalles));
        response.setDetalles(mapearDetalles(detalles));
        response.setAvanceGeneral(calcularAvanceGeneral(detalles));

        return response;
    }

    private String obtenerNombreProyecto(Cotizacion cotizacion) {
        if (cotizacion.getSolicitud() != null) {
            Solicitud solicitud = cotizacion.getSolicitud();

            if (solicitud.getNombreProyectoUsuario() != null && !solicitud.getNombreProyectoUsuario().isBlank()) {
                return solicitud.getNombreProyectoUsuario();
            }

            if (solicitud.getNombreProyectoUsuario()!= null && !solicitud.getNombreProyectoUsuario().isBlank()) {
                return solicitud.getNombreProyectoUsuario();
            }
        }
        return "Sin nombre";
    }

    private List<SemanaCronogramaDTO> generarSemanas(LocalDate fechaInicioCronograma, List<CronogramaDetalle> detalles) {
        List<SemanaCronogramaDTO> semanas = new ArrayList<>();

        if (fechaInicioCronograma == null) {
            return semanas;
        }

        int maxSemana = detalles.stream()
                .map(CronogramaDetalle::getSemana)
                .filter(s -> s != null)
                .max(Comparator.naturalOrder())
                .orElse(0);

        if (maxSemana == 0) {
            return semanas;
        }

        LocalDate inicioPlanificado = ajustarAlLunes(fechaInicioCronograma);

        for (int i = 1; i <= maxSemana; i++) {
            LocalDate inicioSemana = inicioPlanificado.plusWeeks(i - 1);
            LocalDate finSemana = inicioSemana.plusDays(5); // lunes a sábado
            semanas.add(new SemanaCronogramaDTO(i, inicioSemana, finSemana));
        }

        return semanas;
    }

    private LocalDate ajustarAlLunes(LocalDate fecha) {
        if (fecha == null) return null;

        while (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            fecha = fecha.plusDays(1);
        }
        return fecha;
    }

    private List<CronogramaDetalleVistaDTO> mapearDetalles(List<CronogramaDetalle> detalles) {
        List<CronogramaDetalleVistaDTO> lista = new ArrayList<>();

        for (CronogramaDetalle d : detalles) {
            CronogramaDetalleVistaDTO dto = new CronogramaDetalleVistaDTO();
            dto.setIdDetalle(d.getIdCronogramaDetalle());
            dto.setActividad(d.getActividad());
            dto.setDescripcion(d.getDescripcion());
            dto.setSemana(d.getSemana());
            dto.setEstado(d.getEstadoActividad()!= null ? d.getEstadoActividad().toString() : "PENDIENTE");
            dto.setTrabajador(d.getTrabajadorAsignado());
            dto.setPorcentaje(d.getPorcentaje() != null ? d.getPorcentaje().intValue() : 0);
            dto.setNovedades(d.getNovedades());
            lista.add(dto);
        }

        return lista;
    }

    private Integer calcularAvanceGeneral(List<CronogramaDetalle> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return 0;
        }

        int suma = detalles.stream()
                .map(d -> d.getPorcentaje() != null ? d.getPorcentaje().intValue() : 0)
                .reduce(0, Integer::sum);

        return suma / detalles.size();
    }
    
    @Transactional
    public CronogramaResponse crearDesdeCotizacionAprobada(Integer idCotizacion, LocalDate fechaInicio) {

    if (idCotizacion == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cotización es obligatoria");
    }

    if (fechaInicio == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de inicio es obligatoria");
    }

    CrearCronogramaRequest req = new CrearCronogramaRequest();
    req.setIdCotizacion(idCotizacion);
    req.setFechaInicio(fechaInicio);

    return crearCronograma(req);
}
    
    @Transactional
    public CronogramaResponse crearCronograma(CrearCronogramaRequest req) {

        if (req == null || req.getIdCotizacion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cotización es obligatoria");
        }

        if (req.getFechaInicio() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de inicio es obligatoria");
        }

        Cotizacion cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        Optional<Cronograma> existente = cronogramaRepo.findByCotizacion_IdCotizacion(req.getIdCotizacion());
        if (existente.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un cronograma para esta cotización"
            );
        }

        Cronograma cronograma = new Cronograma();
        cronograma.setCotizacion(cotizacion);
        cronograma.setFechaInicio(req.getFechaInicio());

        LocalDate inicioPlanificado = req.getFechaInicio();
        while (inicioPlanificado.getDayOfWeek() != java.time.DayOfWeek.MONDAY) {
            inicioPlanificado = inicioPlanificado.plusDays(1);
        }

        cronograma.setFechaInicioPlanificada(inicioPlanificado);

        List<CotizacionDetalle> detallesCotizacion = cotizacion.getDetalles();

        int maxSemana = 0;
        if (detallesCotizacion != null && !detallesCotizacion.isEmpty()) {
            maxSemana = detallesCotizacion.stream()
                    .map(CotizacionDetalle::getSemana)
                    .filter(s -> s != null)
                    .max(Integer::compareTo)
                    .orElse(0);
        }

        int totalSemanas = (maxSemana > 0) ? maxSemana : 1;
        cronograma.setTotalSemanas(totalSemanas);

        cronograma.setFechaFinEstimada(
                inicioPlanificado.plusWeeks(totalSemanas - 1).plusDays(5)
        );

        if (cronograma.getEstadoCronograma() == null) {
            cronograma.setEstadoCronograma(EstadoCronograma.EN_PROCESO);
        }

        Cronograma guardado = cronogramaRepo.save(cronograma);

        if (detallesCotizacion != null && !detallesCotizacion.isEmpty()) {
            for (CotizacionDetalle det : detallesCotizacion) {

                if (det.getTipoItem() == null || !"ACTIVIDAD".equals(det.getTipoItem().name())) {
            continue;
        }
                Integer semanaDetalle = (det.getSemana() != null && det.getSemana() > 0)
                        ? det.getSemana()
                        : 1;

                LocalDate fechaInicioSemana = inicioPlanificado.plusWeeks(semanaDetalle - 1);
                LocalDate fechaFinSemana = fechaInicioSemana.plusDays(5);

                CronogramaDetalle cd = new CronogramaDetalle();
                cd.setCronograma(guardado);

                cd.setServicio(
                        det.getServicio() != null
                                ? det.getServicio().getNombreServicio()
                                : "Sin servicio"
                );

                cd.setActividad(
                    det.getDescripcion() != null && !det.getDescripcion().isBlank()
                ? det.getDescripcion()
                : "Actividad"
                );

                cd.setDescripcion(det.getDescripcion());
                cd.setSemana(semanaDetalle);
                cd.setFechaInicioSemana(fechaInicioSemana);
                cd.setFechaFinSemana(fechaFinSemana);
                cd.setTrabajadorAsignado(null);
                cd.setEstadoActividad(EstadoActividadCronograma.PENDIENTE);
                cd.setPorcentaje(BigDecimal.ZERO);
                cd.setNovedades(null);

                cronogramaDetalleRepo.save(cd);
            }
        }

        CronogramaResponse response = new CronogramaResponse();
        response.setIdCronograma(guardado.getIdCronograma());
        response.setFechaInicio(guardado.getFechaInicio());
        response.setFechaFinEstimada(guardado.getFechaFinEstimada());
        response.setEstadoCronograma(
                guardado.getEstadoCronograma() != null
                        ? guardado.getEstadoCronograma().name()
                        : "EN_PROCESO"
        );

        return response;
    }
    
    
     public List<CronogramaListResponse> listarCronogramasPorUsuario(String correoUsuario) {
        Usuario usuario = usuarioRepo.findByCorreoUsuario(correoUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        List<Cronograma> cronogramas;

        if (usuario.getIdRol() == 1 || usuario.getIdRol() == 2) {
            cronogramas = cronogramaRepo.findAllByOrderByIdCronogramaDesc();
        } else {
            cronogramas = cronogramaRepo
                    .findByCotizacion_Solicitud_Usuario_IdUsuarioOrderByIdCronogramaDesc(usuario.getIdUsuario());
        }

        return cronogramas.stream()
                .map(this::mapToListResponse)
                .toList();
    }

    private CronogramaListResponse mapToListResponse(Cronograma cronograma) {
        CronogramaListResponse res = new CronogramaListResponse();

        res.setId(cronograma.getIdCronograma());
        res.setIdCronograma(cronograma.getIdCronograma());

        if (cronograma.getCotizacion() != null) {
            res.setIdCotizacion(cronograma.getCotizacion().getIdCotizacion());

            if (cronograma.getCotizacion().getSolicitud() != null) {
                res.setNombreProyecto(
                        cronograma.getCotizacion().getSolicitud().getNombreProyectoUsuario()
                );

                if (cronograma.getCotizacion().getSolicitud().getUsuario() != null) {
                    res.setNombreCliente(
                            cronograma.getCotizacion().getSolicitud().getUsuario().getNombreUsuario()
                    );
                }
            }
        }

        res.setEstadoCronograma(
                cronograma.getEstadoCronograma() != null
                        ? cronograma.getEstadoCronograma().name()
                        : null
        );

        res.setFechaInicio(cronograma.getFechaInicio());
        res.setFechaFin(cronograma.getFechaFinEstimada());

        res.setAvanceGeneral(calcularAvanceGeneral(cronograma));

        return res;
    }

    private Integer calcularAvanceGeneral(Cronograma cronograma) {
        if (cronograma.getDetalles() == null || cronograma.getDetalles().isEmpty()) {
            return 0;
        }

        int cantidadConPorcentaje = 0;
        int suma = 0;

        for (CronogramaDetalle detalle : cronograma.getDetalles()) {
            if (detalle.getPorcentaje() != null) {
                suma += detalle.getPorcentaje().intValue();
                cantidadConPorcentaje++;
            }
        }

        if (cantidadConPorcentaje == 0) {
            return 0;
        }

        return Math.round((float) suma / cantidadConPorcentaje);
    }
}
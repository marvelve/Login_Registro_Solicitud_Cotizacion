/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.CrearCronogramaRequest;
import com.interivalle.DTO.CronogramaDetalleVistaDTO;
import com.interivalle.DTO.CronogramaResponse;
import com.interivalle.DTO.CronogramaVistaResponse;
import com.interivalle.DTO.SemanaCronogramaDTO;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.Cronograma;
import com.interivalle.Modelo.CronogramaDetalle;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Repositorio.CotizacionRepositorio;
import com.interivalle.Repositorio.CronogramaDetalleRepositorio;
import com.interivalle.Repositorio.CronogramaRepositorio;
import jakarta.transaction.Transactional;
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
            dto.setEstado(d.getEstado()!= null ? d.getEstado().toString() : "PENDIENTE");
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

    List<com.interivalle.Modelo.CotizacionDetalle> detallesCotizacion =
            cotizacion.getDetalles();

    int maxSemana = 0;
    if (detallesCotizacion != null && !detallesCotizacion.isEmpty()) {
        maxSemana = detallesCotizacion.stream()
                .map(com.interivalle.Modelo.CotizacionDetalle::getSemana)
                .filter(s -> s != null)
                .max(Integer::compareTo)
                .orElse(0);
    }

    if (maxSemana > 0) {
        cronograma.setFechaFinEstimada(inicioPlanificado.plusWeeks(maxSemana - 1).plusDays(5));
    } else {
        cronograma.setFechaFinEstimada(inicioPlanificado.plusDays(5));
    }

    if (cronograma.getEstadoCronograma() == null) {
        cronograma.setEstadoCronograma(com.interivalle.Modelo.enums.EstadoCronograma.EN_PROCESO);
    }

    Cronograma guardado = cronogramaRepo.save(cronograma);

    if (detallesCotizacion != null) {
        for (com.interivalle.Modelo.CotizacionDetalle det : detallesCotizacion) {
            CronogramaDetalle cd = new CronogramaDetalle();
            cd.setCronograma(guardado);
            cd.setActividad(det.getActividadMaterial() != null ? det.getActividadMaterial() : det.getDescripcion());
            cd.setDescripcion(det.getDescripcion());
            cd.setSemana(det.getSemana());
            cd.setEstado(com.interivalle.Modelo.enums.EstadoCronograma.EN_PROCESO.toString());
            cd.setPorcentaje(java.math.BigDecimal.ZERO);
            cd.setTrabajadorAsignado(null);
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
}
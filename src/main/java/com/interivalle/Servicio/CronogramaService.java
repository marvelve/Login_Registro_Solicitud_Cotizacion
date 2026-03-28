/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.CronogramaDetalleResponse;
import com.interivalle.DTO.CronogramaResponse;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.CotizacionDetalle;
import com.interivalle.Modelo.Cronograma;
import com.interivalle.Modelo.CronogramaDetalle;
import com.interivalle.Modelo.enums.EstadoActividadCronograma;
import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.EstadoCronograma;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import com.interivalle.Repositorio.CotizacionDetalleRepositorio;
import com.interivalle.Repositorio.CotizacionRepositorio;
import com.interivalle.Repositorio.CronogramaDetalleRepositorio;
import com.interivalle.Repositorio.CronogramaRepositorio;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
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
public class CronogramaService {

    @Autowired
    private CronogramaRepositorio cronogramaRepo;

    @Autowired
    private CronogramaDetalleRepositorio cronogramaDetalleRepo;

    @Autowired
    private CotizacionRepositorio cotizacionRepo;

    @Autowired
    private CotizacionDetalleRepositorio cotizacionDetalleRepo;

    @Transactional
    public CronogramaResponse crearDesdeCotizacionAprobada(Integer idCotizacion, LocalDate fechaInicio) {

        if (idCotizacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El idCotizacion es obligatorio");
        }

        if (fechaInicio == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de inicio es obligatoria");
        }

        Cotizacion cotizacion = cotizacionRepo.findById(idCotizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        if (cotizacion.getEstado() == null || cotizacion.getEstado() != EstadoCotizacion.APROBADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Solo se puede crear cronograma para cotizaciones en estado APROBADA"
            );
        }

        cronogramaRepo.findByCotizacion_IdCotizacion(idCotizacion).ifPresent(c -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La cotización ya tiene cronograma");
        });

        List<CotizacionDetalle> detallesCotizacion = cotizacionDetalleRepo
                .findByCotizacion_IdCotizacionOrderBySemanaAsc(idCotizacion);

        if (detallesCotizacion == null || detallesCotizacion.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La cotización no tiene detalle para generar cronograma"
            );
        }

        List<CotizacionDetalle> actividades = detallesCotizacion.stream()
                .filter(d -> d.getTipoItem() != null && d.getTipoItem() == TipoItemCotizacion.ACTIVIDAD)
                .collect(Collectors.toList());

        if (actividades.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La cotización no tiene actividades para generar cronograma"
            );
        }

        int semanaMaximaCotizacion = actividades.stream()
                .map(CotizacionDetalle::getSemana)
                .filter(s -> s != null)
                .max(Comparator.naturalOrder())
                .orElse(1);

        int totalSemanasCronograma = semanaMaximaCotizacion + 1;

        LocalDate fechaInicioPlanificada = calcularInicioSemanaUno(fechaInicio);
        LocalDate fechaFinEstimada = calcularFechaFinEstimada(fechaInicioPlanificada, totalSemanasCronograma);

        Cronograma cronograma = new Cronograma();
        cronograma.setCotizacion(cotizacion);
        cronograma.setFechaInicio(fechaInicio);
        cronograma.setFechaInicioPlanificada(fechaInicioPlanificada);
        cronograma.setFechaFinEstimada(fechaFinEstimada);
        cronograma.setTotalSemanas(totalSemanasCronograma);
        cronograma.setEstadoCronograma(EstadoCronograma.EN_PROCESO);

        cronograma = cronogramaRepo.save(cronograma);

        List<CronogramaDetalle> detallesCronograma = new ArrayList<>();

        for (CotizacionDetalle detalleCotizacion : actividades) {
            Integer semana = detalleCotizacion.getSemana() != null ? detalleCotizacion.getSemana() : 1;

            LocalDate fechaInicioSemana = calcularFechaInicioSemana(fechaInicioPlanificada, semana);
            LocalDate fechaFinSemana = calcularFechaFinSemana(fechaInicioSemana);

            CronogramaDetalle detalle = new CronogramaDetalle();
            detalle.setCronograma(cronograma);
            detalle.setServicio(obtenerNombreServicio(detalleCotizacion));
            detalle.setActividad(obtenerNombreActividad(detalleCotizacion));
            detalle.setDescripcion(obtenerDescripcion(detalleCotizacion));
            detalle.setSemana(semana);
            detalle.setFechaInicioSemana(fechaInicioSemana);
            detalle.setFechaFinSemana(fechaFinSemana);
            detalle.setTrabajadorAsignado(null);
            detalle.setEstadoActividad(
                    semana == 1
                            ? EstadoActividadCronograma.EN_PROCESO
                            : EstadoActividadCronograma.PENDIENTE
            );
            detalle.setPorcentaje(BigDecimal.ZERO);
            detalle.setNovedades(null);

            detallesCronograma.add(detalle);
        }

        cronogramaDetalleRepo.saveAll(detallesCronograma);

        return mapToResponse(cronograma, detallesCronograma);
    }

    public CronogramaResponse obtenerPorCotizacion(Integer idCotizacion) {
        Cronograma cronograma = cronogramaRepo.findByCotizacion_IdCotizacion(idCotizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado"));

        List<CronogramaDetalle> detalles = cronogramaDetalleRepo
                .findByCronograma_IdCronogramaOrderBySemanaAsc(cronograma.getIdCronograma());

        return mapToResponse(cronograma, detalles);
    }

    public CronogramaResponse obtenerPorId(Integer idCronograma) {
        Cronograma cronograma = cronogramaRepo.findById(idCronograma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado"));

        List<CronogramaDetalle> detalles = cronogramaDetalleRepo
                .findByCronograma_IdCronogramaOrderBySemanaAsc(idCronograma);

        return mapToResponse(cronograma, detalles);
    }

    @Transactional
    public CronogramaDetalleResponse actualizarDetalle(
            Integer idCronogramaDetalle,
            String trabajadorAsignado,
            String estadoActividad,
            BigDecimal porcentaje,
            String novedades
    ) {
        CronogramaDetalle detalle = cronogramaDetalleRepo.findById(idCronogramaDetalle)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle de cronograma no encontrado"));

        if (trabajadorAsignado != null) {
            detalle.setTrabajadorAsignado(trabajadorAsignado);
        }

        if (estadoActividad != null && !estadoActividad.isBlank()) {
            try {
                detalle.setEstadoActividad(EstadoActividadCronograma.valueOf(estadoActividad.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Estado de actividad inválido. Use: PENDIENTE, EN_PROCESO, TERMINADA o ATRASADA"
                );
            }
        }

        if (porcentaje != null) {
            if (porcentaje.compareTo(BigDecimal.ZERO) < 0 || porcentaje.compareTo(new BigDecimal("100")) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El porcentaje debe estar entre 0 y 100");
            }
            detalle.setPorcentaje(porcentaje);
        }

        if (novedades != null) {
            detalle.setNovedades(novedades);
        }

        detalle = cronogramaDetalleRepo.save(detalle);
        actualizarEstadoGeneralCronograma(detalle.getCronograma().getIdCronograma());

        return mapDetalleToResponse(detalle);
    }

    @Transactional
    public void actualizarEstadoGeneralCronograma(Integer idCronograma) {
        Cronograma cronograma = cronogramaRepo.findById(idCronograma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cronograma no encontrado"));

        List<CronogramaDetalle> detalles = cronogramaDetalleRepo
                .findByCronograma_IdCronogramaOrderBySemanaAsc(idCronograma);

        boolean todosTerminados = !detalles.isEmpty() && detalles.stream()
                .allMatch(d -> d.getEstadoActividad() == EstadoActividadCronograma.TERMINADA);

        if (todosTerminados) {
            cronograma.setEstadoCronograma(EstadoCronograma.FINALIZADO);
        } else {
            cronograma.setEstadoCronograma(EstadoCronograma.EN_PROCESO);
        }

        cronogramaRepo.save(cronograma);
    }

    private LocalDate calcularInicioSemanaUno(LocalDate fechaInicio) {
        LocalDate base = fechaInicio.plusDays(3);

        if (base.getDayOfWeek() == DayOfWeek.MONDAY) {
            return base;
        }

        return base.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    private LocalDate calcularFechaInicioSemana(LocalDate fechaInicioPlanificada, Integer semana) {
        int diasASumar = (semana - 1) * 7;
        return fechaInicioPlanificada.plusDays(diasASumar);
    }

    private LocalDate calcularFechaFinSemana(LocalDate fechaInicioSemana) {
        return fechaInicioSemana.plusDays(5); // lunes a sábado
    }

    private LocalDate calcularFechaFinEstimada(LocalDate fechaInicioPlanificada, Integer totalSemanas) {
        LocalDate inicioUltimaSemana = calcularFechaInicioSemana(fechaInicioPlanificada, totalSemanas);
        return calcularFechaFinSemana(inicioUltimaSemana);
    }

    private String obtenerNombreServicio(CotizacionDetalle detalle) {
        if (detalle.getServicio() != null && detalle.getServicio().getNombreServicio() != null) {
            return detalle.getServicio().getNombreServicio();
        }
        return "";
    }

    private String obtenerNombreActividad(CotizacionDetalle detalle) {
        if (detalle.getActividadMaterial() != null && !detalle.getActividadMaterial().isBlank()) {
            return detalle.getActividadMaterial();
        }

        if (detalle.getDescripcion() != null && !detalle.getDescripcion().isBlank()) {
            return detalle.getDescripcion();
        }

        if (detalle.getCategoria() != null && !detalle.getCategoria().isBlank()) {
            return detalle.getCategoria();
        }

        return "Actividad";
    }

    private String obtenerDescripcion(CotizacionDetalle detalle) {
        if (detalle.getDescripcion() != null && !detalle.getDescripcion().isBlank()) {
            return detalle.getDescripcion();
        }

        if (detalle.getActividadMaterial() != null && !detalle.getActividadMaterial().isBlank()) {
            return detalle.getActividadMaterial();
        }

        return "";
    }

    private CronogramaResponse mapToResponse(Cronograma cronograma, List<CronogramaDetalle> detalles) {
        CronogramaResponse response = new CronogramaResponse();
        response.setIdCronograma(cronograma.getIdCronograma());
        response.setIdCotizacion(cronograma.getCotizacion().getIdCotizacion());
        response.setProyecto(
                cronograma.getCotizacion().getSolicitud() != null
                        ? cronograma.getCotizacion().getSolicitud().getNombreProyectoUsuario()
                        : null
        );
        response.setEstadoCronograma(cronograma.getEstadoCronograma().name());
        response.setFechaInicio(cronograma.getFechaInicio());
        response.setFechaInicioPlanificada(cronograma.getFechaInicioPlanificada());
        response.setFechaFinEstimada(cronograma.getFechaFinEstimada());
        response.setTotalSemanas(cronograma.getTotalSemanas());

        List<CronogramaDetalleResponse> detallesResponse = detalles.stream()
                .map(this::mapDetalleToResponse)
                .collect(Collectors.toList());

        response.setDetalles(detallesResponse);
        return response;
    }

    private CronogramaDetalleResponse mapDetalleToResponse(CronogramaDetalle detalle) {
        CronogramaDetalleResponse dto = new CronogramaDetalleResponse();
        dto.setIdCronogramaDetalle(detalle.getIdCronogramaDetalle());
        dto.setServicio(detalle.getServicio());
        dto.setActividad(detalle.getActividad());
        dto.setDescripcion(detalle.getDescripcion());
        dto.setSemana(detalle.getSemana());
        dto.setFechaInicioSemana(detalle.getFechaInicioSemana());
        dto.setFechaFinSemana(detalle.getFechaFinSemana());
        dto.setTrabajadorAsignado(detalle.getTrabajadorAsignado());
        dto.setEstadoActividad(detalle.getEstadoActividad().name());
        dto.setPorcentaje(detalle.getPorcentaje());
        dto.setNovedades(detalle.getNovedades());
        return dto;
    }
}

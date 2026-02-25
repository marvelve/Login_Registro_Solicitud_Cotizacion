/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.CrearCotizacionRequest;
import com.interivalle.DTO.CotizacionDetalleResponse;
import com.interivalle.DTO.CotizacionHistorialResponse;
import com.interivalle.DTO.CotizacionObservacionResponse;
import com.interivalle.DTO.CotizacionResponse;
import com.interivalle.DTO.ObservacionRequest;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.CotizacionDetalle;
import com.interivalle.Modelo.CotizacionHistorialEstado;
import com.interivalle.Modelo.CotizacionObservacion;
import com.interivalle.Modelo.Servicios;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import com.interivalle.Modelo.enums.TipoObservacion;
import com.interivalle.Repositorio.CotizacionDetalleRepositorio;
import com.interivalle.Repositorio.CotizacionHistorialRepositorio;
import com.interivalle.Repositorio.CotizacionObservacionRepositorio;
import com.interivalle.Repositorio.CotizacionRepositorio;
import com.interivalle.Repositorio.ServiciosRepositorio;
import com.interivalle.Repositorio.SolicitudRepositorio;
import com.interivalle.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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
public class CotizacionService {

    @Autowired private CotizacionRepositorio cotizacionRepo;
    @Autowired private CotizacionDetalleRepositorio detalleRepo;
    @Autowired private CotizacionObservacionRepositorio obsRepo;
    @Autowired private CotizacionHistorialRepositorio histRepo;

    @Autowired private SolicitudRepositorio solicitudRepo;
    @Autowired private ServiciosRepositorio serviciosRepo;
    @Autowired private UsuarioRepositorio usuarioRepo;


    // CREA COTIZACION
    @Transactional
    public CotizacionResponse crearCotizacion(Integer idUsuario, CrearCotizacionRequest req) {

        Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Solicitud solicitud = solicitudRepo.findById(req.getSolicitudId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        // Validar dueño de la solicitud (cliente)
        if (!solicitud.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes cotizar una solicitud de otro usuario");
        }

        if (req.getDetalles() == null || req.getDetalles().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar al menos 1 item en detalles");
        }

        Cotizacion cot = new Cotizacion();
        cot.setSolicitud(solicitud);
        cot.setTipo(req.getTipo());
        cot.setEstado(EstadoCotizacion.BORRADOR);
        cot.setCreadaPor(usuario);

        // Guardar cabecera primero para obtener id
        cot = cotizacionRepo.save(cot);

        BigDecimal totalGeneral = BigDecimal.ZERO;
        BigDecimal totalManoObra = BigDecimal.ZERO;
        BigDecimal totalMateriales = BigDecimal.ZERO;
        BigDecimal totalProductos = BigDecimal.ZERO;

        // Crear detalles
        for (CrearCotizacionRequest.DetalleItem item : req.getDetalles()) {

            Servicios servicio = serviciosRepo.findById(item.getServicioId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Servicio no encontrado: " + item.getServicioId()
                ));

            validarItem(item);

            BigDecimal subtotal = item.getCantidad().multiply(item.getPrecioUnitario());

            CotizacionDetalle det = new CotizacionDetalle();
            det.setCotizacion(cot);
            det.setServicio(servicio);
            det.setTipoItem(item.getTipoItem());
            det.setCategoria(item.getCategoria());
            det.setSemana(item.getSemana());
            det.setDescripcion(item.getDescripcion());
            det.setCantidad(item.getCantidad());
            det.setPrecioUnitario(item.getPrecioUnitario());
            det.setSubtotal(subtotal);

            detalleRepo.save(det);

            totalGeneral = totalGeneral.add(subtotal);

            if (item.getTipoItem() == TipoItemCotizacion.MANO_OBRA) {
                totalManoObra = totalManoObra.add(subtotal);
            } else if (item.getTipoItem() == TipoItemCotizacion.MATERIAL) {
                totalMateriales = totalMateriales.add(subtotal);
            } else if (item.getTipoItem() == TipoItemCotizacion.PRODUCTO) {
                totalProductos = totalProductos.add(subtotal);
            }
        }

        cot.setTotalManoObra(totalManoObra);
        cot.setTotalMateriales(totalMateriales);
        cot.setTotalProductos(totalProductos);
        cot.setTotalEstimado(totalGeneral);

        cot = cotizacionRepo.save(cot);

        // Historial (creación)
        guardarHistorial(cot, null, EstadoCotizacion.BORRADOR, usuario);

        return toResponseCompleto(cot);
    }


    // LISTA POR CLIENTE
    public List<CotizacionResponse> listarPorCliente(Integer idUsuario) {
        List<Cotizacion> lista = cotizacionRepo.findBySolicitud_Usuario_IdUsuario(idUsuario);
        List<CotizacionResponse> out = new ArrayList<>();
        for (Cotizacion c : lista) {
            out.add(toResponseBasico(c));
        }
        return out;
    }


    // DETALLE POR ID CLIENTE GET BY ID (CLIENTE)

    public CotizacionResponse verDetalle(Integer idUsuario, Integer idCotizacion) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);
        return toResponseCompleto(cot);
    }

    // ENVIAR
    @Transactional
    public CotizacionResponse enviar(Integer idUsuario, Integer idCotizacion) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

        if (cot.getEstado() != EstadoCotizacion.BORRADOR) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo puedes enviar cotizaciones en BORRADOR");
        }

        Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        EstadoCotizacion anterior = cot.getEstado();
        cot.setEstado(EstadoCotizacion.ENVIADA);
        cot = cotizacionRepo.save(cot);

        guardarHistorial(cot, anterior, EstadoCotizacion.ENVIADA, usuario);

        return toResponseCompleto(cot);
    }

    // APROBAR
    @Transactional
    public CotizacionResponse aprobar(Integer idUsuario, Integer idCotizacion, ObservacionRequest req) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

        if (cot.getEstado() != EstadoCotizacion.ENVIADA && cot.getEstado() != EstadoCotizacion.EN_REVISION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo puedes aprobar cotizaciones ENVIADA o EN_REVISION");
        }

        Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        EstadoCotizacion anterior = cot.getEstado();
        cot.setEstado(EstadoCotizacion.APROBADA);
        cot = cotizacionRepo.save(cot);

        guardarObservacion(cot, usuario, TipoObservacion.APROBACION, req.getMensaje());
        guardarHistorial(cot, anterior, EstadoCotizacion.APROBADA, usuario);

        return toResponseCompleto(cot);
    }

    // RECHAZAR
    @Transactional
    public CotizacionResponse rechazar(Integer idUsuario, Integer idCotizacion, ObservacionRequest req) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

        if (cot.getEstado() != EstadoCotizacion.ENVIADA && cot.getEstado() != EstadoCotizacion.EN_REVISION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo puedes rechazar cotizaciones ENVIADA o EN_REVISION");
        }

        Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        EstadoCotizacion anterior = cot.getEstado();
        cot.setEstado(EstadoCotizacion.RECHAZADA);
        cot = cotizacionRepo.save(cot);

        guardarObservacion(cot, usuario, TipoObservacion.RECHAZO, req.getMensaje());
        guardarHistorial(cot, anterior, EstadoCotizacion.RECHAZADA, usuario);

        return toResponseCompleto(cot);
    }

    // Helpers de negocio + validación
    private void validarItem(CrearCotizacionRequest.DetalleItem item) {
        if (item.getTipoItem() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tipoItem es obligatorio");
        }
        if (item.getCantidad() == null || item.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cantidad debe ser > 0");
        }
        if (item.getPrecioUnitario() == null || item.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "precioUnitario no puede ser negativo");
        }
    }

    private Cotizacion getCotizacionDelUsuario(Integer idUsuario, Integer idCotizacion) {
        return cotizacionRepo.findByIdCotizacionAndSolicitud_Usuario_IdUsuario(idCotizacion, idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada o sin acceso"));
    }

    private void guardarObservacion(Cotizacion cot, Usuario usuario, TipoObservacion tipo, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mensaje es obligatorio");
        }
        CotizacionObservacion obs = new CotizacionObservacion();
        obs.setCotizacion(cot);
        obs.setUsuario(usuario);
        obs.setTipo(tipo);
        obs.setMensaje(mensaje.trim());
        obsRepo.save(obs);
    }

    private void guardarHistorial(Cotizacion cot, EstadoCotizacion anterior, EstadoCotizacion nuevo, Usuario usuario) {
        CotizacionHistorialEstado h = new CotizacionHistorialEstado();
        h.setCotizacion(cot);
        h.setEstadoAnterior(anterior == null ? EstadoCotizacion.BORRADOR : anterior);
        h.setEstadoNuevo(nuevo);
        h.setCambiadoPor(usuario);
        histRepo.save(h);
    }

    // Mappers -> Response DTOs
    private CotizacionResponse toResponseBasico(Cotizacion cot) {
        CotizacionResponse r = new CotizacionResponse();
        r.setIdCotizacion(cot.getIdCotizacion());

        r.setSolicitudId(cot.getSolicitud().getIdSolicitud());
        r.setNombreProyecto(cot.getSolicitud().getNombreProyectoUsuario());

        r.setTipo(cot.getTipo());
        r.setEstado(cot.getEstado());

        r.setTotalManoObra(cot.getTotalManoObra());
        r.setTotalMateriales(cot.getTotalMateriales());
        r.setTotalProductos(cot.getTotalProductos());
        r.setTotalEstimado(cot.getTotalEstimado());

        r.setFechaCreacion(cot.getFechaCreacion());
        r.setFechaActualizacion(cot.getFechaActualizacion());

        // En listado no cargamos detalles/obs/historial
        r.setDetalles(null);
        r.setObservaciones(null);
        r.setHistorial(null);

        return r;
    }

    private CotizacionResponse toResponseCompleto(Cotizacion cot) {
        CotizacionResponse r = toResponseBasico(cot);

        // Detalles
        List<CotizacionDetalle> detalles = detalleRepo.findByCotizacion_IdCotizacion(cot.getIdCotizacion());
        detalles.sort(Comparator
            .comparing((CotizacionDetalle d) -> d.getServicio().getIdServicio())
            .thenComparing(d -> d.getTipoItem().name())
            .thenComparing(d -> d.getCategoria() == null ? "" : d.getCategoria())
            .thenComparing(d -> d.getSemana() == null ? "" : d.getSemana())
            .thenComparing(d -> d.getDescripcion() == null ? "" : d.getDescripcion())
        );

        List<CotizacionDetalleResponse> detResp = new ArrayList<>();
        for (CotizacionDetalle d : detalles) {
            CotizacionDetalleResponse dr = new CotizacionDetalleResponse();
            dr.setIdDetalle(d.getIdDetalle());
            dr.setServicioId(d.getServicio().getIdServicio());
            dr.setNombreServicio(d.getServicio().getNombreServicio());

            dr.setTipoItem(d.getTipoItem());
            dr.setCategoria(d.getCategoria());
            dr.setSemana(d.getSemana());
            dr.setDescripcion(d.getDescripcion());

            dr.setCantidad(d.getCantidad());
            dr.setPrecioUnitario(d.getPrecioUnitario());
            dr.setSubtotal(d.getSubtotal());

            detResp.add(dr);
        }
        r.setDetalles(detResp);

        // Observaciones
        List<CotizacionObservacion> obs = obsRepo.findByCotizacion_IdCotizacionOrderByFechaAsc(cot.getIdCotizacion());
        List<CotizacionObservacionResponse> obsResp = new ArrayList<>();
        for (CotizacionObservacion o : obs) {
            CotizacionObservacionResponse or = new CotizacionObservacionResponse();
            or.setIdObservacion(o.getIdObservacion());
            or.setTipo(o.getTipo());
            or.setMensaje(o.getMensaje());
            or.setUsuarioNombre(o.getUsuario().getNombreUsuario());
            or.setFecha(o.getFecha());
            obsResp.add(or);
        }
        r.setObservaciones(obsResp);

        // Historial
        List<CotizacionHistorialEstado> hist = histRepo.findByCotizacion_IdCotizacionOrderByFechaAsc(cot.getIdCotizacion());
        List<CotizacionHistorialResponse> histResp = new ArrayList<>();
        for (CotizacionHistorialEstado h : hist) {
            CotizacionHistorialResponse hr = new CotizacionHistorialResponse();
            hr.setIdHistorial(h.getIdHistorial());
            hr.setEstadoAnterior(h.getEstadoAnterior());
            hr.setEstadoNuevo(h.getEstadoNuevo());
            hr.setUsuarioNombre(h.getCambiadoPor().getNombreUsuario());
            hr.setFecha(h.getFecha());
            histResp.add(hr);
        }
        r.setHistorial(histResp);

        return r;
    }
}

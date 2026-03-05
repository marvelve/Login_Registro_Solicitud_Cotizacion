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
import com.interivalle.DTO.GenerarCotizacionBaseRequest;
import com.interivalle.DTO.ObservacionRequest;
import com.interivalle.Modelo.ActividadMaterial;
import com.interivalle.Modelo.CatalogoItem;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.CotizacionDetalle;
import com.interivalle.Modelo.CotizacionHistorialEstado;
import com.interivalle.Modelo.CotizacionObservacion;
import com.interivalle.Modelo.Servicios;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.TipoCotizacion;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import com.interivalle.Modelo.enums.TipoObservacion;
import com.interivalle.Repositorio.ActividadMaterialRepositorio;
import com.interivalle.Repositorio.CatalogoItemRepositorio;
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
    @Autowired private ActividadMaterialRepositorio actividadMaterialRepo;
    @Autowired private CatalogoItemRepositorio catalogoItemRepo;


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
            det.setPrecioUnitarioVenta(item.getPrecioUnitario());
            det.setSubtotal(subtotal);

            detalleRepo.save(det);

            totalGeneral = totalGeneral.add(subtotal);

            if (item.getTipoItem() == TipoItemCotizacion.ACTIVIDAD) {
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
            .thenComparing(d -> d.getSemana() == null ? 0 : d.getSemana())
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
            dr.setPrecioUnitario(d.getPrecioUnitarioVenta());
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
    
    
    @Transactional
    public CotizacionResponse generarCotizacionBaseDesdeSolicitud(Integer idUsuario, GenerarCotizacionBaseRequest req) {

    Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    Solicitud solicitud = solicitudRepo.findById(req.getSolicitudId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

    // Validar dueño
    if (!solicitud.getUsuario().getIdUsuario().equals(idUsuario)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes generar cotización para otra solicitud");
    }

    // (Opcional) Si tu Solicitud maneja tipoSolicitud:
    // if (!"COTIZACION_BASE".equalsIgnoreCase(solicitud.getTipoSolicitud())) {
    //     throw new ResponseStatusException(HttpStatus.CONFLICT, "La solicitud no es de tipo COTIZACION_BASE");
    // }

    // Evitar duplicar cotizaciones en BORRADOR para la misma solicitud
    cotizacionRepo.findFirstBySolicitud_IdSolicitudAndEstado(solicitud.getIdSolicitud(), EstadoCotizacion.BORRADOR)
            .ifPresent(c -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe una cotización en BORRADOR para esta solicitud. Envíala o elimínala antes de generar otra.");
            });

    // 1) Crear cotización (cabecera)
    Cotizacion cot = new Cotizacion();
    cot.setSolicitud(solicitud);
    cot.setTipo(TipoCotizacion.BASE);
    cot.setEstado(EstadoCotizacion.BORRADOR);
    cot.setCreadaPor(usuario);

    cot = cotizacionRepo.save(cot);

    // 2) MVP: traer ACTIVIDADES de Obra Blanca (servicio 1) que estén activas
    // Más adelante filtras según inputs (tipoCielo, divisionPared, etc.)
    Integer idServicioObraBlanca = 1; // AJUSTA a tu id real
    List<CatalogoItem> actividades = catalogoItemRepo
            .findByServicio_IdServiciosAndTipoItemAndActivoTrue(idServicioObraBlanca, TipoItemCotizacion.ACTIVIDAD);

    BigDecimal total = BigDecimal.ZERO;
    BigDecimal totalActividades = BigDecimal.ZERO;
    BigDecimal totalMateriales = BigDecimal.ZERO;

    for (CatalogoItem act : actividades) {

     Integer semana = act.getSemana();   // 1..5 viene de catalogo_item
    if (semana == null) semana = 1;     // fallback

        // 2.1 Crear detalle ACTIVIDAD
        BigDecimal precioActividad = act.getPrecioUnitarioVenta() != null
                ? act.getPrecioUnitarioVenta()
                : BigDecimal.ZERO;

        CotizacionDetalle detAct = new CotizacionDetalle();
        detAct.setCotizacion(cot);
        detAct.setServicio(act.getServicio());
        detAct.setTipoItem(TipoItemCotizacion.ACTIVIDAD);
        detAct.setCategoria(act.getCategoria());
        detAct.setSemana(semana);
        detAct.setDescripcion(act.getNombreItem());
        detAct.setCantidad(BigDecimal.ONE);
        detAct.setPrecioUnitarioVenta(precioActividad);
        detAct.setSubtotalVenta(precioActividad);

        detalleRepo.save(detAct);

        totalActividades = totalActividades.add(precioActividad);
        total = total.add(precioActividad);

        // 2.2 Generar MATERIALES desde BOM (actividad_material)
        List<ActividadMaterial> bom = actividadMaterialRepo
                .findByActividad_IdCatalogoItemAndSemanaAndActivoTrue(act.getIdCatalogoItem(), semana);

        for (ActividadMaterial am : bom) {

            CatalogoItem material = am.getMaterial();

            BigDecimal cantidad = am.getCantidad() != null ? am.getCantidad(): BigDecimal.ZERO;

            BigDecimal pVenta = material.getPrecioUnitarioVenta() != null
                    ? material.getPrecioUnitarioVenta()
                    : BigDecimal.ZERO;

            //BigDecimal subtotal = cantidad.multiply(pVenta);
            BigDecimal subtotalVenta = cantidad.multiply(pVenta);
            
            CotizacionDetalle detMat = new CotizacionDetalle();
            detMat.setCotizacion(cot);
            detMat.setServicio(material.getServicio());
            detMat.setTipoItem(TipoItemCotizacion.MATERIAL);
            detMat.setCategoria(material.getCategoria());
            detMat.setSemana(semana);
            detMat.setDescripcion(material.getNombreItem());
            detMat.setCantidad(cantidad);
            detMat.setPrecioUnitarioVenta(pVenta);
            //detMat.setSubtotal(subtotal);
           
            detMat.setSubtotalVenta(subtotalVenta);

            // Si sigues usando "subtotal" como total principal:
            detMat.setSubtotal(subtotalVenta);

            detalleRepo.save(detMat);

            totalMateriales = totalMateriales.add(subtotalVenta);
            total = total.add(subtotalVenta);
        }
    }

    // 3) Guardar totales
    cot.setTotalManoObra(totalActividades);  // ACTIVIDADES = Mano de obra
    cot.setTotalMateriales(totalMateriales);
    cot.setTotalProductos(BigDecimal.ZERO); // MVP
    cot.setTotalEstimado(total);

    cot = cotizacionRepo.save(cot);

    // Historial creación (correcto)
    guardarHistorial(cot, null, EstadoCotizacion.BORRADOR, usuario);

    return toResponseCompleto(cot);
    }
}

package com.interivalle.Servicio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interivalle.DTO.ActividadAgrupadaResponse;
import com.interivalle.DTO.AprobarCotizacionRequest;
import com.interivalle.DTO.CarpinteriaBaseRequest;
import com.interivalle.DTO.CotizacionVistaCompletaResponse;
import com.interivalle.DTO.CotizacionPersonalizadaDetalleResponse;
import com.interivalle.DTO.CotizacionActividadResponse;
import com.interivalle.DTO.CotizacionBaseResponse;
import com.interivalle.DTO.CotizacionDetalleResponse;
import com.interivalle.DTO.CotizacionHistorialResponse;
import com.interivalle.DTO.CotizacionObservacionResponse;
import com.interivalle.DTO.CotizacionResponse;
import com.interivalle.DTO.CotizacionSemanaResponse;
import com.interivalle.DTO.CrearCotizacionRequest;
import com.interivalle.DTO.GenerarCotizacionBaseRequest;
import com.interivalle.DTO.MaterialAgrupadoResponse;
import com.interivalle.DTO.ObservacionRequest;
import com.interivalle.Modelo.ActividadMaterial;
import com.interivalle.Modelo.CatalogoItem;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.CotizacionCarpinteria;
import com.interivalle.Modelo.CotizacionDetalle;
import com.interivalle.Modelo.CotizacionHistorialEstado;
import com.interivalle.Modelo.CotizacionManoObra;
import com.interivalle.Modelo.CotizacionMezon;
import com.interivalle.Modelo.CotizacionObservacion;
import com.interivalle.Modelo.CotizacionVidrio;
import com.interivalle.Modelo.Servicios;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Modelo.SolicitudServicios;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Modelo.enums.EstadoCotizacion;
import com.interivalle.Modelo.enums.TipoCotizacion;
import com.interivalle.Modelo.enums.TipoItemCotizacion;
import com.interivalle.Modelo.enums.TipoObservacion;
import com.interivalle.Repositorio.ActividadMaterialRepositorio;
import com.interivalle.Repositorio.CatalogoItemRepositorio;
import com.interivalle.Repositorio.CotizacionCarpinteriaRepositorio;
import com.interivalle.Repositorio.CotizacionDetalleRepositorio;
import com.interivalle.Repositorio.CotizacionHistorialRepositorio;
import com.interivalle.Repositorio.CotizacionManoObraRepositorio;
import com.interivalle.Repositorio.CotizacionMezonRepositorio;
import com.interivalle.Repositorio.CotizacionObservacionRepositorio;
import com.interivalle.Repositorio.CotizacionRepositorio;
import com.interivalle.Repositorio.CotizacionVidrioRepositorio;
import com.interivalle.Repositorio.ServiciosRepositorio;
import com.interivalle.Repositorio.SolicitudRepositorio;
import com.interivalle.Repositorio.UsuarioRepositorio;
import com.interivalle.Servicio.CronogramaService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class CotizacionService {

    @Autowired private CotizacionRepositorio cotizacionRepo;
    @Autowired private CotizacionDetalleRepositorio detalleRepo;
    @Autowired private CotizacionObservacionRepositorio obsRepo;
    @Autowired private CotizacionHistorialRepositorio histRepo;
    @Autowired private CotizacionPersonalizadaService cotizacionPersonalizadaService;

    @Autowired private SolicitudRepositorio solicitudRepo;
    @Autowired private ServiciosRepositorio serviciosRepo;
    @Autowired private UsuarioRepositorio usuarioRepo;
    @Autowired private ActividadMaterialRepositorio actividadMaterialRepo;
    @Autowired private CatalogoItemRepositorio catalogoItemRepo;

    @Autowired private CotizacionManoObraRepositorio cotizacionManoObraRepo;
    @Autowired private CotizacionCarpinteriaRepositorio cotizacionCarpinteriaRepo;
    @Autowired private CotizacionVidrioRepositorio cotizacionVidrioRepo;
    @Autowired private CotizacionMezonRepositorio cotizacionMezonRepo;
    @Autowired private CronogramaService cronogramaServicio;

    // CREA COTIZACION MANUAL
    @Transactional
    public CotizacionResponse crearCotizacion(Integer idUsuario, CrearCotizacionRequest req) {

        Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Solicitud solicitud = solicitudRepo.findById(req.getSolicitudId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        if (!solicitud.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes cotizar una solicitud de otro usuario");
        }

        if (req.getDetalles() == null || req.getDetalles().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes enviar al menos 1 item en detalles");
        }

        Cotizacion cot = new Cotizacion();
        cot.setSolicitud(solicitud);
        cot.setTipo(req.getTipo());
        cot.setEstado(EstadoCotizacion.GENERADA);
        cot.setCreadaPor(usuario);

        cot = cotizacionRepo.save(cot);

        BigDecimal totalGeneral = BigDecimal.ZERO;
        BigDecimal totalManoObra = BigDecimal.ZERO;
        BigDecimal totalMateriales = BigDecimal.ZERO;
        BigDecimal totalProductos = BigDecimal.ZERO;

        for (CrearCotizacionRequest.DetalleItem item : req.getDetalles()) {

            Servicios servicio = serviciosRepo.findById(item.getServicioId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Servicio no encontrado: " + item.getServicioId()
                ));

            validarItem(item);

            BigDecimal subtotalVenta = item.getCantidad().multiply(item.getPrecioUnitario());

            CotizacionDetalle det = new CotizacionDetalle();
            det.setCotizacion(cot);
            det.setServicio(servicio);
            det.setTipoItem(item.getTipoItem());
            det.setCategoria(item.getCategoria());
            det.setSemana(item.getSemana());
            det.setDescripcion(item.getDescripcion());
            det.setActividadMaterial(item.getActividadMaterial());
            det.setCantidad(item.getCantidad());
            det.setPrecioUnitarioVenta(item.getPrecioUnitario());
            det.setSubtotalVenta(subtotalVenta);

            detalleRepo.save(det);

            totalGeneral = totalGeneral.add(subtotalVenta);

            if (item.getTipoItem() == TipoItemCotizacion.ACTIVIDAD) {
                totalManoObra = totalManoObra.add(subtotalVenta);
            } else if (item.getTipoItem() == TipoItemCotizacion.MATERIAL) {
                totalMateriales = totalMateriales.add(subtotalVenta);
            } else if (item.getTipoItem() == TipoItemCotizacion.PRODUCTO) {
                totalProductos = totalProductos.add(subtotalVenta);
            }
        }

        cot.setTotalManoObra(totalManoObra);
        cot.setTotalMateriales(totalMateriales);
        cot.setTotalProductos(totalProductos);
        cot.setTotalEstimado(totalGeneral);

        cot = cotizacionRepo.save(cot);

        guardarHistorial(cot, null, EstadoCotizacion.GENERADA, usuario);

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
    
    //LISTAR TODAS LAS COTIZACIONES
    public List<CotizacionResponse> listarTodas() {
        List<Cotizacion> lista = cotizacionRepo.findAll();
        List<CotizacionResponse> out = new ArrayList<>();

        for (Cotizacion c : lista) {
            out.add(toResponseBasico(c));
        }

        return out;
    }

    // DETALLE POR ID CLIENTE
    public CotizacionResponse verDetalle(Integer idUsuario, Integer idCotizacion) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);
        return toResponseCompleto(cot);
    }
    
    //DETALLE ADMIN Y SUPERVISOR
    public CotizacionResponse verDetalleAdminSupervisor(Integer idCotizacion) {
        Cotizacion cot = cotizacionRepo.findById(idCotizacion)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cotización no encontrada"
            ));

        return toResponseCompleto(cot);
    }

    // ENVIAR

    @Transactional
    public CotizacionResponse enviar(Integer idUsuario, Integer idCotizacion) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

        if (cot.getEstado() != EstadoCotizacion.GENERADA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo puedes enviar cotizaciones en GENERADA");
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
    public CotizacionResponse aprobar(Integer idUsuario, Integer idCotizacion, AprobarCotizacionRequest req) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

        if (cot.getEstado() != EstadoCotizacion.GENERADA && cot.getEstado() != EstadoCotizacion.EN_REVISION) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Solo puedes aprobar cotizaciones GENERADA o EN_REVISION"
            );
        }

        if (req == null || req.getFechaInicio() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "La fechaInicio es obligatoria para generar el cronograma"
            );
        }

        Usuario usuario = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        EstadoCotizacion anterior = cot.getEstado();

        cot.setEstado(EstadoCotizacion.APROBADA);
        cot.setFechaAprobacion(LocalDateTime.now());
        cot = cotizacionRepo.save(cot);

        guardarObservacion(cot, usuario, TipoObservacion.APROBACION, req.getMensaje());
        guardarHistorial(cot, anterior, EstadoCotizacion.APROBADA, usuario);

        cronogramaServicio.crearDesdeCotizacionAprobada(cot.getIdCotizacion(), req.getFechaInicio());

        return toResponseCompleto(cot);
    }

    // RECHAZAR
    @Transactional
    public CotizacionResponse rechazar(Integer idUsuario, Integer idCotizacion, ObservacionRequest req) {
        Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

        if (cot.getEstado() != EstadoCotizacion.GENERADA && cot.getEstado() != EstadoCotizacion.EN_REVISION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo puedes rechazar cotizaciones GENERADA o EN_REVISION");
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

    // GENERAR COTIZACION BASE DESDE SOLICITUD + GUARDAR FORMULARIOS
    @Transactional
    public CotizacionBaseResponse generarCotizacionBaseDesdeSolicitud(Integer idUsuario, GenerarCotizacionBaseRequest req) {

        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Solicitud solicitud = solicitudRepo.findById(req.getSolicitudId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

        if (!solicitud.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes generar cotización para otra solicitud");
        }

        if (!"COTIZACION_BASE".equalsIgnoreCase(solicitud.getTipoSolicitud())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La solicitud no corresponde a cotización base");
        }

        Set<Integer> idsServiciosSolicitud = solicitud.getServiciosSeleccionados()
                .stream()
                .map((SolicitudServicios item) -> item.getServicios().getIdServicio())
                .collect(Collectors.toSet());

        if (req.getManoObra() != null && !idsServiciosSolicitud.contains(1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud no contiene el servicio Mano de Obra");
        }

        if (req.getCarpinteria() != null && !idsServiciosSolicitud.contains(2)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud no contiene el servicio Carpintería");
        }

        if (req.getVidrio() != null && !idsServiciosSolicitud.contains(3)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud no contiene el servicio Divisiones en Vidrio");
        }

        if (req.getMezon() != null && !idsServiciosSolicitud.contains(4)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud no contiene el servicio Mesón Granito");
        }

        if (req.getManoObra() == null
                && req.getCarpinteria() == null
                && req.getVidrio() == null
                && req.getMezon() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar al menos una sección de cotización");
        }

        Cotizacion cot = new Cotizacion();
        cot.setSolicitud(solicitud);
        cot.setCreadaPor(usuario);
        cot.setTipo(TipoCotizacion.BASE);
        cot.setEstado(EstadoCotizacion.GENERADA);
        cot.setTotalManoObra(BigDecimal.ZERO);
        cot.setTotalMateriales(BigDecimal.ZERO);
        cot.setTotalProductos(BigDecimal.ZERO);
        cot.setTotalEstimado(BigDecimal.ZERO);

        cot = cotizacionRepo.save(cot);

        guardarHistorial(cot, null, EstadoCotizacion.GENERADA, usuario);

        if (req.getManoObra() != null) {
            CotizacionManoObra mano = new CotizacionManoObra();
            mano.setCotizacion(cot);
            mano.setMedidaAreaPrivada(req.getManoObra().getMedidaAreaPrivada());
            mano.setCantidadBanos(req.getManoObra().getCantidadBanos());
            mano.setTipoCielo(req.getManoObra().getTipoCielo());
            mano.setDivisionPared(req.getManoObra().getDivisionPared());
            cotizacionManoObraRepo.save(mano);
        }

        if (req.getCarpinteria() != null) {
            CotizacionCarpinteria carp = new CotizacionCarpinteria();
            carp.setCotizacion(cot);
            carp.setCantidadCloset(req.getCarpinteria().getCantidadCloset());
            carp.setCantidadPuertas(req.getCarpinteria().getCantidadPuertas());
            carp.setMuebleAltoCocina(req.getCarpinteria().getMuebleAltoCocina());
            carp.setMuebleBajoCocina(req.getCarpinteria().getMuebleBajoCocina());
            carp.setCantidadBanos(req.getCarpinteria().getCantidadBanos());
            cotizacionCarpinteriaRepo.save(carp);
        }

        if (req.getVidrio() != null) {
            CotizacionVidrio vidrio = new CotizacionVidrio();
            vidrio.setCotizacion(cot);
            vidrio.setCantidadBanos(req.getVidrio().getCantidadBanos());
            vidrio.setTipoApertura(req.getVidrio().getTipoApertura());
            vidrio.setColorAccesorios(req.getVidrio().getColorAccesorios());
            cotizacionVidrioRepo.save(vidrio);
        }

        if (req.getMezon() != null) {
            CotizacionMezon mezon = new CotizacionMezon();
            mezon.setCotizacion(cot);
            mezon.setMezonCocina(req.getMezon().getMezonCocina());
            mezon.setMezonBarra(req.getMezon().getMezonBarra());
            mezon.setMezonLavamanos(req.getMezon().getMezonLavamanos());
            cotizacionMezonRepo.save(mezon);
        }


        // GENERAR DETALLES DESDE CATALOGO + ACTIVIDAD_MATERIAL
        BigDecimal totalManoObra = BigDecimal.ZERO;
        BigDecimal totalMateriales = BigDecimal.ZERO;
        BigDecimal totalProductos = BigDecimal.ZERO;

        LocalDate hoy = LocalDate.now();

        if (req.getManoObra() != null) {
            System.out.println("=== ENTRANDO A GENERAR DETALLES MANO DE OBRA ===");

            List<CatalogoItem> actividadesManoObra = catalogoItemRepo.buscarVigentesPorServicioYTipo(
                    1,
                    TipoItemCotizacion.ACTIVIDAD,
                    hoy
            );

            System.out.println("Actividades encontradas: " + actividadesManoObra.size());

            for (CatalogoItem actividad : actividadesManoObra) {
                System.out.println("Actividad: " + actividad.getNombreItem());

                BigDecimal valorActividad = calcularValorActividad(actividad, req);
                System.out.println("Valor actividad: " + valorActividad);

                if (valorActividad.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }

                CotizacionDetalle detActividad = new CotizacionDetalle();
                detActividad.setCotizacion(cot);
                detActividad.setServicio(actividad.getServicio());
                detActividad.setTipoItem(TipoItemCotizacion.ACTIVIDAD);
                detActividad.setCategoria(actividad.getCategoria());
                detActividad.setSemana(actividad.getSemana());
               // detActividad.setDescripcion(actividad.getNombreItem());
                detActividad.setDescripcion(obtenerDescripcionCatalogo(actividad));
                detActividad.setActividadMaterial(actividad.getNombreItem());
                detActividad.setCantidad(BigDecimal.ONE);
                detActividad.setPrecioUnitarioVenta(valorActividad);
                detActividad.setSubtotalVenta(valorActividad);
                detActividad.setPrecioUnitarioProveedor(BigDecimal.ZERO);
                detActividad.setSubtotalProveedor(BigDecimal.ZERO);

                detalleRepo.save(detActividad);

                totalManoObra = totalManoObra.add(valorActividad);

                List<ActividadMaterial> materialesRelacionados =
                        actividadMaterialRepo.findByActividad_IdCatalogoItemAndActivoTrue(
                                actividad.getIdCatalogoItem()
                        );

                System.out.println("Materiales relacionados: " + materialesRelacionados.size());

                for (ActividadMaterial rel : materialesRelacionados) {
                    CatalogoItem material = rel.getMaterial();

                    if (material == null || material.getActivo() == null || !material.getActivo()) {
                        continue;
                    }

                    BigDecimal cantidadMaterial = calcularCantidadMaterial(rel, req);

                    if (cantidadMaterial.compareTo(BigDecimal.ZERO) <= 0) {
                        continue;
                    }

                    BigDecimal precioVenta = material.getPrecioUnitarioVenta() != null
                            ? material.getPrecioUnitarioVenta()
                            : BigDecimal.ZERO;

                    BigDecimal precioProveedor = material.getPrecioUnitarioProveedor() != null
                            ? material.getPrecioUnitarioProveedor()
                            : BigDecimal.ZERO;

                    BigDecimal subtotalVenta = cantidadMaterial.multiply(precioVenta);
                    BigDecimal subtotalProveedor = cantidadMaterial.multiply(precioProveedor);

                    CotizacionDetalle detMaterial = new CotizacionDetalle();
                    detMaterial.setCotizacion(cot);
                    detMaterial.setServicio(material.getServicio());
                    detMaterial.setTipoItem(TipoItemCotizacion.MATERIAL);
                    detMaterial.setCategoria(material.getCategoria());
                    detMaterial.setSemana(rel.getSemana() != null ? rel.getSemana() : actividad.getSemana());
                    //detMaterial.setDescripcion(material.getNombreItem());
                    detMaterial.setDescripcion(obtenerDescripcionCatalogo(material));
                    detMaterial.setActividadMaterial(actividad.getNombreItem());
                    detMaterial.setCantidad(cantidadMaterial);
                    detMaterial.setPrecioUnitarioVenta(precioVenta);
                    detMaterial.setSubtotalVenta(subtotalVenta);
                    detMaterial.setPrecioUnitarioProveedor(precioProveedor);
                    detMaterial.setSubtotalProveedor(subtotalProveedor);

                    detalleRepo.save(detMaterial);

                    totalMateriales = totalMateriales.add(subtotalVenta);
                }
            }
        }
        
        if (req.getCarpinteria() != null) {
        System.out.println("=== ENTRANDO A GENERAR DETALLES CARPINTERIA ===");

        List<CatalogoItem> productosCarpinteria = catalogoItemRepo.buscarVigentesPorServicioYTipo(
                2,
                TipoItemCotizacion.PRODUCTO,
                hoy
        );

        System.out.println("Productos carpintería encontrados: " + productosCarpinteria.size());

        for (CatalogoItem producto : productosCarpinteria) {
            BigDecimal cantidad = obtenerCantidadProductoCarpinteria(producto, req.getCarpinteria());

            if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            BigDecimal precioVenta = producto.getPrecioUnitarioVenta() != null
                    ? producto.getPrecioUnitarioVenta()
                    : BigDecimal.ZERO;

            BigDecimal precioProveedor = producto.getPrecioUnitarioProveedor() != null
                    ? producto.getPrecioUnitarioProveedor()
                    : BigDecimal.ZERO;

            BigDecimal subtotalVenta = cantidad.multiply(precioVenta);
            BigDecimal subtotalProveedor = cantidad.multiply(precioProveedor);

            CotizacionDetalle detProducto = new CotizacionDetalle();
            detProducto.setCotizacion(cot);
            detProducto.setServicio(producto.getServicio());
            detProducto.setTipoItem(TipoItemCotizacion.PRODUCTO);
            detProducto.setCategoria(producto.getCategoria());
            detProducto.setSemana(producto.getSemana());
            detProducto.setDescripcion(obtenerDescripcionCatalogo(producto));
            detProducto.setActividadMaterial(producto.getNombreItem());
            detProducto.setCantidad(cantidad);
            detProducto.setUnidad(producto.getUnidad());
            detProducto.setPrecioUnitarioVenta(precioVenta);
            detProducto.setSubtotalVenta(subtotalVenta);
            detProducto.setPrecioUnitarioProveedor(precioProveedor);
            detProducto.setSubtotalProveedor(subtotalProveedor);

            detalleRepo.save(detProducto);

            totalProductos = totalProductos.add(subtotalVenta);
        }
    }

        cot.setTotalManoObra(totalManoObra);
        cot.setTotalMateriales(totalMateriales);
        cot.setTotalProductos(totalProductos);
        cot.setTotalEstimado(totalManoObra.add(totalMateriales).add(totalProductos));

        cotizacionRepo.save(cot);

        CotizacionBaseResponse resp = new CotizacionBaseResponse();
        resp.setSolicitudId(solicitud.getIdSolicitud());
        resp.setIdCotizacion(cot.getIdCotizacion());
        resp.setMensaje("Cotización base guardada correctamente");
        resp.setManoObraProcesada(req.getManoObra() != null);
        resp.setCarpinteriaProcesada(req.getCarpinteria() != null);
        resp.setVidrioProcesado(req.getVidrio() != null);
        resp.setMezonProcesado(req.getMezon() != null);

        return resp;
    }
    
    private String obtenerDescripcionCatalogo(CatalogoItem item) {
    if (item == null) {
        return "";
    }

    if (item.getDescripcion() != null && !item.getDescripcion().trim().isEmpty()) {
        return item.getDescripcion().trim();
    }

    if (item.getNombreItem() != null) {
        return item.getNombreItem().trim();
    }

    return "";
}
    
    private BigDecimal obtenerAreaTotalDesdeParams(String paramsJson) {
    try {
        if (paramsJson == null || paramsJson.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(paramsJson);

        if (root.has("areaTotal") && !root.get("areaTotal").isNull()) {
            return root.get("areaTotal").decimalValue();
        }

        BigDecimal areaPiso = root.has("areaPiso") && !root.get("areaPiso").isNull()
                ? root.get("areaPiso").decimalValue()
                : BigDecimal.ZERO;

        BigDecimal areaPared = root.has("areaPared") && !root.get("areaPared").isNull()
                ? root.get("areaPared").decimalValue()
                : BigDecimal.ZERO;

        return areaPiso.add(areaPared);

    } catch (Exception e) {
        return BigDecimal.ZERO;
    }
}
    
private Integer obtenerCantidadSegunActividad(CatalogoItem actividad, GenerarCotizacionBaseRequest req) {
    if (actividad == null || actividad.getNombreItem() == null || req.getManoObra() == null) {
        return null;
    }

    String nombre = actividad.getNombreItem().toLowerCase().trim(); //minuscula

    if (nombre.contains("poyo")) {
        return req.getManoObra().getCantidadPoyos();
    }

    if (nombre.contains("centrar luces") ||  nombre.contains("punto electrico") || nombre.contains("puntos electricos")) {
        return req.getManoObra().getCantidadPuntosElectricos();
    }

    return null;
}

private BigDecimal obtenerMetrosCuadradosSegunActividad(CatalogoItem actividad, GenerarCotizacionBaseRequest req) {
    if (actividad == null || actividad.getNombreItem() == null || req.getManoObra() == null) {
        return null;
    }

    String nombre = actividad.getNombreItem().toLowerCase().trim();

    if (nombre.contains("muro en drywall") || nombre.contains("muro") ){
        return req.getManoObra().getMetrosCuadradosMuro();
    }

    if (nombre.contains("drywall en cielo") || nombre.contains("cielo")) {
        return req.getManoObra().getMetrosCuadradosCielo();
    }

    if (nombre.contains("tapar tuberias") || nombre.contains("tapar tuberías")) {
        return req.getManoObra().getMetrosCuadradosTaparTuberias();
    }

    if (nombre.contains("panel yeso")) {
        return req.getManoObra().getMetrosCuadradosPanelYeso();
    }

    return null;
}

private BigDecimal calcularValorActividad(CatalogoItem actividad, GenerarCotizacionBaseRequest req) {
    String formula = actividad.getFormulaCode() == null ? "" : actividad.getFormulaCode().trim();

    BigDecimal precio = actividad.getPrecioUnitarioVenta() != null
            ? actividad.getPrecioUnitarioVenta()
            : BigDecimal.ZERO;

    BigDecimal factor = actividad.getFactor() != null
            ? actividad.getFactor()
            : BigDecimal.ONE;

    switch (formula) {
        case "FIJO":
            return precio;

        case "AREA_PRIVADA_X_FACTOR":
            if (req.getManoObra() == null || req.getManoObra().getMedidaAreaPrivada() == null) {
                return BigDecimal.ZERO;
            }
            return precio.multiply(
                    factor.multiply(BigDecimal.valueOf(req.getManoObra().getMedidaAreaPrivada()))
            );

        case "AREA_PRIVADA_X_PRECIO":
            if (req.getManoObra() == null || req.getManoObra().getMedidaAreaPrivada() == null) {
                return BigDecimal.ZERO;
            }
            return precio.multiply(
                    BigDecimal.valueOf(req.getManoObra().getMedidaAreaPrivada())
            );

        case "METRO_CUADRADO_X_PRECIO":
            BigDecimal metros2 = obtenerMetrosCuadradosSegunActividad(actividad, req);
            if (metros2 == null || metros2.compareTo(BigDecimal.ZERO) <= 0) {
                return BigDecimal.ZERO;
            }
            System.out.println("Metros cuadrados: " + metros2);
            return precio.multiply(metros2);

        case "CANTIDAD_X_PRECIO":
            Integer cantidad = obtenerCantidadSegunActividad(actividad, req);
            if (cantidad == null || cantidad <= 0) {
                return BigDecimal.ZERO;
            }
            return precio.multiply(BigDecimal.valueOf(cantidad));

        case "AREA_FIJA_X_PRECIO":
            BigDecimal areaFija = obtenerAreaTotalDesdeParams(actividad.getParamsJson());
            if (areaFija == null || areaFija.compareTo(BigDecimal.ZERO) <= 0) {
                return BigDecimal.ZERO;
            }
            return precio.multiply(areaFija);

        default:
            return BigDecimal.ZERO;
    }
}
        
        
        private BigDecimal calcularCantidadMaterial(ActividadMaterial rel, GenerarCotizacionBaseRequest req) {
            if (rel.getCantidad() == null) {
                return BigDecimal.ZERO;
            }
            return rel.getCantidad();
        }

    // HELPERS DE NEGOCIO + VALIDACION
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
        h.setEstadoAnterior(anterior == null ? EstadoCotizacion.GENERADA : anterior);
        h.setEstadoNuevo(nuevo);
        h.setCambiadoPor(usuario);
        histRepo.save(h);
    }


    // MAPPERS -> RESPONSE DTOs
    private CotizacionResponse toResponseBasico(Cotizacion cot) {
        CotizacionResponse r = new CotizacionResponse();
        r.setIdCotizacion(cot.getIdCotizacion());

        r.setSolicitudId(cot.getSolicitud().getIdSolicitud());
        r.setNombreProyecto(cot.getSolicitud().getNombreProyectoUsuario());
        r.setNombreUsuario(cot.getSolicitud().getUsuario().getNombreUsuario());

        r.setTipo(cot.getTipo());
        r.setEstado(cot.getEstado());

        r.setTotalManoObra(cot.getTotalManoObra());
        r.setTotalMateriales(cot.getTotalMateriales());
        r.setTotalProductos(cot.getTotalProductos());
        r.setTotalEstimado(cot.getTotalEstimado());

        r.setFechaCreacion(cot.getFechaCreacion());
        r.setFechaActualizacion(cot.getFechaActualizacion());

        r.setDetalles(null);
        r.setSemanas(null);
        r.setActividades(null);
        r.setObservaciones(null);
        r.setHistorial(null);

        return r;
    }

    private CotizacionResponse toResponseCompleto(Cotizacion cot) {
    CotizacionResponse r = toResponseBasico(cot);

    List<CotizacionDetalle> detalles = detalleRepo.findByCotizacion_IdCotizacion(cot.getIdCotizacion());
    detalles.sort(Comparator
        .comparing((CotizacionDetalle d) -> d.getServicio().getIdServicio())
        .thenComparing(d -> d.getTipoItem().name())
        .thenComparing(d -> d.getCategoria() == null ? "" : d.getCategoria())
        .thenComparing(d -> d.getSemana() == null ? 0 : d.getSemana())
        .thenComparing(d -> d.getActividadMaterial() == null ? "" : d.getActividadMaterial())
        .thenComparing(d -> d.getDescripcion() == null ? "" : d.getDescripcion())
    );

    List<CotizacionDetalleResponse> detResp = detalles.stream()
        .map(this::toDetalleResponse)
        .collect(Collectors.toList());

    r.setDetalles(detResp);
    r.setSemanas(agruparPorSemanas(detResp));

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

    private CotizacionDetalleResponse toDetalleResponse(CotizacionDetalle d) {
        CotizacionDetalleResponse dr = new CotizacionDetalleResponse();

        dr.setIdDetalle(d.getIdDetalle());
        dr.setServicioId(d.getServicio().getIdServicio());
        dr.setNombreServicio(d.getServicio().getNombreServicio());

        dr.setTipoItem(d.getTipoItem());
        dr.setCategoria(d.getCategoria());
        dr.setSemana(d.getSemana());
        dr.setDescripcion(d.getDescripcion());
        dr.setActividadMaterial(d.getActividadMaterial());

        dr.setCantidad(d.getCantidad());
        dr.setUnidad(d.getUnidad());
        dr.setPrecioUnitarioVenta(d.getPrecioUnitarioVenta());
        dr.setSubtotalVenta(d.getSubtotalVenta());
        dr.setPrecioUnitarioProveedor(d.getPrecioUnitarioProveedor());
        dr.setSubtotalProveedor(d.getSubtotalProveedor());

        return dr;
    }

private List<CotizacionSemanaResponse> agruparPorSemanas(List<CotizacionDetalleResponse> detalles) {
    Map<Integer, List<CotizacionDetalleResponse>> agrupado = detalles.stream()
        .collect(Collectors.groupingBy(d -> d.getSemana() == null ? 0 : d.getSemana()));

    return agrupado.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(entry -> {
            Integer semana = entry.getKey();
            List<CotizacionDetalleResponse> items = entry.getValue();

            BigDecimal totalManoObra = sumarPorTipo(items, TipoItemCotizacion.ACTIVIDAD);
            BigDecimal totalMateriales = sumarPorTipo(items, TipoItemCotizacion.MATERIAL);
            BigDecimal totalProductos = sumarPorTipo(items, TipoItemCotizacion.PRODUCTO);

            CotizacionSemanaResponse s = new CotizacionSemanaResponse();
            s.setSemana(semana);
            s.setActividades(agruparActividadesConMateriales(items));
            s.setTotalManoObra(totalManoObra);
            s.setTotalMateriales(totalMateriales);
            s.setTotalProductos(totalProductos);
            s.setTotalSemana(totalManoObra);

            return s;
        })
        .collect(Collectors.toList());
}
    
    private String normalizarTexto(String texto) {
    return texto == null ? "" : texto.trim().toLowerCase();
    }
    
    private List<ActividadAgrupadaResponse> agruparActividadesConMateriales(List<CotizacionDetalleResponse> items) {

    List<CotizacionDetalleResponse> actividades = items.stream()
        .filter(i -> i.getTipoItem() == TipoItemCotizacion.ACTIVIDAD)
        .collect(Collectors.toList());

    List<CotizacionDetalleResponse> materiales = items.stream()
        .filter(i -> i.getTipoItem() == TipoItemCotizacion.MATERIAL)
        .collect(Collectors.toList());

    List<ActividadAgrupadaResponse> resultado = new ArrayList<>();

    for (CotizacionDetalleResponse act : actividades) {
        ActividadAgrupadaResponse actividad = new ActividadAgrupadaResponse();
        actividad.setActividad( act.getActividadMaterial() != null && !act.getActividadMaterial().trim().isEmpty()
        ? act.getActividadMaterial()
        : act.getDescripcion());
        
        actividad.setPrecioActividad(
            act.getSubtotalVenta() != null ? act.getSubtotalVenta() : BigDecimal.ZERO
        );

        String nombreActividad = normalizarTexto(
            act.getActividadMaterial() != null ? act.getActividadMaterial() : act.getDescripcion()
        );

        List<MaterialAgrupadoResponse> mats = materiales.stream()
            .filter(mat -> {
                String relacionMaterial = normalizarTexto(mat.getActividadMaterial());
                return relacionMaterial.equals(nombreActividad);
            })
            .map(mat -> {
                MaterialAgrupadoResponse m = new MaterialAgrupadoResponse();
                m.setIdDetalle(mat.getIdDetalle());
                m.setCantidad(mat.getCantidad());
                m.setMaterial(mat.getDescripcion());
                m.setPrecioMaterial(
                    mat.getSubtotalVenta() != null ? mat.getSubtotalVenta() : BigDecimal.ZERO
                );
                return m;
            })
            .collect(Collectors.toList());

        actividad.setMateriales(mats);
        resultado.add(actividad);
    }

    return resultado;
}

    private BigDecimal sumarPorTipo(List<CotizacionDetalleResponse> items, TipoItemCotizacion tipo) {
        return items.stream()
            .filter(i -> i.getTipoItem() == tipo)
            .map(i -> i.getSubtotalVenta() == null ? BigDecimal.ZERO : i.getSubtotalVenta())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public CotizacionVistaCompletaResponse obtenerVistaCompleta(Integer idUsuario, Integer idCotizacion) {
    Cotizacion cot = getCotizacionDelUsuario(idUsuario, idCotizacion);

    List<CotizacionDetalle> detalles = detalleRepo.findByCotizacion_IdCotizacion(cot.getIdCotizacion());
    detalles.sort(Comparator
        .comparing((CotizacionDetalle d) -> d.getServicio().getIdServicio())
        .thenComparing(d -> d.getTipoItem().name())
        .thenComparing(d -> d.getCategoria() == null ? "" : d.getCategoria())
        .thenComparing(d -> d.getSemana() == null ? 0 : d.getSemana())
        .thenComparing(d -> d.getActividadMaterial() == null ? "" : d.getActividadMaterial())
        .thenComparing(d -> d.getDescripcion() == null ? "" : d.getDescripcion())
    );

    List<CotizacionDetalleResponse> detalleBase = detalles.stream()
        .map(this::toDetalleResponse)
        .collect(Collectors.toList());

    CotizacionPersonalizadaDetalleResponse personalizada = null;
    BigDecimal totalAdicionales = BigDecimal.ZERO;

    try {
       // personalizada = cotizacionPersonalizadaService.obtenerDetalle(idCotizacion);
        personalizada = cotizacionPersonalizadaService.obtenerDetallePorCotizacion(idCotizacion);

        if (personalizada != null && personalizada.getTotal() != null) {
            totalAdicionales = personalizada.getTotal();
        }
    } catch (Exception e) {
        personalizada = null;
        totalAdicionales = BigDecimal.ZERO;
       // e.printStackTrace();
     throw e;
    }

    BigDecimal totalBase = cot.getTotalEstimado() != null
        ? cot.getTotalEstimado()
        : BigDecimal.ZERO;

    BigDecimal totalGeneral = totalBase.add(totalAdicionales);

    CotizacionVistaCompletaResponse resp = new CotizacionVistaCompletaResponse();
    resp.setIdCotizacion(cot.getIdCotizacion());
    resp.setNombreProyecto(cot.getSolicitud().getNombreProyectoUsuario());
    resp.setEstado(cot.getEstado().name());

    resp.setTotalManoObra(cot.getTotalManoObra() != null ? cot.getTotalManoObra() : BigDecimal.ZERO);
    resp.setTotalMateriales(cot.getTotalMateriales() != null ? cot.getTotalMateriales() : BigDecimal.ZERO);
    resp.setTotalProductos(cot.getTotalProductos() != null ? cot.getTotalProductos() : BigDecimal.ZERO);
    resp.setTotalEstimadoBase(totalBase);

    resp.setTotalAdicionales(totalAdicionales);
    resp.setTotalGeneral(totalGeneral);

    resp.setDetalleBase(detalleBase);
    resp.setSemanas(agruparPorSemanas(detalleBase));
    resp.setPersonalizada(personalizada);

    return resp;
    }
    
    // VISTA COMPLETA PARA DMIN Y SUPERVISOR
    public CotizacionVistaCompletaResponse obtenerVistaCompletaAdminSupervisor(Integer idCotizacion) {
        Cotizacion cot = cotizacionRepo.findById(idCotizacion)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cotización no encontrada"
            ));

        List<CotizacionDetalle> detalles = detalleRepo.findByCotizacion_IdCotizacion(cot.getIdCotizacion());
        detalles.sort(Comparator
            .comparing((CotizacionDetalle d) -> d.getServicio().getIdServicio())
            .thenComparing(d -> d.getTipoItem().name())
            .thenComparing(d -> d.getCategoria() == null ? "" : d.getCategoria())
            .thenComparing(d -> d.getSemana() == null ? 0 : d.getSemana())
            .thenComparing(d -> d.getActividadMaterial() == null ? "" : d.getActividadMaterial())
            .thenComparing(d -> d.getDescripcion() == null ? "" : d.getDescripcion())
        );

        List<CotizacionDetalleResponse> detalleBase = detalles.stream()
            .map(this::toDetalleResponse)
            .collect(Collectors.toList());

        CotizacionPersonalizadaDetalleResponse personalizada = null;
        BigDecimal totalAdicionales = BigDecimal.ZERO;

        try {
            personalizada = cotizacionPersonalizadaService.obtenerDetallePorCotizacion(idCotizacion);

            if (personalizada != null && personalizada.getTotal() != null) {
                totalAdicionales = personalizada.getTotal();
            }
        } catch (Exception e) {
            personalizada = null;
            totalAdicionales = BigDecimal.ZERO;
            throw e;
        }

        BigDecimal totalBase = cot.getTotalEstimado() != null
            ? cot.getTotalEstimado()
            : BigDecimal.ZERO;

        BigDecimal totalGeneral = totalBase.add(totalAdicionales);

        CotizacionVistaCompletaResponse resp = new CotizacionVistaCompletaResponse();
        resp.setIdCotizacion(cot.getIdCotizacion());
        resp.setNombreProyecto(cot.getSolicitud().getNombreProyectoUsuario());
        resp.setEstado(cot.getEstado().name());

        resp.setTotalManoObra(cot.getTotalManoObra() != null ? cot.getTotalManoObra() : BigDecimal.ZERO);
        resp.setTotalMateriales(cot.getTotalMateriales() != null ? cot.getTotalMateriales() : BigDecimal.ZERO);
        resp.setTotalProductos(cot.getTotalProductos() != null ? cot.getTotalProductos() : BigDecimal.ZERO);
        resp.setTotalEstimadoBase(totalBase);

        resp.setTotalAdicionales(totalAdicionales);
        resp.setTotalGeneral(totalGeneral);

        resp.setDetalleBase(detalleBase);
        resp.setSemanas(agruparPorSemanas(detalleBase));
        resp.setPersonalizada(personalizada);

        return resp;
    }
    
    ///VALIDAR COTIZACION ANTES DE EDITAR    
    private void validarCotizacionEditable(Cotizacion cotizacion) {
        if (cotizacion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada");
        }

        if (cotizacion.getEstado() == EstadoCotizacion.APROBADA ||
            cotizacion.getEstado() == EstadoCotizacion.RECHAZADA) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "La cotización no se puede modificar porque está en estado " + cotizacion.getEstado().name()
            );
        }
    }
    
        private BigDecimal obtenerCantidadProductoCarpinteria(CatalogoItem producto, CarpinteriaBaseRequest req) {
        if (producto == null || producto.getNombreItem() == null || req == null) {
            return BigDecimal.ZERO;
        }

        String nombre = producto.getNombreItem().toLowerCase().trim();

        if (nombre.equals("closet") || nombre.contains("closet")) {
            return req.getCantidadCloset() == null
                    ? BigDecimal.ZERO
                    : BigDecimal.valueOf(req.getCantidadCloset());
        }

        if (nombre.contains("puerta maciza lisa") || nombre.contains("puerta")) {
            return req.getCantidadPuertas() == null
                    ? BigDecimal.ZERO
                    : BigDecimal.valueOf(req.getCantidadPuertas());
        }

        if (nombre.contains("mueble alto de cocina")) {
            return req.getMuebleAltoCocina() == null
                    ? BigDecimal.ZERO
                    : req.getMuebleAltoCocina();
        }

        if (nombre.contains("mueble bajo de cocina")) {
            return req.getMuebleBajoCocina() == null
                    ? BigDecimal.ZERO
                    : req.getMuebleBajoCocina();
        }

        // solución rápida: un solo campo del formulario alimenta ambos muebles de baño
       if (nombre.contains("mueble bajo baño") || nombre.contains("mueble bajo bano")) {
        return req.getCantidadBanos() == null
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(req.getCantidadBanos());
        }

        if (nombre.contains("mueble alto para baño") || nombre.contains("mueble alto para bano")) {
            return req.getCantidadBanos() == null
                    ? BigDecimal.ZERO
                    : BigDecimal.valueOf(req.getCantidadBanos());
        }

        // todavía no existe en tu formulario actual
        if (nombre.contains("mueble barra")) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.ZERO;
    }
    
}
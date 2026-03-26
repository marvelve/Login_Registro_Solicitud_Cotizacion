/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.CotizacionPersonalizadaDetalleResponse;
import com.interivalle.DTO.CotizacionPersonalizadaRequest;
import com.interivalle.DTO.CotizacionPersonalizadaResponse;
import com.interivalle.DTO.ObraBlancaResponse;
import com.interivalle.DTO.CarpinteriaResponse;
import com.interivalle.DTO.VidrioResponse;
import com.interivalle.DTO.MesonGranitoResponse;
import com.interivalle.Modelo.Carpinteria;
import com.interivalle.Modelo.Cotizacion;
import com.interivalle.Modelo.CotizacionPersonalizada;
import com.interivalle.Modelo.MesonGranito;
import com.interivalle.Modelo.ObraBlanca;
import com.interivalle.Modelo.Solicitud;
import com.interivalle.Modelo.Vidrio;
import com.interivalle.Repositorio.CarpinteriaRepositorio;
import com.interivalle.Repositorio.CotizacionPersonalizadaRepositorio;
import com.interivalle.Repositorio.CotizacionRepositorio;
import com.interivalle.Repositorio.MesonGranitoRepositorio;
import com.interivalle.Repositorio.ObraBlancaRepositorio;
import com.interivalle.Repositorio.SolicitudRepositorio;
import com.interivalle.Repositorio.VidrioRepositorio;
import java.math.BigDecimal;
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
public class CotizacionPersonalizadaService {

    @Autowired
    private CotizacionPersonalizadaRepositorio cotizacionRepo;

    @Autowired
    private SolicitudRepositorio solicitudRepo;
    
    @Autowired
    private CotizacionRepositorio cotizacionBaseRepo;

    @Autowired
    private ObraBlancaRepositorio obraBlancaRepo;

    @Autowired
    private CarpinteriaRepositorio carpinteriaRepo;

    @Autowired
    private VidrioRepositorio vidrioRepo;

    @Autowired
    private MesonGranitoRepositorio mesonGranitoRepo;

    // CREAR COTIZACION PRINCIPAL
public CotizacionPersonalizadaResponse crear(CotizacionPersonalizadaRequest req) {

    if (req.getIdSolicitud() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idSolicitud es obligatorio");
    }

    if (req.getIdCotizacion() == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idCotizacion es obligatorio");
    }

    Solicitud solicitud = solicitudRepo.findById(req.getIdSolicitud())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));

    Cotizacion cotizacionBase = cotizacionBaseRepo.findById(req.getIdCotizacion())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización base no encontrada"));

    CotizacionPersonalizada cotizacion = new CotizacionPersonalizada();
    cotizacion.setSolicitud(solicitud);
    cotizacion.setCotizacion(cotizacionBase); // ESTA LINEA ES LA CLAVE
    cotizacion.setUsuario(solicitud.getUsuario());

    if (req.getNombreProyecto() != null && !req.getNombreProyecto().trim().isEmpty()) {
        cotizacion.setNombreProyecto(req.getNombreProyecto());
    } else {
        cotizacion.setNombreProyecto(solicitud.getNombreProyectoUsuario());
    }

    cotizacion.setFechaCotizacion(LocalDate.now());
    cotizacion.setEstado("PENDIENTE");
    cotizacion.setSubtotal(BigDecimal.ZERO);
    cotizacion.setTotal(BigDecimal.ZERO);
    cotizacion.setObservacionGeneral(req.getObservacionGeneral());

    CotizacionPersonalizada guardada = cotizacionRepo.save(cotizacion);
    return toResponse(guardada);
}

    // LISTAR TODAS
    public List<CotizacionPersonalizadaResponse> listarTodas() {
        return cotizacionRepo.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public CotizacionPersonalizadaResponse obtenerPorId(Integer id) {
        CotizacionPersonalizada cotizacion = cotizacionRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        return toResponse(cotizacion);
    }

    // LISTAR POR USUARIO
    public List<CotizacionPersonalizadaResponse> listarPorUsuario(Integer idUsuario) {
        return cotizacionRepo.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // CAMBIAR ESTADO
    public CotizacionPersonalizadaResponse cambiarEstado(Integer id, String estado) {
        CotizacionPersonalizada cotizacion = cotizacionRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        if (estado == null || estado.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }

        String nuevoEstado = estado.trim().toUpperCase();

        if (!nuevoEstado.equals("PENDIENTE")
           && !nuevoEstado.equals("BORRADOR")
           && !nuevoEstado.equals("GENERADA")
           && !nuevoEstado.equals("APROBADA")
           && !nuevoEstado.equals("RECHAZADA")) {
       throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
               "Estado no válido. Use: PENDIENTE, BORRADOR, GENERADA, APROBADA o RECHAZADA");
            }

        cotizacion.setEstado(nuevoEstado);
        CotizacionPersonalizada actualizada = cotizacionRepo.save(cotizacion);

        return toResponse(actualizada);
    }

    // RECALCULAR TOTAL
    public CotizacionPersonalizadaResponse recalcular(Integer idCotizacion) {
        CotizacionPersonalizada cotizacion = cotizacionRepo.findById(idCotizacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        BigDecimal totalObraBlanca = sumarObraBlanca(idCotizacion);
        BigDecimal totalCarpinteria = sumarCarpinteria(idCotizacion);
        BigDecimal totalVidrio = sumarVidrio(idCotizacion);
        BigDecimal totalMeson = sumarMeson(idCotizacion);

        BigDecimal subtotal = totalObraBlanca
                .add(totalCarpinteria)
                .add(totalVidrio)
                .add(totalMeson);

        cotizacion.setSubtotal(subtotal);
        cotizacion.setTotal(subtotal);

        if ("BORRADOR".equalsIgnoreCase(cotizacion.getEstado()) && subtotal.compareTo(BigDecimal.ZERO) > 0) {
            cotizacion.setEstado("GENERADA");
        }

        CotizacionPersonalizada actualizada = cotizacionRepo.save(cotizacion);
        return toResponse(actualizada);
    }

    // =========================
    // METODOS PRIVADOS DE SUMA
    // =========================
    private BigDecimal sumarObraBlanca(Integer idCotizacion) {
        List<ObraBlanca> items = obraBlancaRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacion);

        BigDecimal total = BigDecimal.ZERO;

        for (ObraBlanca item : items) {
            if (item.getSubtotal() != null) {
                total = total.add(item.getSubtotal());
            }
        }

        return total;
    }

    private BigDecimal sumarCarpinteria(Integer idCotizacion) {
        List<Carpinteria> items = carpinteriaRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacion);

        BigDecimal total = BigDecimal.ZERO;

        for (Carpinteria item : items) {
            if (item.getSubtotal() != null) {
                total = total.add(item.getSubtotal());
            }
        }

        return total;
    }

    private BigDecimal sumarVidrio(Integer idCotizacion) {
        List<Vidrio> items = vidrioRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacion);

        BigDecimal total = BigDecimal.ZERO;

        for (Vidrio item : items) {
            if (item.getSubtotal() != null) {
                total = total.add(item.getSubtotal());
            }
        }

        return total;
    }

    private BigDecimal sumarMeson(Integer idCotizacion) {
        List<MesonGranito> items = mesonGranitoRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacion);

        BigDecimal total = BigDecimal.ZERO;

        for (MesonGranito item : items) {
            if (item.getSubtotal() != null) {
                total = total.add(item.getSubtotal());
            }
        }

        return total;
    }

    // =========================
    // CONVERTIR A RESPONSE
    // =========================
    public CotizacionPersonalizadaResponse toResponse(CotizacionPersonalizada cotizacion) {
        CotizacionPersonalizadaResponse dto = new CotizacionPersonalizadaResponse();
        dto.setIdCotizacionPersonalizada(cotizacion.getIdCotizacionPersonalizada());
        dto.setIdSolicitud(cotizacion.getSolicitud().getIdSolicitud());
        dto.setNombreProyecto(cotizacion.getNombreProyecto());
        dto.setFechaCotizacion(cotizacion.getFechaCotizacion());
        dto.setEstado(cotizacion.getEstado());
        dto.setSubtotal(cotizacion.getSubtotal());
        dto.setTotal(cotizacion.getTotal());
        dto.setObservacionGeneral(cotizacion.getObservacionGeneral());
        return dto;
    }
    
    public CotizacionPersonalizadaDetalleResponse obtenerDetalle(Integer idCotizacionPersonalizada) {
    CotizacionPersonalizada cotizacion = cotizacionRepo.findById(idCotizacionPersonalizada)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

    List<ObraBlanca> listaObraBlanca =
            obraBlancaRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacionPersonalizada);

    List<Carpinteria> listaCarpinteria =
            carpinteriaRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacionPersonalizada);

    List<Vidrio> listaVidrio =
            vidrioRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacionPersonalizada);

    List<MesonGranito> listaMeson =
            mesonGranitoRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacionPersonalizada);

    CotizacionPersonalizadaDetalleResponse dto = new CotizacionPersonalizadaDetalleResponse();
    dto.setIdCotizacionPersonalizada(cotizacion.getIdCotizacionPersonalizada());

    if (cotizacion.getCotizacion() != null) {
        dto.setIdCotizacion(cotizacion.getCotizacion().getIdCotizacion());
    }

    dto.setIdSolicitud(cotizacion.getSolicitud().getIdSolicitud());
    dto.setIdUsuario(cotizacion.getUsuario().getIdUsuario());
    dto.setNombreProyecto(cotizacion.getNombreProyecto());
    dto.setFechaCotizacion(cotizacion.getFechaCotizacion());
    dto.setEstado(cotizacion.getEstado());
    dto.setSubtotal(cotizacion.getSubtotal());
    dto.setTotal(cotizacion.getTotal());
    dto.setObservacionGeneral(cotizacion.getObservacionGeneral());

    dto.setObraBlanca(listaObraBlanca.stream().map(this::toObraBlancaResponse).collect(Collectors.toList()));
    dto.setCarpinteria(listaCarpinteria.stream().map(this::toCarpinteriaResponse).collect(Collectors.toList()));
    dto.setVidrio(listaVidrio.stream().map(this::toVidrioResponse).collect(Collectors.toList()));
    dto.setMesonGranito(listaMeson.stream().map(this::toMesonGranitoResponse).collect(Collectors.toList()));

    return dto;
}
    
    private ObraBlancaResponse toObraBlancaResponse(ObraBlanca item) {
    ObraBlancaResponse dto = new ObraBlancaResponse();
    dto.setIdObraBlanca(item.getIdObraBlanca());
    dto.setActividad(item.getActividad());
    dto.setLugar(item.getLugar());
    dto.setUnidad(item.getUnidad());
    dto.setCantidad(item.getCantidad());
    dto.setSemanas(item.getSemanas());
    dto.setPrecioUnitario(item.getPrecioUnitario());
    dto.setMedida(item.getMedida());
    dto.setDescripcion(item.getDescripcion());
    dto.setSubtotal(item.getSubtotal());
    return dto;
}

private CarpinteriaResponse toCarpinteriaResponse(Carpinteria item) {
    CarpinteriaResponse dto = new CarpinteriaResponse();
    dto.setIdCarpinteria(item.getIdCarpinteria());
    dto.setTipoMueble(item.getTipoMueble());
    dto.setMaterial(item.getMaterial());
    dto.setLargo(item.getLargo());
    dto.setAncho(item.getAncho());
    dto.setAlto(item.getAlto());
    dto.setCantidad(item.getCantidad());
    dto.setPrecioUnitario(item.getPrecioUnitario());
    dto.setDescripcion(item.getDescripcion());
    dto.setSubtotal(item.getSubtotal());
    return dto;
}

private VidrioResponse toVidrioResponse(Vidrio item) {
    VidrioResponse dto = new VidrioResponse();
    dto.setIdVidrio(item.getIdVidrio());
    dto.setTipoVidrio(item.getTipoVidrio());
    dto.setAncho(item.getAncho());
    dto.setAlto(item.getAlto());
    dto.setCantidad(item.getCantidad());
    dto.setInstalacion(item.getInstalacion());
    dto.setPrecioUnitario(item.getPrecioUnitario());
    dto.setDescripcion(item.getDescripcion());
    dto.setSubtotal(item.getSubtotal());
    return dto;
}

private MesonGranitoResponse toMesonGranitoResponse(MesonGranito item) {
    MesonGranitoResponse dto = new MesonGranitoResponse();
    dto.setIdMeson(item.getIdMeson());
    dto.setTipoGranito(item.getTipoGranito());
    dto.setLargo(item.getLargo());
    dto.setAncho(item.getAncho());
    dto.setEspesor(item.getEspesor());
    dto.setCantidad(item.getCantidad());
    dto.setPrecioUnitario(item.getPrecioUnitario());
    dto.setDescripcion(item.getDescripcion());
    dto.setSubtotal(item.getSubtotal());
    return dto;
    }

    public CotizacionPersonalizadaDetalleResponse obtenerDetallePorCotizacion(Integer idCotizacion) {
        CotizacionPersonalizada cotizacion = cotizacionRepo
                .findTopByCotizacion_IdCotizacionOrderByIdCotizacionPersonalizadaDesc(idCotizacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe cotización personalizada para la cotización base: " + idCotizacion
                ));

        CotizacionPersonalizadaDetalleResponse resp = new CotizacionPersonalizadaDetalleResponse();
        
        resp.setIdCotizacionPersonalizada(cotizacion.getIdCotizacionPersonalizada());        
        resp.setIdCotizacion(cotizacion.getCotizacion().getIdCotizacion());
        resp.setIdSolicitud(cotizacion.getSolicitud().getIdSolicitud());
        resp.setIdUsuario(cotizacion.getUsuario().getIdUsuario());
        resp.setNombreProyecto(cotizacion.getNombreProyecto());
        resp.setFechaCotizacion(cotizacion.getFechaCotizacion());
        resp.setEstado(cotizacion.getEstado());
        resp.setSubtotal(cotizacion.getSubtotal());
        resp.setTotal(cotizacion.getTotal());
        resp.setObservacionGeneral(cotizacion.getObservacionGeneral());

        resp.setObraBlanca(
                obraBlancaRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(
                        cotizacion.getIdCotizacionPersonalizada()
                ).stream().map(this::toObraBlancaResponse).toList()
        );

        resp.setCarpinteria(
                carpinteriaRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(cotizacion.getIdCotizacionPersonalizada())
                        .stream().map(this::toCarpinteriaResponse).toList()
        );

        resp.setVidrio(
                vidrioRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(cotizacion.getIdCotizacionPersonalizada())
                        .stream().map(this::toVidrioResponse).toList()
        );

        resp.setMesonGranito(
                mesonGranitoRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(cotizacion.getIdCotizacionPersonalizada())
                        .stream().map(this::toMesonGranitoResponse).toList()
        );

        return resp;
    }
}

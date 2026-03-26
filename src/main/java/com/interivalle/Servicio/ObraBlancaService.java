package com.interivalle.Servicio;

import com.interivalle.DTO.ObraBlancaRequest;
import com.interivalle.DTO.ObraBlancaResponse;
import com.interivalle.Modelo.CotizacionPersonalizada;
import com.interivalle.Modelo.ObraBlanca;
import com.interivalle.Repositorio.CotizacionPersonalizadaRepositorio;
import com.interivalle.Repositorio.ObraBlancaRepositorio;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ObraBlancaService {

    @Autowired
    private ObraBlancaRepositorio obraBlancaRepo;

    @Autowired
    private CotizacionPersonalizadaRepositorio cotizacionRepo;

    // GUARDAR
    public ObraBlancaResponse guardar(ObraBlancaRequest req) {
        if (req.getIdCotizacion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idCotizacion es obligatorio");
        }

        CotizacionPersonalizada cotizacion = cotizacionRepo
                .findTopByCotizacion_IdCotizacionOrderByIdCotizacionPersonalizadaDesc(req.getIdCotizacion())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cotización personalizada no encontrada para la cotización base: " + req.getIdCotizacion()
                ));

        ObraBlanca item = new ObraBlanca();
        item.setCotizacionPersonalizada(cotizacion);
        item.setActividad(req.getActividad());
        item.setLugar(req.getLugar());
        item.setUnidad(req.getUnidad());
        item.setCantidad(req.getCantidad());
        item.setSemanas(req.getSemanas());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setMedida(req.getMedida());
        item.setDescripcion(req.getDescripcion());
        item.setSubtotal(calcularSubtotal(req));

        ObraBlanca guardado = obraBlancaRepo.save(item);
        return toResponse(guardado);
    }

    // LISTAR POR COTIZACION BASE
    public List<ObraBlancaResponse> listarPorCotizacion(Integer idCotizacion) {
        return obraBlancaRepo
                .findByCotizacionPersonalizada_Cotizacion_IdCotizacion(idCotizacion)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public ObraBlancaResponse obtenerPorId(Integer id) {
        ObraBlanca item = obraBlancaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Item de obra blanca no encontrado"
                ));

        return toResponse(item);
    }

    // ACTUALIZAR
    public ObraBlancaResponse actualizar(Integer id, ObraBlancaRequest req) {
        ObraBlanca item = obraBlancaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Item de obra blanca no encontrado"
                ));

        if (req.getIdCotizacionPersonalizada() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacionPersonalizada())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cotización personalizada no encontrada"
                    ));
            item.setCotizacionPersonalizada(cotizacion);
        } else if (req.getIdCotizacion() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo
                    .findTopByCotizacion_IdCotizacionOrderByIdCotizacionPersonalizadaDesc(req.getIdCotizacion())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cotización personalizada no encontrada para la cotización base: " + req.getIdCotizacion()
                    ));
            item.setCotizacionPersonalizada(cotizacion);
        }

        item.setActividad(req.getActividad());
        item.setLugar(req.getLugar());
        item.setUnidad(req.getUnidad());
        item.setCantidad(req.getCantidad());
        item.setSemanas(req.getSemanas());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setMedida(req.getMedida());
        item.setDescripcion(req.getDescripcion());
        item.setSubtotal(calcularSubtotal(req));

        ObraBlanca actualizado = obraBlancaRepo.save(item);
        return toResponse(actualizado);
    }

    // ELIMINAR
    public void eliminar(Integer id) {
        ObraBlanca item = obraBlancaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Item de obra blanca no encontrado"
                ));

        obraBlancaRepo.delete(item);
    }

    // MAPPER ENTITY -> RESPONSE
    private ObraBlancaResponse toResponse(ObraBlanca item) {
        ObraBlancaResponse resp = new ObraBlancaResponse();

        resp.setIdObraBlanca(item.getIdObraBlanca());
        resp.setActividad(item.getActividad());
        resp.setLugar(item.getLugar());
        resp.setUnidad(item.getUnidad());
        resp.setCantidad(item.getCantidad());
        resp.setSemanas(item.getSemanas());
        resp.setPrecioUnitario(item.getPrecioUnitario());
        resp.setMedida(item.getMedida());
        resp.setSubtotal(item.getSubtotal());
        resp.setDescripcion(item.getDescripcion());

        if (item.getCotizacionPersonalizada() != null) {
            resp.setIdCotizacionPersonalizada(
                    item.getCotizacionPersonalizada().getIdCotizacionPersonalizada()
            );

            if (item.getCotizacionPersonalizada().getCotizacion() != null) {
                resp.setIdCotizacion(
                        item.getCotizacionPersonalizada().getCotizacion().getIdCotizacion()
                );
            }
        }

        return resp;
    }

    // CALCULAR SUBTOTAL
    private BigDecimal calcularSubtotal(ObraBlancaRequest req) {
        if (req.getPrecioUnitario() == null || req.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotalBase = BigDecimal.ZERO;

        if (req.getMedida() != null && req.getMedida().compareTo(BigDecimal.ZERO) > 0) {
            subtotalBase = req.getMedida().multiply(req.getPrecioUnitario());
        } else if (req.getCantidad() != null && req.getCantidad() > 0) {
            subtotalBase = req.getPrecioUnitario().multiply(BigDecimal.valueOf(req.getCantidad()));
        }

        if (req.getSemanas() != null && req.getSemanas() > 0) {
            subtotalBase = subtotalBase.multiply(BigDecimal.valueOf(req.getSemanas()));
        }

        return subtotalBase;
    }
}
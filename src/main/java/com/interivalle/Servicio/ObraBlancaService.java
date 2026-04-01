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

        if (req.getIdActividad() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idActividad es obligatorio");
        }

        if (req.getLugar() == null || req.getLugar().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lugar es obligatorio");
        }

        CotizacionPersonalizada cotizacion = cotizacionRepo
         .findByCotizacion_IdCotizacion(req.getIdCotizacion())
         .orElseThrow(() -> new ResponseStatusException(
                 HttpStatus.NOT_FOUND,
                 "Cotización personalizada no encontrada para la cotización base: " + req.getIdCotizacion()
         ));

        String lugarNormalizado = normalizarLugar(req.getLugar());

        boolean existe = obraBlancaRepo
                .existsByCotizacionPersonalizada_Cotizacion_IdCotizacionAndIdActividadAndLugarIgnoreCase(
                        req.getIdCotizacion(),
                        req.getIdActividad(),
                        lugarNormalizado
                );

        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta actividad ya está en la cotización para ese mismo lugar"
            );
        }

        ObraBlanca item = new ObraBlanca();
        item.setCotizacionPersonalizada(cotizacion);
        item.setIdActividad(req.getIdActividad());
        item.setActividad(req.getActividad());
        item.setLugar(lugarNormalizado);
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
                .findByCotizacionPersonalizada_Cotizacion_IdCotizacionOrderByIdObraBlancaAsc(idCotizacion)
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

        Integer idCotizacionBase = null;

        if (req.getIdCotizacionPersonalizada() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacionPersonalizada())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Cotización personalizada no encontrada"
                    ));
            item.setCotizacionPersonalizada(cotizacion);
            idCotizacionBase = cotizacion.getCotizacion().getIdCotizacion();

        } else if (req.getIdCotizacion() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo
                .findByCotizacion_IdCotizacion(req.getIdCotizacion())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cotización personalizada no encontrada para la cotización base: " + req.getIdCotizacion()
                ));
            item.setCotizacionPersonalizada(cotizacion);
            idCotizacionBase = req.getIdCotizacion();

        } else if (item.getCotizacionPersonalizada() != null
                && item.getCotizacionPersonalizada().getCotizacion() != null) {
            idCotizacionBase = item.getCotizacionPersonalizada().getCotizacion().getIdCotizacion();
        }

        if (req.getIdActividad() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idActividad es obligatorio");
        }

        if (req.getLugar() == null || req.getLugar().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lugar es obligatorio");
        }

        String lugarNormalizado = normalizarLugar(req.getLugar());

        ObraBlanca existente = obraBlancaRepo
                .findByCotizacionPersonalizada_Cotizacion_IdCotizacionAndIdActividadAndLugarIgnoreCase(
                        idCotizacionBase,
                        req.getIdActividad(),
                        lugarNormalizado
                )
                .orElse(null);

        if (existente != null && !existente.getIdObraBlanca().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta actividad ya está en la cotización para ese mismo lugar"
            );
        }

        item.setIdActividad(req.getIdActividad());
        item.setActividad(req.getActividad());
        item.setLugar(lugarNormalizado);
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
    resp.setIdActividad(item.getIdActividad());
    resp.setActividad(item.getActividad());
    resp.setLugar(item.getLugar());
    resp.setUnidad(item.getUnidad());
    resp.setTipoCobro(item.getUnidad()); // <-- agregar esto
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

    // NORMALIZAR LUGAR
   private String normalizarLugar(String lugar) {
    return lugar == null ? "" : lugar.trim().replaceAll("\\s+", " ").toUpperCase();
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
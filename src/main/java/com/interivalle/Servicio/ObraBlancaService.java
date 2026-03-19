/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.ObraBlancaRequest;
import com.interivalle.Modelo.CotizacionPersonalizada;
import com.interivalle.Modelo.ObraBlanca;
import com.interivalle.Repositorio.CotizacionPersonalizadaRepositorio;
import com.interivalle.Repositorio.ObraBlancaRepositorio;
import java.math.BigDecimal;
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
public class ObraBlancaService {

    @Autowired
    private ObraBlancaRepositorio obraBlancaRepo;

    @Autowired
    private CotizacionPersonalizadaRepositorio cotizacionRepo;

    // GUARDAR
    public ObraBlanca guardar(ObraBlancaRequest req) {
        CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        ObraBlanca item = new ObraBlanca();
        item.setCotizacion(cotizacion);
        item.setActividad(req.getActividad());
        item.setLugar(req.getLugar());
        item.setUnidad(req.getUnidad());
        item.setCantidad(req.getCantidad());
        item.setSemanas(req.getSemanas());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setMedida(req.getMedida());
        item.setDescripcion(req.getDescripcion());

        item.setSubtotal(calcularSubtotal(req));

        return obraBlancaRepo.save(item);
    }

    // LISTAR POR COTIZACION
    public List<ObraBlanca> listarPorCotizacion(Integer idCotizacion) {
        return obraBlancaRepo.findByCotizacionIdCotizacion(idCotizacion);
    }

    // OBTENER POR ID
    public ObraBlanca obtenerPorId(Integer id) {
        return obraBlancaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de obra blanca no encontrado"));
    }

    // ACTUALIZAR
    public ObraBlanca actualizar(Integer id, ObraBlancaRequest req) {
        ObraBlanca item = obraBlancaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de obra blanca no encontrado"));

        if (req.getIdCotizacion() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));
            item.setCotizacion(cotizacion);
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

        return obraBlancaRepo.save(item);
    }

    // ELIMINAR
    public void eliminar(Integer id) {
        ObraBlanca item = obraBlancaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de obra blanca no encontrado"));

        obraBlancaRepo.delete(item);
    }

    // CALCULAR SUBTOTAL
    private BigDecimal calcularSubtotal(ObraBlancaRequest req) {
        if (req.getPrecioUnitario() == null || req.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotalBase = BigDecimal.ZERO;

        // prioridad 1: medida * precio
        if (req.getMedida() != null && req.getMedida().compareTo(BigDecimal.ZERO) > 0) {
            subtotalBase = req.getMedida().multiply(req.getPrecioUnitario());
        }
        // prioridad 2: cantidad * precio
        else if (req.getCantidad() != null && req.getCantidad() > 0) {
            subtotalBase = req.getPrecioUnitario().multiply(BigDecimal.valueOf(req.getCantidad()));
        }

        // si hay semanas, multiplica
        if (req.getSemanas() != null && req.getSemanas() > 0) {
            subtotalBase = subtotalBase.multiply(BigDecimal.valueOf(req.getSemanas()));
        }

        return subtotalBase;
    }
}

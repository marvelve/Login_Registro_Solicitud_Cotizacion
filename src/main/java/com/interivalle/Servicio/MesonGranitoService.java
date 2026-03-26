/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.MesonGranitoRequest;
import com.interivalle.Modelo.CotizacionPersonalizada;
import com.interivalle.Modelo.MesonGranito;
import com.interivalle.Repositorio.CotizacionPersonalizadaRepositorio;
import com.interivalle.Repositorio.MesonGranitoRepositorio;
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
public class MesonGranitoService {

    @Autowired
    private MesonGranitoRepositorio mesonRepo;

    @Autowired
    private CotizacionPersonalizadaRepositorio cotizacionRepo;

    public MesonGranito guardar(MesonGranitoRequest req) {
        CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        MesonGranito item = new MesonGranito();
        item.setCotizacionPersonalizada(cotizacion);
        item.setTipoGranito(req.getTipoGranito());
        item.setLargo(req.getLargo());
        item.setAncho(req.getAncho());
        item.setEspesor(req.getEspesor());
        item.setCantidad(req.getCantidad());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setDescripcion(req.getDescripcion());
        item.setSubtotal(calcularSubtotal(req));

        return mesonRepo.save(item);
    }

    public List<MesonGranito> listarPorCotizacion(Integer idCotizacion) {
        return mesonRepo.findByCotizacionPersonalizada_IdCotizacionPersonalizada(idCotizacion);
    }

    public MesonGranito obtenerPorId(Integer id) {
        return mesonRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de mesón no encontrado"));
    }

    public MesonGranito actualizar(Integer id, MesonGranitoRequest req) {
        MesonGranito item = mesonRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de mesón no encontrado"));

        if (req.getIdCotizacion() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));
            item.setCotizacionPersonalizada(cotizacion);
        }

        item.setTipoGranito(req.getTipoGranito());
        item.setLargo(req.getLargo());
        item.setAncho(req.getAncho());
        item.setEspesor(req.getEspesor());
        item.setCantidad(req.getCantidad());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setDescripcion(req.getDescripcion());
        item.setSubtotal(calcularSubtotal(req));

        return mesonRepo.save(item);
    }

    public void eliminar(Integer id) {
        MesonGranito item = mesonRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de mesón no encontrado"));

        mesonRepo.delete(item);
    }

    private BigDecimal calcularSubtotal(MesonGranitoRequest req) {
        if (req.getPrecioUnitario() == null || req.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotalBase = BigDecimal.ZERO;

        if (req.getLargo() != null && req.getLargo().compareTo(BigDecimal.ZERO) > 0
                && req.getAncho() != null && req.getAncho().compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal area = req.getLargo().multiply(req.getAncho());
            subtotalBase = area.multiply(req.getPrecioUnitario());
        } else {
            subtotalBase = req.getPrecioUnitario();
        }

        if (req.getCantidad() != null && req.getCantidad() > 0) {
            subtotalBase = subtotalBase.multiply(BigDecimal.valueOf(req.getCantidad()));
        }

        return subtotalBase;
    }
}

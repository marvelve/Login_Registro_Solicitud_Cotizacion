/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.VidrioRequest;
import com.interivalle.Modelo.CotizacionPersonalizada;
import com.interivalle.Modelo.Vidrio;
import com.interivalle.Repositorio.CotizacionPersonalizadaRepositorio;
import com.interivalle.Repositorio.VidrioRepositorio;
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
public class VidrioService {

    @Autowired
    private VidrioRepositorio vidrioRepo;

    @Autowired
    private CotizacionPersonalizadaRepositorio cotizacionRepo;

    public Vidrio guardar(VidrioRequest req) {
        CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));

        Vidrio item = new Vidrio();
        item.setCotizacion(cotizacion);
        item.setTipoVidrio(req.getTipoVidrio());
        item.setAncho(req.getAncho());
        item.setAlto(req.getAlto());
        item.setCantidad(req.getCantidad());
        item.setInstalacion(req.getInstalacion());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setDescripcion(req.getDescripcion());
        item.setSubtotal(calcularSubtotal(req));

        return vidrioRepo.save(item);
    }

    public List<Vidrio> listarPorCotizacion(Integer idCotizacion) {
        return vidrioRepo.findByCotizacionIdCotizacion(idCotizacion);
    }

    public Vidrio obtenerPorId(Integer id) {
        return vidrioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de vidrio no encontrado"));
    }

    public Vidrio actualizar(Integer id, VidrioRequest req) {
        Vidrio item = vidrioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de vidrio no encontrado"));

        if (req.getIdCotizacion() != null) {
            CotizacionPersonalizada cotizacion = cotizacionRepo.findById(req.getIdCotizacion())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cotización no encontrada"));
            item.setCotizacion(cotizacion);
        }

        item.setTipoVidrio(req.getTipoVidrio());
        item.setAncho(req.getAncho());
        item.setAlto(req.getAlto());
        item.setCantidad(req.getCantidad());
        item.setInstalacion(req.getInstalacion());
        item.setPrecioUnitario(req.getPrecioUnitario());
        item.setDescripcion(req.getDescripcion());
        item.setSubtotal(calcularSubtotal(req));

        return vidrioRepo.save(item);
    }

    public void eliminar(Integer id) {
        Vidrio item = vidrioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de vidrio no encontrado"));

        vidrioRepo.delete(item);
    }

    private BigDecimal calcularSubtotal(VidrioRequest req) {
        if (req.getPrecioUnitario() == null || req.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotalBase = BigDecimal.ZERO;

        if (req.getAncho() != null && req.getAncho().compareTo(BigDecimal.ZERO) > 0
                && req.getAlto() != null && req.getAlto().compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal area = req.getAncho().multiply(req.getAlto());
            subtotalBase = area.multiply(req.getPrecioUnitario());
        } else {
            subtotalBase = req.getPrecioUnitario();
        }

        if (req.getCantidad() != null && req.getCantidad() > 0) {
            subtotalBase = subtotalBase.multiply(BigDecimal.valueOf(req.getCantidad()));
        }

        // recargo por instalación: 10%
        if (Boolean.TRUE.equals(req.getInstalacion())) {
            BigDecimal recargo = subtotalBase.multiply(new BigDecimal("0.10"));
            subtotalBase = subtotalBase.add(recargo);
        }

        return subtotalBase;
    }
}

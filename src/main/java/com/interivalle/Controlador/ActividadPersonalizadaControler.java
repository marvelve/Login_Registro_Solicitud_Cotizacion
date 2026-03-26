/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;

import com.interivalle.Modelo.ActividadPersonalizada;
import com.interivalle.Modelo.Servicios;
import com.interivalle.Repositorio.ActividadPersonalizadaRepositorio;
import com.interivalle.Repositorio.ServiciosRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/actividades-personalizadas")
@CrossOrigin(origins = "*")
public class ActividadPersonalizadaControler {

    @Autowired
    private ActividadPersonalizadaRepositorio actividadRepo;
    @Autowired
    private ServiciosRepositorio serviciosRepo;

    @GetMapping
    public List<ActividadPersonalizada> listar(@RequestParam(required = false) Integer idServicio) {
        if (idServicio != null) {
            return actividadRepo.findByServicios_IdServiciosAndEstadoTrue(idServicio);
        }
        return actividadRepo.findByEstadoTrue();
    }
    
     @GetMapping("/{id}")
    public ActividadPersonalizada obtenerPorId(@PathVariable Integer id) {
        return actividadRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActividadPersonalizada crear(@RequestBody ActividadPersonalizada actividad) {
        if (actividad.getServicios() == null || actividad.getServicios().getIdServicio() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del servicio");
        }

        Integer idServicio = actividad.getServicios().getIdServicio();

        Servicios servicio = serviciosRepo.findById(idServicio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));

        actividad.setServicios(servicio);

        if (actividad.getEstado() == null) {
            actividad.setEstado(true);
        }

        return actividadRepo.save(actividad);
    }

    @PutMapping("/{id}")
    public ActividadPersonalizada actualizar(
            @PathVariable Integer id,
            @RequestBody ActividadPersonalizada actividadRequest) {

        ActividadPersonalizada actividad = actividadRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));

        actividad.setNombreActividad(actividadRequest.getNombreActividad());
        actividad.setTipoCobro(actividadRequest.getTipoCobro());
        actividad.setPrecioUnitario(actividadRequest.getPrecioUnitario());
        actividad.setEstado(actividadRequest.getEstado());

        if (actividadRequest.getServicios() != null && actividadRequest.getServicios().getIdServicio() != null) {
            Integer idServicio = actividadRequest.getServicios().getIdServicio();

            Servicios servicio = serviciosRepo.findById(idServicio)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));

            actividad.setServicios(servicio);
        }

        return actividadRepo.save(actividad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        ActividadPersonalizada actividad = actividadRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));

        actividadRepo.delete(actividad);
    }
}

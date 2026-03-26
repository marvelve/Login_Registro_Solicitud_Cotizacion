/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;
import com.interivalle.DTO.CotizacionBaseResponse;
import com.interivalle.DTO.CotizacionResponse;
import com.interivalle.DTO.CotizacionVistaCompletaResponse;
import com.interivalle.DTO.CrearCotizacionRequest;
import com.interivalle.DTO.GenerarCotizacionBaseRequest;
import com.interivalle.Modelo.Usuario;
import com.interivalle.Repositorio.UsuarioRepositorio;
import com.interivalle.Servicio.CotizacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author mary_
 */
@RestController
@RequestMapping("/api/cliente/cotizaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteCotizacionControler {


    @Autowired
    private CotizacionService cotizacionService;
     @Autowired private UsuarioRepositorio usuarioRepo;

   @PostMapping("/generar-base")
    public CotizacionBaseResponse generarBase(
        Authentication authentication,
        @RequestBody GenerarCotizacionBaseRequest req) {

    String correo = authentication.getName();

    Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    Integer idUsuario = usuario.getIdUsuario();

        return cotizacionService.generarCotizacionBaseDesdeSolicitud(idUsuario, req);
    }
    
    @PostMapping
    public CotizacionResponse crear(
    Authentication authentication,
    @RequestBody CrearCotizacionRequest req) {

    String correo = authentication.getName();

    Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Usuario no encontrado"));

    return cotizacionService.crearCotizacion(usuario.getIdUsuario(), req);
    }
    
    @GetMapping("/{idCotizacion}")
    public CotizacionResponse verDetalle(
        Authentication authentication,
        @PathVariable Integer idCotizacion) {

        String correo = authentication.getName();

        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return cotizacionService.verDetalle(usuario.getIdUsuario(), idCotizacion);
    }
    
    @GetMapping
        public List<CotizacionResponse> listar(Authentication authentication) {
        String correo = authentication.getName();

        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return cotizacionService.listarPorCliente(usuario.getIdUsuario());
    }
   
   @GetMapping("/{idCotizacion}/vista-completa")
        public CotizacionVistaCompletaResponse vistaCompleta(
        @PathVariable Integer idCotizacion,
        Authentication authentication) {

       String correo = authentication.getName();

        Usuario usuario = usuarioRepo.findByCorreoUsuario(correo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return cotizacionService.obtenerVistaCompleta(usuario.getIdUsuario(), idCotizacion);
        }
}

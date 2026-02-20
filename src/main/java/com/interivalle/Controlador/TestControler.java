/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Controlador;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author mary_
 */


@RestController
@RequestMapping("/api/test")
public class TestControler {

    @GetMapping("/public")
    public String publico() { return "OK público"; }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cliente")
    public String cliente() { return "OK cliente"; }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/supervisor")
    public String supervisor() { return "OK supervisor"; }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() { return "OK admin"; }
}

package com.interivalle.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author mary_
 * Entidad Usuario
 * Representa la tabla "usuario" en la base de datos
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 5, message = "El nombre debe tener mínimo 5 caracteres")
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    @Column(name = "correo_usuario", unique = true)
    private String correoUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "contraseña debe tener mínimo 6 caracteres")
    @Column(name = "contrasena_usuario")
    private String contrasenaUsuario;

    @Column(name = "celular_usuario")
    private String celularUsuario;

    @Column(name = "ciudad_usuario")
    private String ciudadUsuario;

    @Column(name = "fecha_registro_usuario")
    private LocalDateTime fechaRegistroUsuario;

    @Column(name = "id_rol")
    private Integer idRol = 3;
    
    @Column(name = "estado_usuario")
    private Boolean estadoUsuario = true; // true=ACTIVO, false=INACTIVO

    
    // MÉTODO JPA

    @PrePersist
    protected void onCreate() {
        this.fechaRegistroUsuario = LocalDateTime.now();
    }

    // CONSTRUCTOR

    public Usuario() {
        // Constructor vacío requerido por JPA
    }

    public Usuario(String nombreUsuario, String correoUsuario, String contrasenaUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
    }

    // GETTERS Y SETTERS

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getCelularUsuario() {
        return celularUsuario;
    }

    public void setCelularUsuario(String celularUsuario) {
        this.celularUsuario = celularUsuario;
    }

    public String getCiudadUsuario() {
        return ciudadUsuario;
    }

    public void setCiudadUsuario(String ciudadUsuario) {
        this.ciudadUsuario = ciudadUsuario;
    }

    public LocalDateTime getFechaRegistroUsuario() {
        return fechaRegistroUsuario;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    
    public Boolean getEstadoUsuario() { return estadoUsuario; }
    public void setEstadoUsuario(Boolean estadoUsuario) { this.estadoUsuario = estadoUsuario; }
}

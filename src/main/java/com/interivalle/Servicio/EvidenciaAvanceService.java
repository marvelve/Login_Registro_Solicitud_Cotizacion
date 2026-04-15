/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interivalle.Servicio;

import com.interivalle.DTO.EvidenciaAvanceRequest;
import com.interivalle.DTO.EvidenciaAvanceResponse;
import com.interivalle.Modelo.AvanceSemanal;
import com.interivalle.Modelo.EvidenciaAvance;
import com.interivalle.Repositorio.AvanceSemanalRepositorio;
import com.interivalle.Repositorio.EvidenciaAvanceRepositorio;
import static jakarta.persistence.GenerationType.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.nio.file.Path;
/**
 *
 * @author mary_
 */

@Service
public class EvidenciaAvanceService {

    @Autowired
    private EvidenciaAvanceRepositorio evidenciaRepo;

    @Autowired
    private AvanceSemanalRepositorio avanceRepo;

    private final String uploadDir = "uploads/evidencias";

    public EvidenciaAvanceResponse guardarEvidencia(
            Integer idAvance,
            String descripcion,
            MultipartFile archivo
    ) {
        if (idAvance == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del avance es obligatorio");
        }

        if (archivo == null || archivo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El archivo es obligatorio");
        }

        AvanceSemanal avance = avanceRepo.findById(idAvance)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avance no encontrado"));

        String nombreOriginal = archivo.getOriginalFilename();
        String tipoArchivo = determinarTipoArchivo(nombreOriginal, archivo.getContentType());

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String extension = obtenerExtension(nombreOriginal);
            //String nombreGuardado = UUID.randomUUID() + extension;
            String nombreGuardado = java.util.UUID.randomUUID().toString() + extension;

            Path destino = uploadPath.resolve(nombreGuardado);
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            EvidenciaAvance evidencia = new EvidenciaAvance();
            evidencia.setAvanceSemanal(avance);
            evidencia.setTipoArchivo(tipoArchivo);
            evidencia.setNombreArchivo(nombreOriginal);
            evidencia.setUrlArchivo("/uploads/evidencias/" + nombreGuardado);
            evidencia.setDescripcion(descripcion);

            EvidenciaAvance guardada = evidenciaRepo.save(evidencia);

            return mapToResponse(guardada);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar el archivo: " + e.getMessage()
            );
        }
    }

    public List<EvidenciaAvanceResponse> listarPorAvance(Integer idAvance) {
        if (idAvance == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del avance es obligatorio");
        }

        return evidenciaRepo.findByAvanceSemanal_IdAvanceOrderByIdEvidenciaAsc(idAvance)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private EvidenciaAvanceResponse mapToResponse(EvidenciaAvance evidencia) {
        EvidenciaAvanceResponse res = new EvidenciaAvanceResponse();
        res.setIdEvidencia(evidencia.getIdEvidencia());
        res.setIdAvance(evidencia.getAvanceSemanal().getIdAvance());
        res.setTipoArchivo(evidencia.getTipoArchivo());
        res.setNombreArchivo(evidencia.getNombreArchivo());
        res.setUrlArchivo(evidencia.getUrlArchivo());
        res.setDescripcion(evidencia.getDescripcion());
        return res;
    }

    private String determinarTipoArchivo(String nombreArchivo, String contentType) {
        String extension = obtenerExtension(nombreArchivo).toLowerCase();

        if (contentType != null) {
            if (contentType.startsWith("image/")) {
                return "FOTO";
            }
            if (contentType.startsWith("video/")) {
                return "VIDEO";
            }
        }

        if (extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png") || extension.equals(".gif") || extension.equals(".webp")) {
            return "FOTO";
        }

        if (extension.equals(".mp4") || extension.equals(".mov") || extension.equals(".avi") || extension.equals(".mkv") || extension.equals(".webm")) {
            return "VIDEO";
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de archivo no permitido. Solo fotos o videos");
    }

    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return "";
        }
        return nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
    }
}

package com.example.tpov2.controller;

import com.example.tpov2.model.Publicacion;
import com.example.tpov2.service.DinamicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/algoritmos/dinamica")
@Tag(name = "Programación Dinámica", description = "Endpoints para ejecutar algoritmos de programación dinámica")
public class DinamicaController {

    @Autowired
    private DinamicaService dinamicaService;

    @Operation(
            summary = "Optimizar Contenido (Problema de la Mochila)",
            description = "Resuelve el problema de la mochila 0/1. Dado un usuario y un tiempo máximo de lectura (la 'capacidad' de la mochila), " +
                    "este algoritmo utiliza programación dinámica para seleccionar el conjunto de publicaciones de ese usuario que maximiza " +
                    "la suma de 'engagementScore' (el 'valor') sin exceder el 'tiempoLectura' total (el 'peso')."
    )
    @ApiResponse(responseCode = "200", description = "Conjunto óptimo de publicaciones encontrado.")
    @GetMapping("/optimizar-contenido")
    public ResponseEntity<List<Publicacion>> optimizarContenido(
            @Parameter(description = "ID del usuario cuyas publicaciones se evaluarán.", required = true, example = "u1")
            @RequestParam String userId,
            @Parameter(description = "Tiempo máximo de lectura disponible (capacidad de la mochila).", required = true, example = "10")
            @RequestParam int tiempoMaximo) {
        
        List<Publicacion> seleccionadas = dinamicaService.seleccionarPublicacionesOptimas(userId, tiempoMaximo);
        return ResponseEntity.ok(seleccionadas);
    }
}

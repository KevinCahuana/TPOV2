package com.example.tpov2.controller;

import com.example.tpov2.dto.CaminoDTO;
import com.example.tpov2.dto.ErrorResponseDTO;
import com.example.tpov2.service.BranchAndBoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/algoritmos/branch-and-bound")
@Tag(name = "Algoritmo de Ramificación y Poda (Branch & Bound)", description = "Endpoints para resolver problemas de optimización")
public class BranchAndBoundController {

    @Autowired
    private BranchAndBoundService branchAndBoundService;

    @Operation(
            summary = "Resolver Camino Óptimo con Paradas Obligatorias",
            description = "Implementa un algoritmo de Ramificación y Poda para encontrar el camino de menor coste desde una ciudad de origen a una de destino, " +
                    "asegurando que se visiten una serie de ciudades intermedias obligatorias. " +
                    "La estrategia utiliza una cola de prioridad para explorar siempre el camino parcial más prometedor (de menor coste) " +
                    "y poda las ramas que ya son peores que la mejor solución encontrada hasta el momento. Este es un problema de optimización combinatoria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Camino óptimo encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CaminoDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró un camino que cumpla todas las condiciones, o alguna de las ciudades no existe.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/camino-con-paradas")
    public ResponseEntity<CaminoDTO> encontrarCaminoConParadas(
            @Parameter(description = "Nombre de la ciudad de origen.", required = true, example = "Buenos Aires")
            @RequestParam String ciudadOrigen,
            @Parameter(description = "Nombre de la ciudad de destino.", required = true, example = "Salta")
            @RequestParam String ciudadDestino,
            @Parameter(description = "Lista de ciudades intermedias que deben ser visitadas. (Separadas por coma)", required = true, example = "Rosario,Cordoba")
            @RequestParam List<String> ciudadesIntermedias) {
        
        CaminoDTO mejorCamino = branchAndBoundService.encontrarCaminoOptimoConParadas(ciudadOrigen, ciudadDestino, ciudadesIntermedias);
        if (mejorCamino != null) {
            return ResponseEntity.ok(mejorCamino);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

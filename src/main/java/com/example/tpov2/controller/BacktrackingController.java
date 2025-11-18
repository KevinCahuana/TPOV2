package com.example.tpov2.controller;

import com.example.tpov2.dto.CaminoDTO;
import com.example.tpov2.dto.ErrorResponseDTO;
import com.example.tpov2.service.BacktrackingService;
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
@RequestMapping("/algoritmos/backtracking")
@Tag(name = "Algoritmo de Backtracking", description = "Endpoints para ejecutar algoritmos de búsqueda exhaustiva con retroceso")
public class BacktrackingController {

    @Autowired
    private BacktrackingService backtrackingService;

    @Operation(
            summary = "Encontrar todos los caminos con coste limitado",
            description = "Implementa un algoritmo de backtracking para encontrar todos los caminos simples (sin repetir nodos) " +
                    "entre un usuario de origen y uno de destino. El algoritmo explora recursivamente las rutas de amistad, " +
                    "pero 'poda' (hace backtrack) las ramas que exceden un coste máximo acumulado o que intentan visitar un nodo ya presente en el camino actual."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada. La lista puede estar vacía si no se encontraron caminos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CaminoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario de origen o destino no encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/encontrar-caminos")
    public ResponseEntity<List<CaminoDTO>> encontrarCaminos(
            @Parameter(description = "ID del usuario de origen.", required = true, example = "u1")
            @RequestParam String origenId,
            @Parameter(description = "ID del usuario de destino.", required = true, example = "u4")
            @RequestParam String destinoId,
            @Parameter(description = "Coste máximo acumulado que puede tener un camino.", required = true, example = "15")
            @RequestParam int maxCost) {
        
        // El try-catch ya no es necesario. Si el servicio lanza la excepción,
        // el GlobalExceptionHandler la interceptará y generará la respuesta 404.
        List<CaminoDTO> caminos = backtrackingService.encontrarCaminos(origenId, destinoId, maxCost);
        return ResponseEntity.ok(caminos);
    }
}

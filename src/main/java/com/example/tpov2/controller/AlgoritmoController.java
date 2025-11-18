package com.example.tpov2.controller;

import com.example.tpov2.dto.ErrorResponseDTO;
import com.example.tpov2.model.Ruta;
import com.example.tpov2.model.Usuario;
import com.example.tpov2.service.GrafoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/algoritmos")
@Tag(
        name = "Algoritmos de Grafos",
        description = "Endpoints para ejecutar algoritmos de grafos sobre la red de usuarios y ciudades"
)
public class AlgoritmoController {

    @Autowired
    private GrafoService grafoService;

    @Operation(
            summary = "Búsqueda en Anchura (BFS)",
            description = "Encuentra todos los usuarios alcanzables desde un usuario inicial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada."),
            @ApiResponse(responseCode = "404", description = "Usuario inicial no encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/bfs/{userId}/alcanzables")
    public ResponseEntity<List<Usuario>> getAlcanzablesBfs(
            @Parameter(description = "ID del usuario para iniciar la búsqueda.", required = true, example = "u1")
            @PathVariable String userId) {
        
        List<Usuario> alcanzables = grafoService.encontrarAlcanzablesBfs(userId);
        return ResponseEntity.ok(alcanzables);
    }

    @Operation(
            summary = "Búsqueda en Profundidad (DFS)",
            description = "Encuentra todos los usuarios alcanzables desde un usuario inicial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada."),
            @ApiResponse(responseCode = "404", description = "Usuario inicial no encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/dfs/{userId}/alcanzables")
    public ResponseEntity<List<Usuario>> getAlcanzablesDfs(
            @Parameter(description = "ID del usuario para iniciar la búsqueda.", required = true, example = "u1")
            @PathVariable String userId) {

        List<Usuario> alcanzables = grafoService.encontrarAlcanzablesDfs(userId);
        return ResponseEntity.ok(alcanzables);
    }

    @Operation(
            summary = "Camino más corto con Dijkstra",
            description = "Encuentra el camino de menor coste (amistad más cercana) entre dos usuarios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada. Una lista vacía significa que no hay camino."),
            @ApiResponse(responseCode = "404", description = "Usuario de origen o destino no encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/dijkstra/camino-minimo")
    public ResponseEntity<List<Usuario>> getCaminoMinimoDijkstra(
            @Parameter(description = "ID del usuario origen.", required = true, example = "u1")
            @RequestParam String origenId,
            @Parameter(description = "ID del usuario destino.", required = true, example = "u6")
            @RequestParam String destinoId) {

        List<Usuario> camino = grafoService.encontrarCaminoMinimoDijkstra(origenId, destinoId);
        return ResponseEntity.ok(camino);
    }

    @Operation(
            summary = "Árbol de Recubrimiento Mínimo (MST) con Prim",
            description = "Calcula el MST del grafo de ciudades para encontrar la red de rutas de menor coste total."
    )
    @ApiResponse(responseCode = "200", description = "MST encontrado exitosamente.")
    @GetMapping("/prim/mst")
    public ResponseEntity<List<Ruta>> getMstPrim() {
        return ResponseEntity.ok(grafoService.encontrarArbolRecubrimientoMinimoPrim());
    }

    @Operation(
            summary = "Árbol de Recubrimiento Mínimo (MST) con Kruskal",
            description = "Calcula el MST del grafo de ciudades para encontrar la red de rutas de menor coste total."
    )
    @ApiResponse(responseCode = "200", description = "MST encontrado exitosamente.")
    @GetMapping("/kruskal/mst")
    public ResponseEntity<List<Ruta>> getMstKruskal() {
        return ResponseEntity.ok(grafoService.encontrarArbolRecubrimientoMinimoKruskal());
    }
}

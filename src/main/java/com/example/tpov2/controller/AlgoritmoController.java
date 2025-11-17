package com.example.tpov2.controller;

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

    // ... (endpoints existentes de BFS, DFS, Dijkstra)

    @Operation(
            summary = "Árbol de Recubrimiento Mínimo (MST) con Prim",
            description = "Calcula el Árbol de Recubrimiento Mínimo del grafo de ciudades utilizando el algoritmo de Prim. " +
                    "Este algoritmo es ideal para grafos densos y encuentra la red de rutas de menor coste total que conecta todas las ciudades."
    )
    @ApiResponse(responseCode = "200", description = "MST encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ruta.class)))
    @GetMapping("/prim/mst")
    public ResponseEntity<List<Ruta>> getMstPrim() {
        return ResponseEntity.ok(grafoService.encontrarArbolRecubrimientoMinimoPrim());
    }

    @Operation(
            summary = "Árbol de Recubrimiento Mínimo (MST) con Kruskal",
            description = "Calcula el Árbol de Recubrimiento Mínimo del grafo de ciudades utilizando el algoritmo de Kruskal. " +
                    "Este algoritmo es eficiente en grafos dispersos y, al igual que Prim, encuentra la red de rutas de menor coste total."
    )
    @ApiResponse(responseCode = "200", description = "MST encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ruta.class)))
    @GetMapping("/kruskal/mst")
    public ResponseEntity<List<Ruta>> getMstKruskal() {
        return ResponseEntity.ok(grafoService.encontrarArbolRecubrimientoMinimoKruskal());
    }
    
    @Operation(
            summary = "Búsqueda en anchura (BFS)",
            description = "Encuentra todos los usuarios alcanzables desde un usuario inicial utilizando el algoritmo " +
                    "de búsqueda en anchura (Breadth-First Search). " +
                    "Este algoritmo recorre el grafo nivel por nivel, explorando primero todos los vecinos directos " +
                    "antes de pasar a los siguientes niveles."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios alcanzables encontrada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario inicial no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/bfs/{userId}/alcanzables")
    public ResponseEntity<List<Usuario>> getAlcanzablesBfs(
            @Parameter(
                    description = "Identificador único del usuario desde el cual iniciar la búsqueda",
                    required = true,
                    example = "user123"
            )
            @PathVariable String userId) {

        List<Usuario> alcanzables = grafoService.encontrarAlcanzablesBfs(userId);

        // Si el service devuelve lista vacía, interpretamos: usuario inicial no existe
        if (alcanzables.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alcanzables);
    }

    @Operation(
            summary = "Búsqueda en profundidad (DFS)",
            description = "Encuentra todos los usuarios alcanzables desde un usuario inicial utilizando el algoritmo " +
                    "de búsqueda en profundidad (Depth-First Search). " +
                    "Este algoritmo explora lo más profundo posible en cada rama antes de retroceder."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios alcanzables encontrada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario inicial no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/dfs/{userId}/alcanzables")
    public ResponseEntity<List<Usuario>> getAlcanzablesDfs(
            @Parameter(
                    description = "Identificador único del usuario desde el cual iniciar la búsqueda",
                    required = true,
                    example = "user123"
            )
            @PathVariable String userId) {

        List<Usuario> alcanzables = grafoService.encontrarAlcanzablesDfs(userId);

        if (alcanzables.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alcanzables);
    }

    @Operation(
            summary = "Camino más corto con Dijkstra",
            description = "Encuentra el camino más corto entre dos usuarios utilizando el algoritmo de Dijkstra. " +
                    "Este algoritmo considera los pesos de las relaciones de amistad para calcular la ruta óptima. " +
                    "Retorna una lista ordenada de usuarios que representan el camino desde el origen hasta el destino."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Camino más corto encontrado exitosamente. Si no existe camino, retorna una lista vacía",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario de origen o destino no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/dijkstra/camino-minimo")
    public ResponseEntity<List<Usuario>> getCaminoMinimoDijkstra(
            @Parameter(
                    description = "Identificador único del usuario origen",
                    required = true,
                    example = "user123"
            )
            @RequestParam String origenId,
            @Parameter(
                    description = "Identificador único del usuario destino",
                    required = true,
                    example = "user456"
            )
            @RequestParam String destinoId) {

        List<Usuario> camino = grafoService.encontrarCaminoMinimoDijkstra(origenId, destinoId);
        // Ojo: aquí seguimos devolviendo 200 incluso si la lista está vacía,
        // interpretándolo como "no hay camino". Si querés distinguir 404 (usuario no existe),
        // habría que agregar validación específica en el service/repositorio.
        return ResponseEntity.ok(camino);
    }
}

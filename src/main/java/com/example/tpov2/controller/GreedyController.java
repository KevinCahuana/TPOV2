package com.example.tpov2.controller;

import com.example.tpov2.dto.ErrorResponseDTO;
import com.example.tpov2.dto.RecomendacionAmigoDTO;
import com.example.tpov2.service.GreedyService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/algoritmos/greedy")
@Tag(name = "Algoritmo Voraz (Greedy)", description = "Endpoints para ejecutar algoritmos basados en estrategias voraces")
public class GreedyController {

    @Autowired
    private GreedyService greedyService;

    @Operation(
            summary = "Recomendar Amigo por Intereses Comunes",
            description = "Implementa un algoritmo voraz para recomendar un nuevo amigo a un usuario. " +
                    "La estrategia consiste en buscar, entre todos los usuarios que no son amigos del usuario actual, " +
                    "aquel que comparta la mayor cantidad de intereses."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendación encontrada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecomendacionAmigoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario inicial no encontrado o no hay recomendación posible.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/recomendar-amigo/{userId}")
    public ResponseEntity<RecomendacionAmigoDTO> recomendarAmigo(
            @Parameter(description = "ID del usuario para el cual se busca una recomendación.", required = true, example = "u1")
            @PathVariable String userId) {
        
        RecomendacionAmigoDTO recomendado = greedyService.recomendarAmigo(userId);
        if (recomendado != null) {
            return ResponseEntity.ok(recomendado);
        } else {
            // Este caso es para cuando no se encuentra una recomendación, no porque el usuario no exista.
            // El GlobalExceptionHandler se encargará del caso en que el userId es inválido.
            return ResponseEntity.notFound().build();
        }
    }
}

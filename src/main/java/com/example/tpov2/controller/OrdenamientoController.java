package com.example.tpov2.controller;

import com.example.tpov2.model.Usuario;
import com.example.tpov2.service.OrdenamientoService;
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
@RequestMapping("/algoritmos/ordenamiento")
@Tag(name = "Algoritmos de Ordenamiento", description = "Endpoints para ordenar la lista de usuarios")
public class OrdenamientoController {

    @Autowired
    private OrdenamientoService ordenamientoService;

    @Operation(
            summary = "Ordenar usuarios con Quicksort",
            description = "Obtiene y ordena la lista de usuarios usando el algoritmo Quicksort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios ordenada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Criterio de ordenamiento no soportado.", content = @Content)
    })
    @GetMapping("/quicksort")
    public ResponseEntity<List<Usuario>> ordenarConQuicksort(
            @Parameter(
                    description = "Criterio para ordenar la lista.",
                    required = true,
                    schema = @Schema(type = "string", allowableValues = {"nombre", "edad"})
            )
            @RequestParam String criterio) {
        try {
            List<Usuario> usuariosOrdenados = ordenamientoService.ordenarConQuicksort(criterio);
            return ResponseEntity.ok(usuariosOrdenados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Ordenar usuarios con Mergesort",
            description = "Obtiene y ordena la lista de usuarios usando el algoritmo Mergesort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios ordenada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Criterio de ordenamiento no soportado.", content = @Content)
    })
    @GetMapping("/mergesort")
    public ResponseEntity<List<Usuario>> ordenarConMergesort(
            @Parameter(
                    description = "Criterio para ordenar la lista.",
                    required = true,
                    schema = @Schema(type = "string", allowableValues = {"nombre", "edad"})
            )
            @RequestParam String criterio) {
        try {
            List<Usuario> usuariosOrdenados = ordenamientoService.ordenarConMergesort(criterio);
            return ResponseEntity.ok(usuariosOrdenados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

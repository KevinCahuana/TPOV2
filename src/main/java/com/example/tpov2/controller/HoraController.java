package com.example.tpov2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Tag(name = "Hora", description = "API para obtener la hora")
public class HoraController {

    @Operation(summary = "Obtiene la fecha y hora actual",
            responses = {
                    @ApiResponse(description = "Operación exitosa", responseCode = "200", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
            })
    @GetMapping("/hora")
    public String obtenerHora() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return "La hora actual es: " + dtf.format(now);
    }
}

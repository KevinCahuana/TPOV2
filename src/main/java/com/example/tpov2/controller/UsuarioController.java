package com.example.tpov2.controller;

import com.example.tpov2.model.Usuario;
import com.example.tpov2.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger logger = LogManager.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/merge")
    public ResponseEntity<Usuario> mergeUsuario(@RequestBody Usuario usuario) {
        logger.info("Solicitud para fusionar usuario: {}", usuario.getUserId());
        Usuario mergedUsuario = usuarioService.mergeUsuario(usuario);
        logger.info("Usuario fusionado exitosamente: {}", mergedUsuario.getUserId());
        return ResponseEntity.ok(mergedUsuario);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Usuario> getUsuarioByUserId(@PathVariable String userId) {
        logger.info("Solicitud para obtener usuario por userId: {}", userId);
        return usuarioService.getUsuarioByUserId(userId)
                .map(usuario -> {
                    logger.info("Usuario encontrado con userId: {}", userId);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    logger.warn("Usuario no encontrado con userId: {}", userId);
                    return ResponseEntity.notFound().build();
                });
    }
}

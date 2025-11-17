package com.example.tpov2.service;

import com.example.tpov2.model.Usuario;
import com.example.tpov2.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger logger = LogManager.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Usuario mergeUsuario(Usuario usuario) {
        logger.info("Fusionando usuario con userId: {}", usuario.getUserId());
        Usuario merged = usuarioRepository.save(usuario);
        logger.info("Usuario fusionado en la base de datos con userId: {}", merged.getUserId());
        return merged;
    }

    public Optional<Usuario> getUsuarioByUserId(String userId) {
        logger.info("Buscando usuario por userId: {}", userId);
        Optional<Usuario> usuario = usuarioRepository.findByUserId(userId);
        usuario.ifPresentOrElse(
                u -> logger.info("Usuario encontrado en la base de datos con userId: {}", userId),
                () -> logger.warn("Usuario no encontrado en la base de datos con userId: {}", userId)
        );
        return usuario;
    }
}

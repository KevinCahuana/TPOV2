package com.example.tpov2.service;

import com.example.tpov2.dto.RecomendacionAmigoDTO;
import com.example.tpov2.model.Usuario;
import com.example.tpov2.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GreedyService {

    private static final Logger log = LoggerFactory.getLogger(GreedyService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RecomendacionAmigoDTO recomendarAmigo(String userId) {
        log.info("[Greedy] Iniciando recomendación de amigo para el usuario: {}", userId);

        // Obtener el usuario actual y sus amigos
        if (!usuarioRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuario no encontrado: " + userId);
        }
        List<Usuario> amigosActuales = usuarioRepository.findAmigosByUserId(userId);
        Set<String> idsAmigos = amigosActuales.stream().map(Usuario::getUserId).collect(Collectors.toSet());
        idsAmigos.add(userId); // Añadir al propio usuario para no recomendarse a sí mismo

        // Usar el nuevo método del repositorio para obtener los intereses directamente
        Set<String> interesesUsuarioActual = usuarioRepository.findInteresesNombresByUserId(userId);
        log.info("Intereses de {}: {}", userId, interesesUsuarioActual); // Log solicitado

        // Obtener todos los demás usuarios
        List<Usuario> todosLosUsuarios = usuarioRepository.findAll();

        Usuario mejorCandidato = null;
        long maxInteresesComunes = -1;
        List<String> interesesDelMejorCandidato = new ArrayList<>();

        // Estrategia Voraz
        for (Usuario candidato : todosLosUsuarios) {
            if (!idsAmigos.contains(candidato.getUserId())) {
                Set<String> interesesCandidato = usuarioRepository.findInteresesNombresByUserId(candidato.getUserId());
                
                List<String> interesesComunes = interesesCandidato.stream()
                        .filter(interesesUsuarioActual::contains)
                        .collect(Collectors.toList());

                long numInteresesComunes = interesesComunes.size();
                log.debug("[Greedy] Candidato: {}. Intereses en común: {} ({})", candidato.getUserId(), numInteresesComunes, interesesComunes);

                if (numInteresesComunes > maxInteresesComunes) {
                    maxInteresesComunes = numInteresesComunes;
                    mejorCandidato = candidato;
                    interesesDelMejorCandidato = new ArrayList<>(interesesComunes);
                    log.info("[Greedy] Nuevo mejor candidato encontrado: {} con {} intereses en común.", mejorCandidato.getUserId(), maxInteresesComunes);
                }
            }
        }

        if (mejorCandidato != null) {
            RecomendacionAmigoDTO recomendacion = new RecomendacionAmigoDTO(mejorCandidato, interesesDelMejorCandidato);
            log.info("[Greedy] Recomendación final: {}", recomendacion.toString());
            return recomendacion;
        } else {
            log.warn("[Greedy] No se encontraron candidatos para recomendar.");
            return null;
        }
    }
}

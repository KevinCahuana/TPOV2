package com.example.tpov2.service;

import com.example.tpov2.dto.CaminoDTO;
import com.example.tpov2.model.AmigoConPeso;
import com.example.tpov2.model.Usuario;
import com.example.tpov2.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BacktrackingService {

    private static final Logger log = LoggerFactory.getLogger(BacktrackingService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<CaminoDTO> encontrarCaminos(String origenId, String destinoId, int maxCost) {
        log.info("[Backtracking] Iniciando búsqueda de caminos de {} a {} con coste máximo {}", origenId, destinoId, maxCost);

        Usuario origen = usuarioRepository.findByUserId(origenId).orElseThrow(() -> new IllegalArgumentException("Usuario origen no encontrado"));
        Usuario destino = usuarioRepository.findByUserId(destinoId).orElseThrow(() -> new IllegalArgumentException("Usuario destino no encontrado"));

        List<CaminoDTO> todosLosCaminos = new ArrayList<>();
        
        Set<Usuario> caminoInicial = new LinkedHashSet<>();
        caminoInicial.add(origen);

        backtrackRecursivo(origen, destino, maxCost, 0, caminoInicial, todosLosCaminos);

        log.info("[Backtracking] Búsqueda finalizada. Se encontraron {} caminos.", todosLosCaminos.size());
        return todosLosCaminos;
    }

    private void backtrackRecursivo(Usuario actual, Usuario destino, int maxCost, int costoActual, Set<Usuario> caminoActual, List<CaminoDTO> todosLosCaminos) {
        log.debug("[Backtracking] -> Evaluando en {}. Coste actual: {}. Camino: {}", actual.getNombre(), costoActual, getPathIds(caminoActual));

        if (actual.equals(destino)) {
            List<String> idsDelCamino = getPathIds(caminoActual);
            log.info("[Backtracking] *** Solución encontrada: {} con coste {} ***", idsDelCamino, costoActual);
            todosLosCaminos.add(new CaminoDTO(idsDelCamino, costoActual));
            return;
        }

        List<AmigoConPeso> amigos = usuarioRepository.findAmigosConPesoByUserId(actual.getUserId());
        for (AmigoConPeso amigo : amigos) {
            Usuario siguienteAmigo = amigo.getAmigo();
            int pesoArista = amigo.getPeso();

            if (!caminoActual.contains(siguienteAmigo) && (costoActual + pesoArista <= maxCost)) {
                Set<Usuario> nuevoCamino = new LinkedHashSet<>(caminoActual);
                nuevoCamino.add(siguienteAmigo);
                
                backtrackRecursivo(siguienteAmigo, destino, maxCost, costoActual + pesoArista, nuevoCamino, todosLosCaminos);
            } else {
                if (caminoActual.contains(siguienteAmigo)) {
                    log.debug("[Backtracking] Omitiendo {}: ya está en el camino.", siguienteAmigo.getNombre());
                } else {
                    log.debug("[Backtracking] Omitiendo {}: el coste ({}) excedería el máximo ({}).", siguienteAmigo.getNombre(), costoActual + pesoArista, maxCost);
                }
            }
        }
        log.debug("[Backtracking] <- Finalizada exploración desde {}.", actual.getNombre());
    }

    private List<String> getPathIds(Set<Usuario> path) {
        return path.stream()
                .map(Usuario::getUserId)
                .collect(Collectors.toList());
    }
}

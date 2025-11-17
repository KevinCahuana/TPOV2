package com.example.tpov2.service;

import com.example.tpov2.model.AmigoConPeso;
import com.example.tpov2.model.Usuario;
import com.example.tpov2.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrafoService {

    private static final Logger log = LoggerFactory.getLogger(GrafoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> encontrarAlcanzablesBfs(String userId) {
        log.info("[BFS] Iniciando desde el usuario: {}", userId);
        if (!usuarioRepository.existsById(userId)) {
            log.warn("[BFS] El usuario inicial {} no existe.", userId);
            return Collections.emptyList();
        }

        Queue<String> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();

        cola.add(userId);
        visitados.add(userId);

        while (!cola.isEmpty()) {
            String idActual = cola.poll();
            log.debug("[BFS] Visitando (sacando de la cola): {}", idActual);

            List<Usuario> amigos = usuarioRepository.findAmigosByUserId(idActual);
            for (Usuario amigo : amigos) {
                log.debug("[BFS] Analizando amigo: {}. Visitado: {}", amigo.getUserId(), visitados.contains(amigo.getUserId()));
                if (visitados.add(amigo.getUserId())) {
                    log.debug("[BFS] Agregando a la cola: {}", amigo.getUserId());
                    cola.add(amigo.getUserId());
                }
            }
        }

        log.info("[BFS] Finalizado. Nodos alcanzables: {}", visitados.size());
        List<Usuario> resultado = new ArrayList<>();
        usuarioRepository.findAllById(visitados).forEach(resultado::add);
        return resultado;
    }

    public List<Usuario> encontrarAlcanzablesDfs(String userId) {
        log.info("[DFS] Iniciando desde el usuario: {}", userId);
        if (!usuarioRepository.existsById(userId)) {
            log.warn("[DFS] El usuario inicial {} no existe.", userId);
            return Collections.emptyList();
        }

        Set<String> visitados = new HashSet<>();
        List<Usuario> resultado = new ArrayList<>();
        dfsRecursivo(userId, visitados, resultado);

        log.info("[DFS] Finalizado. Nodos alcanzables: {}", resultado.size());
        return resultado;
    }

    private void dfsRecursivo(String idActual, Set<String> visitados, List<Usuario> resultado) {
        log.debug("[DFS] Visitando recursivamente: {}", idActual);
        visitados.add(idActual);
        usuarioRepository.findById(idActual).ifPresent(resultado::add);

        List<Usuario> amigos = usuarioRepository.findAmigosByUserId(idActual);
        for (Usuario amigo : amigos) {
            log.debug("[DFS] Analizando amigo: {}. Visitado: {}", amigo.getUserId(), visitados.contains(amigo.getUserId()));
            if (!visitados.contains(amigo.getUserId())) {
                dfsRecursivo(amigo.getUserId(), visitados, resultado);
            }
        }
    }

    public List<Usuario> encontrarCaminoMinimoDijkstra(String origenId, String destinoId) {
        log.info("[Dijkstra] Iniciando desde {} hasta {}", origenId, destinoId);

        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());

        distancias.put(origenId, 0);
        pq.add(new AbstractMap.SimpleEntry<>(origenId, 0));
        log.debug("[Dijkstra] Distancias inicializadas. Agregando {} a la PQ con distancia 0", origenId);

        Set<String> visitados = new HashSet<>();

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> entrada = pq.poll();
            String idActual = entrada.getKey();
            int costoActual = entrada.getValue();

            if (visitados.contains(idActual)) {
                continue;
            }
            visitados.add(idActual);

            log.debug("[Dijkstra] PQ poll: Visitando {} con costo {}", idActual, costoActual);

            if (idActual.equals(destinoId)) {
                break; // Encontramos el destino
            }

            List<AmigoConPeso> vecinos = usuarioRepository.findAmigosConPesoByUserId(idActual);
            for (AmigoConPeso vecino : vecinos) {
                String vecinoId = vecino.getAmigo().getUserId();
                int pesoArista = vecino.getPeso();
                int nuevoCosto = distancias.get(idActual) + pesoArista;
                int costoVecinoActual = distancias.getOrDefault(vecinoId, Integer.MAX_VALUE);

                log.debug("[Dijkstra] ...Analizando vecino {}: costo actual {}, nuevo costo propuesto {}", vecinoId, costoVecinoActual, nuevoCosto);

                if (nuevoCosto < costoVecinoActual) {
                    distancias.put(vecinoId, nuevoCosto);
                    predecesores.put(vecinoId, idActual);
                    pq.add(new AbstractMap.SimpleEntry<>(vecinoId, nuevoCosto));
                    log.info("[Dijkstra] ...Nuevo camino más corto a {} encontrado: {}", vecinoId, nuevoCosto);
                    log.debug("[Dijkstra] ......Agregando/Actualizando {} en PQ", vecinoId);
                }
            }
        }

        Integer distanciaFinal = distancias.get(destinoId);
        if (distanciaFinal == null || distanciaFinal == Integer.MAX_VALUE) {
            log.warn("[Dijkstra] No se encontró un camino de {} a {}", origenId, destinoId);
            return Collections.emptyList();
        }

        log.info("[Dijkstra] Camino más corto encontrado para {}. Costo total: {}", destinoId, distanciaFinal);

        // Reconstruir camino
        LinkedList<String> idsDelCamino = new LinkedList<>();
        String pasoActual = destinoId;
        while (pasoActual != null) {
            idsDelCamino.addFirst(pasoActual);
            pasoActual = predecesores.get(pasoActual);
        }

        Map<String, Usuario> mapaDeUsuarios = new HashMap<>();
        usuarioRepository.findAllById(idsDelCamino).forEach(u -> mapaDeUsuarios.put(u.getUserId(), u));

        return idsDelCamino.stream()
                .map(mapaDeUsuarios::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

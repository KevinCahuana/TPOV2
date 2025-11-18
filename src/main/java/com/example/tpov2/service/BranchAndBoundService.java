package com.example.tpov2.service;

import com.example.tpov2.dto.CaminoDTO;
import com.example.tpov2.model.Ruta;
import com.example.tpov2.repository.CiudadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BranchAndBoundService {

    private static final Logger log = LoggerFactory.getLogger(BranchAndBoundService.class);

    @Autowired
    private CiudadRepository ciudadRepository;

    // Clase interna para representar el estado de un camino parcial en la búsqueda
    private static class PathState implements Comparable<PathState> {
        private final List<String> path;
        private final int cost;
        private final Set<String> intermediateVisited;

        public PathState(List<String> path, int cost, Set<String> intermediateVisited) {
            this.path = path;
            this.cost = cost;
            this.intermediateVisited = intermediateVisited;
        }

        public String getLastCity() {
            return path.get(path.size() - 1);
        }

        @Override
        public int compareTo(PathState other) {
            // Prioriza los caminos que han visitado más ciudades intermedias, y luego por coste
            int intermediateCompare = Integer.compare(other.intermediateVisited.size(), this.intermediateVisited.size());
            if (intermediateCompare != 0) {
                return intermediateCompare;
            }
            return Integer.compare(this.cost, other.cost);
        }
    }

    public CaminoDTO encontrarCaminoOptimoConParadas(String ciudadOrigen, String ciudadDestino, List<String> ciudadesIntermedias) {
        log.info("[B&B] Iniciando búsqueda de {} a {} pasando por {}", ciudadOrigen, ciudadDestino, ciudadesIntermedias);

        List<Ruta> todasLasRutas = ciudadRepository.findAllRutas();
        if (todasLasRutas.isEmpty()) {
            throw new IllegalStateException("No hay rutas en la base de datos.");
        }

        Map<String, List<Ruta>> adj = new HashMap<>();
        for (Ruta ruta : todasLasRutas) {
            adj.computeIfAbsent(ruta.getOrigen(), k -> new ArrayList<>()).add(ruta);
            adj.computeIfAbsent(ruta.getDestino(), k -> new ArrayList<>()).add(new Ruta(ruta.getDestino(), ruta.getOrigen(), ruta.getDistancia()));
        }

        if (!adj.containsKey(ciudadOrigen) || !adj.containsKey(ciudadDestino)) {
            throw new IllegalArgumentException("La ciudad de origen o destino no existe.");
        }
        for(String ciudad : ciudadesIntermedias) {
            if(!adj.containsKey(ciudad)) {
                throw new IllegalArgumentException("La ciudad intermedia " + ciudad + " no existe.");
            }
        }

        PriorityQueue<PathState> pq = new PriorityQueue<>();
        CaminoDTO mejorCaminoGlobal = null;
        int costeMejorCamino = Integer.MAX_VALUE;

        List<String> pathInicial = new ArrayList<>();
        pathInicial.add(ciudadOrigen);
        pq.add(new PathState(pathInicial, 0, new HashSet<>()));
        log.debug("[B&B] Inicio: Añadiendo estado inicial para {}", ciudadOrigen);

        while (!pq.isEmpty()) {
            PathState estadoActual = pq.poll();

            // PODA (Bound): Si el coste actual ya es peor que la mejor solución, descartar.
            if (estadoActual.cost >= costeMejorCamino) {
                log.warn("[B&B] PODA: Descartando camino {} (coste {}) por superar el mejor coste actual ({})", estadoActual.path, estadoActual.cost, costeMejorCamino);
                continue;
            }

            String ultimaCiudad = estadoActual.getLastCity();

            // CONDICIÓN DE ÉXITO: Hemos llegado al destino y visitado todas las paradas intermedias.
            if (ultimaCiudad.equals(ciudadDestino) && estadoActual.intermediateVisited.size() == ciudadesIntermedias.size()) {
                costeMejorCamino = estadoActual.cost;
                mejorCaminoGlobal = new CaminoDTO(estadoActual.path, estadoActual.cost);
                log.info("[B&B] *** Nueva mejor solución encontrada: {} con coste {} ***", estadoActual.path, estadoActual.cost);
                // No hacemos 'continue' aquí para que la nueva cota (bound) se use para podar la cola de prioridad.
            }

            // RAMIFICACIÓN: explorar vecinos no visitados
            for (Ruta rutaVecina : adj.getOrDefault(ultimaCiudad, Collections.emptyList())) {
                String vecino = rutaVecina.getDestino();
                if (!estadoActual.path.contains(vecino)) {
                    List<String> nuevoPath = new ArrayList<>(estadoActual.path);
                    nuevoPath.add(vecino);

                    Set<String> nuevasIntermediasVisitadas = new HashSet<>(estadoActual.intermediateVisited);
                    if (ciudadesIntermedias.contains(vecino)) {
                        nuevasIntermediasVisitadas.add(vecino);
                    }
                    
                    PathState nuevoEstado = new PathState(nuevoPath, estadoActual.cost + rutaVecina.getDistancia(), nuevasIntermediasVisitadas);
                    pq.add(nuevoEstado);
                    log.debug("[B&B] Ramificación: Añadiendo a la cola el camino {} con coste {}", nuevoPath, nuevoEstado.cost);
                }
            }
        }

        if (mejorCaminoGlobal != null) {
            log.info("[B&B] Búsqueda finalizada. Mejor camino: {}", mejorCaminoGlobal.getCamino());
        } else {
            log.warn("[B&B] No se encontró ningún camino que cumpla todas las condiciones.");
        }

        return mejorCaminoGlobal;
    }
}

package com.example.tpov2.service;

import com.example.tpov2.model.AmigoConPeso;
import com.example.tpov2.model.Ruta;
import com.example.tpov2.model.Usuario;
import com.example.tpov2.repository.CiudadRepository;
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

    @Autowired
    private CiudadRepository ciudadRepository;

    // ... (métodos existentes de BFS, DFS, Dijkstra)

    public List<Ruta> encontrarArbolRecubrimientoMinimoPrim() {
        log.info("[Prim] Iniciando algoritmo de Prim para encontrar el MST de ciudades.");
        List<Map<String, Object>> todasLasRutasMap = ciudadRepository.findAllRutas();
        if (todasLasRutasMap.isEmpty()) {
            return Collections.emptyList();
        }

        // Construir una lista de adyacencia
        Map<String, List<Ruta>> adj = new HashMap<>();
        for (Map<String, Object> rutaMap : todasLasRutasMap) {
            String origen = (String) rutaMap.get("origen");
            String destino = (String) rutaMap.get("destino");
            int distancia = ((Number) rutaMap.get("distancia")).intValue();
            adj.computeIfAbsent(origen, k -> new ArrayList<>()).add(new Ruta(origen, destino, distancia));
            adj.computeIfAbsent(destino, k -> new ArrayList<>()).add(new Ruta(destino, origen, distancia));
        }

        List<Ruta> mst = new ArrayList<>();
        PriorityQueue<Ruta> pq = new PriorityQueue<>(Comparator.comparingInt(Ruta::getDistancia));
        Set<String> visitados = new HashSet<>();

        // Empezar desde una ciudad arbitraria
        String ciudadInicial = adj.keySet().iterator().next();
        visitados.add(ciudadInicial);
        pq.addAll(adj.get(ciudadInicial));
        log.debug("[Prim] Ciudad inicial: {}. Añadiendo sus rutas a la cola de prioridad.", ciudadInicial);

        while (!pq.isEmpty() && visitados.size() < adj.size()) {
            Ruta rutaActual = pq.poll();
            String destino = rutaActual.getDestino();

            if (visitados.contains(destino)) {
                continue;
            }

            visitados.add(destino);
            mst.add(rutaActual);
            log.debug("[Prim] Seleccionada ruta: {} -> {} con distancia {}. Total MST: {}", rutaActual.getOrigen(), destino, rutaActual.getDistancia(), mst.size());

            for (Ruta nuevaRuta : adj.get(destino)) {
                if (!visitados.contains(nuevaRuta.getDestino())) {
                    pq.add(nuevaRuta);
                }
            }
        }
        log.info("[Prim] Algoritmo finalizado. MST contiene {} rutas.", mst.size());
        return mst;
    }

    public List<Ruta> encontrarArbolRecubrimientoMinimoKruskal() {
        log.info("[Kruskal] Iniciando algoritmo de Kruskal para encontrar el MST de ciudades.");
        List<Map<String, Object>> todasLasRutasMap = ciudadRepository.findAllRutas();
        if (todasLasRutasMap.isEmpty()) {
            return Collections.emptyList();
        }

        List<Ruta> todasLasRutas = todasLasRutasMap.stream()
                .map(map -> new Ruta((String) map.get("origen"), (String) map.get("destino"), ((Number) map.get("distancia")).intValue()))
                .sorted(Comparator.comparingInt(Ruta::getDistancia))
                .collect(Collectors.toList());

        List<Ruta> mst = new ArrayList<>();
        Map<String, String> parent = new HashMap<>();
        ciudadRepository.findAll().forEach(ciudad -> parent.put(ciudad.getNombre(), ciudad.getNombre()));

        for (Ruta ruta : todasLasRutas) {
            String root1 = find(parent, ruta.getOrigen());
            String root2 = find(parent, ruta.getDestino());

            if (!root1.equals(root2)) {
                mst.add(ruta);
                union(parent, root1, root2);
                log.debug("[Kruskal] Añadida ruta: {} -> {} con distancia {}. Total MST: {}", ruta.getOrigen(), ruta.getDestino(), ruta.getDistancia(), mst.size());
            }
        }
        log.info("[Kruskal] Algoritmo finalizado. MST contiene {} rutas.", mst.size());
        return mst;
    }

    // Métodos auxiliares para Kruskal (Union-Find)
    private String find(Map<String, String> parent, String i) {
        if (parent.get(i).equals(i)) {
            return i;
        }
        return find(parent, parent.get(i));
    }

    private void union(Map<String, String> parent, String x, String y) {
        String rootX = find(parent, x);
        String rootY = find(parent, y);
        if (!rootX.equals(rootY)) {
            parent.put(rootX, rootY);
        }
    }
    
    // ... (métodos existentes de BFS, DFS, Dijkstra)
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

package com.example.tpov2.service;

import com.example.tpov2.model.Publicacion;
import com.example.tpov2.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DinamicaService {

    private static final Logger log = LoggerFactory.getLogger(DinamicaService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Publicacion> seleccionarPublicacionesOptimas(String userId, int tiempoMaximo) {
        log.info("[Dinamica] Iniciando selección óptima de publicaciones para {} con un tiempo máximo de {} minutos.", userId, tiempoMaximo);

        List<Publicacion> publicaciones = usuarioRepository.findPublicacionesByUserId(userId);
        if (publicaciones.isEmpty()) {
            log.warn("[Dinamica] El usuario {} no tiene publicaciones.", userId);
            return new ArrayList<>();
        }

        int n = publicaciones.size();
        int[][] dp = new int[n + 1][tiempoMaximo + 1];

        // Construir la tabla de DP
        for (int i = 1; i <= n; i++) {
            Publicacion pub = publicaciones.get(i - 1);
            int peso = pub.getTiempoLectura();
            int valor = pub.getEngagementScore();

            for (int w = 1; w <= tiempoMaximo; w++) {
                if (peso <= w) {
                    // Decidir si incluir la publicación actual o no
                    dp[i][w] = Math.max(valor + dp[i - 1][w - peso], dp[i - 1][w]);
                } else {
                    // No se puede incluir la publicación actual
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int maxEngagement = dp[n][tiempoMaximo];
        log.info("[Dinamica] Máximo engagement alcanzable: {}", maxEngagement);

        // Reconstruir el conjunto de publicaciones seleccionadas
        List<Publicacion> seleccionadas = new ArrayList<>();
        int w = tiempoMaximo;
        for (int i = n; i > 0 && maxEngagement > 0; i--) {
            if (maxEngagement != dp[i - 1][w]) {
                Publicacion pub = publicaciones.get(i - 1);
                seleccionadas.add(pub);
                maxEngagement -= pub.getEngagementScore();
                w -= pub.getTiempoLectura();
                log.debug("[Dinamica] Seleccionada publicación: {} (Valor: {}, Peso: {})", pub.getPostId(), pub.getEngagementScore(), pub.getTiempoLectura());
            }
        }

        log.info("[Dinamica] Selección finalizada. {} publicaciones seleccionadas.", seleccionadas.size());
        return seleccionadas;
    }
}

package com.example.TPOV2.service;

    if (!usuarioRepository.existsById(origenId) || !usuarioRepository.existsById(destinoId)) {
        log.warn("[Dijkstra] El usuario de origen {} o destino {} no existe.", origenId, destinoId);
        return Collections.emptyList();
    }

import com.example.TPOV2.model.Usuario;
import com.example.TPOV2.repository.AmigoConPeso;
import com.example.TPOV2.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

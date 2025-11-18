package com.example.tpov2.dto;

import java.util.List;

public class CaminoDTO {

    private final List<String> camino;
    private final int costoTotal;

    public CaminoDTO(List<String> camino, int costoTotal) {
        this.camino = camino;
        this.costoTotal = costoTotal;
    }

    public List<String> getCamino() {
        return camino;
    }

    public int getCostoTotal() {
        return costoTotal;
    }
}

package com.example.tpov2.dto;

import com.example.tpov2.model.Usuario;
import java.util.List;

public class RecomendacionAmigoDTO {

    private final Usuario usuarioRecomendado;
    private final List<String> interesesEnComun;

    public RecomendacionAmigoDTO(Usuario usuarioRecomendado, List<String> interesesEnComun) {
        this.usuarioRecomendado = usuarioRecomendado;
        this.interesesEnComun = interesesEnComun;
    }

    public Usuario getUsuarioRecomendado() {
        return usuarioRecomendado;
    }

    public List<String> getInteresesEnComun() {
        return interesesEnComun;
    }

    @Override
    public String toString() {
        return "RecomendacionAmigoDTO{" +
                "usuarioRecomendado=" + usuarioRecomendado +
                ", interesesEnComun=" + interesesEnComun +
                '}';
    }
}

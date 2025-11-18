package com.example.tpov2.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Publicacion")
public class Publicacion {

    @Id
    private final String postId;
    private final int engagementScore;
    private final int tiempoLectura;

    public Publicacion(String postId, int engagementScore, int tiempoLectura) {
        this.postId = postId;
        this.engagementScore = engagementScore;
        this.tiempoLectura = tiempoLectura;
    }

    public String getPostId() {
        return postId;
    }

    public int getEngagementScore() {
        return engagementScore;
    }

    public int getTiempoLectura() {
        return tiempoLectura;
    }
}

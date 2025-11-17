package com.example.tpov2.repository;

import com.example.tpov2.model.Ciudad;
import com.example.tpov2.model.Ruta;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends Neo4jRepository<Ciudad, String> {

    /**
     * Devuelve una lista ÚNICA de todas las rutas (aristas) del grafo de ciudades.
     * Se usa "WHERE id(c1) < id(c2)" para evitar duplicados en un grafo no dirigido.
     */
    @Query("MATCH (c1:Ciudad)-[r:RUTA_ENTRE]-(c2:Ciudad) " +
           "WHERE id(c1) < id(c2) " +
           "RETURN c1.nombre AS origen, c2.nombre AS destino, r.distancia AS distancia")
    List<Ruta> findAllRutas();
}

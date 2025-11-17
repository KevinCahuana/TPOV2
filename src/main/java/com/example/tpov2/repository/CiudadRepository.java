package com.example.tpov2.repository;

import com.example.tpov2.model.Ciudad;
import com.example.tpov2.model.Ruta; // Importar la clase Ruta
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends Neo4jRepository<Ciudad, String> {

    /**
     * Devuelve todas las rutas (aristas) del grafo de ciudades.
     * Cada Ruta representa una arista con su origen, destino y distancia.
     */
    @Query("MATCH (c1:Ciudad)-[r:RUTA_ENTRE]->(c2:Ciudad) RETURN c1.nombre AS origen, c2.nombre AS destino, r.distancia AS distancia")
    List<Ruta> findAllRutas(); // Cambiado a List<Ruta>
}

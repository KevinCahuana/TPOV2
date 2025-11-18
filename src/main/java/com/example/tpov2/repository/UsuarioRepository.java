package com.example.tpov2.repository;

import com.example.tpov2.model.AmigoConPeso;
import com.example.tpov2.model.Interes;
import com.example.tpov2.model.Publicacion;
import com.example.tpov2.model.Usuario;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends Neo4jRepository<Usuario, String> {

    Optional<Usuario> findByUserId(String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[:ES_AMIGO_DE]-(amigo:Usuario) RETURN amigo")
    List<Usuario> findAmigosByUserId(@Param("userId") String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[r:ES_AMIGO_DE]-(amigo:Usuario) " +
            "RETURN amigo AS amigo, r.peso AS peso")
    List<AmigoConPeso> findAmigosConPesoByUserId(@Param("userId") String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[r:ES_AMIGO_DE]-(amigo:Usuario) " +
            "RETURN amigo, r.peso as peso")
    List<Map<String, Object>> findAmigosConPesoByUserIdRaw(@Param("userId") String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[:TIENE_INTERES]->(i:Interes) RETURN i")
    List<Interes> findInteresesByUserId(@Param("userId") String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[:TIENE_INTERES]->(i:Interes) RETURN i.nombre")
    Set<String> findInteresesNombresByUserId(@Param("userId") String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[:CREO]->(p:Publicacion) RETURN p")
    List<Publicacion> findPublicacionesByUserId(@Param("userId") String userId);
}

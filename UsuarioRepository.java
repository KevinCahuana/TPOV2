import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends Neo4jRepository<Usuario, String> {

    Optional<Usuario> findByUserId(String userId);

    Optional<Usuario> findByUserId(String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[:ES_AMIGO_DE]-(amigo:Usuario) RETURN amigo")
    List<Usuario> findAmigosByUserId(@Param("userId") String userId);

    @Query("MATCH (u:Usuario {userId: $userId})-[r:ES_AMIGO_DE]-(amigo:Usuario) RETURN amigo AS amigo, r.peso AS peso")
    List<com.example.TPOV2.repository.AmigoConPeso> findAmigosConPesoByUserId(@Param("userId") String userId);
}

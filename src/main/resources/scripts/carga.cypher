// ======================================================================
// PASO 1: Crear Nodos de Usuarios
// (Necesarios para BFS, DFS, Dijkstra, Quicksort, Backtracking)
// ======================================================================
MERGE (u1:Usuario {userId: 'u1', nombre: 'Ana', edad: 28});
MERGE (u2:Usuario {userId: 'u2', nombre: 'Bruno', edad: 35});
MERGE (u3:Usuario {userId: 'u3', nombre: 'Carla', edad: 22});
MERGE (u4:Usuario {userId: 'u4', nombre: 'Daniel', edad: 41});
MERGE (u5:Usuario {userId: 'u5', nombre: 'Elena', edad: 30});
MERGE (u6:Usuario {userId: 'u6', nombre: 'Fabian', edad: 29});
MERGE (u7:Usuario {userId: 'u7', nombre: 'Gabriela', edad: 33});
MERGE (u8:Usuario {userId: 'u8', nombre: 'Hector', edad: 27});
MERGE (u9:Usuario {userId: 'u9', nombre: 'Iris', edad: 38});
MERGE (u10:Usuario {userId: 'u10', nombre: 'Juan', edad: 25});

// ======================================================================
// PASO 2: Crear Nodos de Ciudades
// (Necesarios para Prim, Kruskal, Branch & Bound)
// ======================================================================
MERGE (c1:Ciudad {nombre: 'Buenos Aires'});
MERGE (c2:Ciudad {nombre: 'Cordoba'});
MERGE (c3:Ciudad {nombre: 'Rosario'});
MERGE (c4:Ciudad {nombre: 'Mendoza'});
MERGE (c5:Ciudad {nombre: 'Madrid'}); // Un usuario internacional

// ======================================================================
// PASO 3: Crear Nodos de Intereses
// (Necesarios para algoritmos Greedy)
// ======================================================================
MERGE (i1:Interes {nombre: 'Programacion'});
MERGE (i2:Interes {nombre: 'Videojuegos'});
MERGE (i3:Interes {nombre: 'Musica'});
MERGE (i4:Interes {nombre: 'Senderismo'});
MERGE (i5:Interes {nombre: 'Cocina'});

// ======================================================================
// PASO 4: Crear Nodos de Publicaciones
// (Necesarios para Programación Dinámica - Problema de la Mochila)
// 'engagementScore' es el 'valor', 'tiempoLectura' es el 'peso'
// ======================================================================
MERGE (p1:Publicacion {postId: 'p1', engagementScore: 80, tiempoLectura: 3});
MERGE (p2:Publicacion {postId: 'p2', engagementScore: 40, tiempoLectura: 2});
MERGE (p3:Publicacion {postId: 'p3', engagementScore: 100, tiempoLectura: 4});
MERGE (p4:Publicacion {postId: 'p4', engagementScore: 120, tiempoLectura: 5});
MERGE (p5:Publicacion {postId: 'p5', engagementScore: 30, tiempoLectura: 1});
MERGE (p6:Publicacion {postId: 'p6', engagementScore: 75, tiempoLectura: 3});

// ======================================================================
// PASO 5: Crear Relaciones de Amistad (ES_AMIGO_DE)
// (Para BFS, DFS, Dijkstra, Backtracking)
// La propiedad 'peso' simula la "cercanía" o "afinidad" (menor es mejor)
// ======================================================================
MATCH (u1:Usuario {userId: 'u1'}), (u2:Usuario {userId: 'u2'}) MERGE (u1)-[:ES_AMIGO_DE {peso: 5}]->(u2) MERGE (u2)-[:ES_AMIGO_DE {peso: 5}]->(u1);
MATCH (u1:Usuario {userId: 'u1'}), (u3:Usuario {userId: 'u3'}) MERGE (u1)-[:ES_AMIGO_DE {peso: 2}]->(u3) MERGE (u3)-[:ES_AMIGO_DE {peso: 2}]->(u1);
MATCH (u2:Usuario {userId: 'u2'}), (u4:Usuario {userId: 'u4'}) MERGE (u2)-[:ES_AMIGO_DE {peso: 8}]->(u4) MERGE (u4)-[:ES_AMIGO_DE {peso: 8}]->(u2);
MATCH (u3:Usuario {userId: 'u3'}), (u4:Usuario {userId: 'u4'}) MERGE (u3)-[:ES_AMIGO_DE {peso: 1}]->(u4) MERGE (u4)-[:ES_AMIGO_DE {peso: 1}]->(u1);
MATCH (u3:Usuario {userId: 'u3'}), (u5:Usuario {userId: 'u5'}) MERGE (u3)-[:ES_AMIGO_DE {peso: 10}]->(u5) MERGE (u5)-[:ES_AMIGO_DE {peso: 10}]->(u3);
MATCH (u5:Usuario {userId: 'u5'}), (u6:Usuario {userId: 'u6'}) MERGE (u5)-[:ES_AMIGO_DE {peso: 3}]->(u6) MERGE (u6)-[:ES_AMIGO_DE {peso: 3}]->(u5);
MATCH (u6:Usuario {userId: 'u6'}), (u7:Usuario {userId: 'u7'}) MERGE (u6)-[:ES_AMIGO_DE {peso: 2}]->(u7) MERGE (u7)-[:ES_AMIGO_DE {peso: 2}]->(u6);
MATCH (u7:Usuario {userId: 'u7'}), (u8:Usuario {userId: 'u8'}) MERGE (u7)-[:ES_AMIGO_DE {peso: 4}]->(u8) MERGE (u8)-[:ES_AMIGO_DE {peso: 4}]->(u7);
MATCH (u8:Usuario {userId: 'u8'}), (u9:Usuario {userId: 'u9'}) MERGE (u8)-[:ES_AMIGO_DE {peso: 6}]->(u9) MERGE (u9)-[:ES_AMIGO_DE {peso: 6}]->(u8);
MATCH (u9:Usuario {userId: 'u9'}), (u10:Usuario {userId: 'u10'}) MERGE (u9)-[:ES_AMIGO_DE {peso: 1}]->(u10) MERGE (u10)-[:ES_AMIGO_DE {peso: 1}]->(u9);
MATCH (u1:Usuario {userId: 'u1'}), (u10:Usuario {userId: 'u10'}) MERGE (u1)-[:ES_AMIGO_DE {peso: 3}]->(u10) MERGE (u10)-[:ES_AMIGO_DE {peso: 3}]->(u1);

// ======================================================================
// PASO 6: Crear Relaciones de Rutas Físicas (RUTA_ENTRE)
// (Para Prim, Kruskal, Branch & Bound)
// 'distancia' es el peso para el Árbol de Recubrimiento Mínimo
// ======================================================================
MATCH (c1:Ciudad {nombre: 'Buenos Aires'}), (c2:Ciudad {nombre: 'Cordoba'}) MERGE (c1)-[:RUTA_ENTRE {distancia: 700}]->(c2) MERGE (c2)-[:RUTA_ENTRE {distancia: 700}]->(c1);
MATCH (c1:Ciudad {nombre: 'Buenos Aires'}), (c3:Ciudad {nombre: 'Rosario'}) MERGE (c1)-[:RUTA_ENTRE {distancia: 300}]->(c3) MERGE (c3)-[:RUTA_ENTRE {distancia: 300}]->(c1);
MATCH (c1:Ciudad {nombre: 'Buenos Aires'}), (c4:Ciudad {nombre: 'Mendoza'}) MERGE (c1)-[:RUTA_ENTRE {distancia: 1050}]->(c4) MERGE (c4)-[:RUTA_ENTRE {distancia: 1050}]->(c1);
MATCH (c2:Ciudad {nombre: 'Cordoba'}), (c3:Ciudad {nombre: 'Rosario'}) MERGE (c2)-[:RUTA_ENTRE {distancia: 400}]->(c3) MERGE (c3)-[:RUTA_ENTRE {distancia: 400}]->(c2);
MATCH (c2:Ciudad {nombre: 'Cordoba'}), (c4:Ciudad {nombre: 'Mendoza'}) MERGE (c2)-[:RUTA_ENTRE {distancia: 450}]->(c4) MERGE (c4)-[:RUTA_ENTRE {distancia: 450}]->(c2);
MATCH (c3:Ciudad {nombre: 'Rosario'}), (c4:Ciudad {nombre: 'Mendoza'}) MERGE (c3)-[:RUTA_ENTRE {distancia: 800}]->(c4) MERGE (c4)-[:RUTA_ENTRE {distancia: 800}]->(c3);
MATCH (c1:Ciudad {nombre: 'Buenos Aires'}), (c5:Ciudad {nombre: 'Madrid'}) MERGE (c1)-[:RUTA_ENTRE {distancia: 10000}]->(c5) MERGE (c5)-[:RUTA_ENTRE {distancia: 10000}]->(c1);

// ======================================================================
// PASO 7: Asignar relaciones de Intereses (TIENE_INTERES)
// (Para Greedy: "Recomendar amigos por intereses en común")
// ======================================================================
MATCH (u:Usuario {userId: 'u1'}), (i:Interes {nombre: 'Programacion'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u1'}), (i:Interes {nombre: 'Videojuegos'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u2'}), (i:Interes {nombre: 'Programacion'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u2'}), (i:Interes {nombre: 'Cocina'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u3'}), (i:Interes {nombre: 'Musica'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u4'}), (i:Interes {nombre: 'Programacion'}) MERGE (u)-[:TIANE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u4'}), (i:Interes {nombre: 'Musica'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u5'}), (i:Interes {nombre: 'Senderismo'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u6'}), (i:Interes {nombre: 'Senderismo'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u6'}), (i:Interes {nombre: 'Cocina'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u7'}), (i:Interes {nombre: 'Videojuegos'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u8'}), (i:Interes {nombre: 'Musica'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u9'}), (i:Interes {nombre: 'Programacion'}) MERGE (u)-[:TIENE_INTERES]->(i);
MATCH (u:Usuario {userId: 'u10'}), (i:Interes {nombre: 'Cocina'}) MERGE (u)-[:TIENE_INTERES]->(i);

// ======================================================================
// PASO 8: Asignar Publicaciones a Usuarios (CREO)
// (Para Programación Dinámica)
// ======================================================================
MATCH (u:Usuario {userId: 'u1'}), (p:Publicacion {postId: 'p1'}) MERGE (u)-[:CREO]->(p);
MATCH (u:Usuario {userId: 'u1'}), (p:Publicacion {postId: 'p2'}) MERGE (u)-[:CREO]->(p);
MATCH (u:Usuario {userId: 'u2'}), (p:Publicacion {postId: 'p3'}) MERGE (u)-[:CREO]->(p);
MATCH (u:Usuario {userId: 'u3'}), (p:Publicacion {postId: 'p4'}) MERGE (u)-[:CREO]->(p);
MATCH (u:Usuario {userId: 'u3'}), (p:Publicacion {postId: 'p5'}) MERGE (u)-[:CREO]->(p);
MATCH (u:Usuario {userId: 'u4'}), (p:Publicacion {postId: 'p6'}) MERGE (u)-[:CREO]->(p);

// ======================================================================
// PASO 9: Asignar Usuarios a Ciudades (VIVE_EN)
// ======================================================================
MATCH (u:Usuario {userId: 'u1'}), (c:Ciudad {nombre: 'Buenos Aires'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u2'}), (c:Ciudad {nombre: 'Cordoba'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u3'}), (c:Ciudad {nombre: 'Rosario'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u4'}), (c:Ciudad {nombre: 'Buenos Aires'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u5'}), (c:Ciudad {nombre: 'Mendoza'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u6'}), (c:Ciudad {nombre: 'Cordoba'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u7'}), (c:Ciudad {nombre: 'Rosario'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u8'}), (c:Ciudad {nombre: 'Buenos Aires'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u9'}), (c:Ciudad {nombre: 'Mendoza'}) MERGE (u)-[:VIVE_EN]->(c);
MATCH (u:Usuario {userId: 'u10'}), (c:Ciudad {nombre: 'Madrid'}) MERGE (u)-[:VIVE_EN]->(c);

// ======================================================================
// PASO 1: Crear Nodos de Usuario
// ======================================================================
MERGE (u1:Usuario {userId: 'u1', nombre: 'Ana'});
MERGE (u2:Usuario {userId: 'u2', nombre: 'Bruno'});
MERGE (u3:Usuario {userId: 'u3', nombre: 'Carla'});
MERGE (u4:Usuario {userId: 'u4', nombre: 'Daniel'});
MERGE (u5:Usuario {userId: 'u5', nombre: 'Elena'});
MERGE (u6:Usuario {userId: 'u6', nombre: 'Fabian'});
MERGE (u7:Usuario {userId: 'u7', nombre: 'Gabi'});

// ======================================================================
// PASO 2: Crear Relaciones (Grafo dirigido)
// IMPORTANTE: Creamos relaciones en AMBAS direcciones
// para simular la amistad "no dirigida".
// ======================================================================

// --- El "Camino Óptimo" para Dijkstra ---
// Ana <-> Bruno (Peso bajo)
MATCH (u1:Usuario {userId: 'u1'}), (u2:Usuario {userId: 'u2'})
MERGE (u1)-[:ES_AMIGO_DE {peso: 4}]->(u2)
MERGE (u2)-[:ES_AMIGO_DE {peso: 4}]->(u1);

// Bruno <-> Carla (Peso bajo)
MATCH (u2:Usuario {userId: 'u2'}), (u3:Usuario {userId: 'u3'})
MERGE (u2)-[:ES_AMIGO_DE {peso: 5}]->(u3)
MERGE (u3)-[:ES_AMIGO_DE {peso: 5}]->(u2);

// --- El "Camino Trampa" para Dijkstra ---
// Ana <-> Carla (Peso alto)
MATCH (u1:Usuario {userId: 'u1'}), (u3:Usuario {userId: 'u3'})
MERGE (u1)-[:ES_AMIGO_DE {peso: 15}]->(u3)
MERGE (u3)-[:ES_AMIGO_DE {peso: 15}]->(u1);

// --- Ramas adicionales para BFS/DFS ---
// Bruno <-> Daniel (Conectado a la rama principal)
MATCH (u2:Usuario {userId: 'u2'}), (u4:Usuario {userId: 'u4'})
MERGE (u2)-[:ES_AMIGO_DE {peso: 2}]->(u4)
MERGE (u4)-[:ES_AMIGO_DE {peso: 2}]->(u2);

// Daniel <-> Elena (Profundidad para DFS)
MATCH (u4:Usuario {userId: 'u4'}), (u5:Usuario {userId: 'u5'})
MERGE (u4)-[:ES_AMIGO_DE {peso: 3}]->(u5)
MERGE (u5)-[:ES_AMIGO_DE {peso: 3}]->(u4);

// Daniel <-> Fabian (Otra rama)
MATCH (u4:Usuario {userId: 'u4'}), (u6:Usuario {userId: 'u6'})
MERGE (u4)-[:ES_AMIGO_DE {peso: 6}]->(u6)
MERGE (u6)-[:ES_AMIGO_DE {peso: 6}]->(u4);

// Ana <-> Gabi (Una rama simple desde el inicio para "anchura" de BFS)
MATCH (u1:Usuario {userId: 'u1'}), (u7:Usuario {userId: 'u7'})
MERGE (u1)-[:ES_AMIGO_DE {peso: 7}]->(u7)
MERGE (u7)-[:ES_AMIGO_DE {peso: 7}]->(u1);
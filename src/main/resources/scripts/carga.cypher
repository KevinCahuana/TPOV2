// ======================================================================
// PASO 1: Crear Nodos de Usuarios, Ciudades e Intereses
// ======================================================================
// Usuarios
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

// Ciudades
MERGE (:Ciudad {nombre: 'Buenos Aires'});
MERGE (:Ciudad {nombre: 'Cordoba'});
MERGE (:Ciudad {nombre: 'Rosario'});
MERGE (:Ciudad {nombre: 'Mendoza'});
MERGE (:Ciudad {nombre: 'Madrid'});

// Intereses (15 en total)
MERGE (:Interes {nombre: 'Programacion'});
MERGE (:Interes {nombre: 'Videojuegos'});
MERGE (:Interes {nombre: 'Musica'});
MERGE (:Interes {nombre: 'Senderismo'});
MERGE (:Interes {nombre: 'Cocina'});
MERGE (:Interes {nombre: 'Cine'});
MERGE (:Interes {nombre: 'Fotografia'});
MERGE (:Interes {nombre: 'Viajes'});
MERGE (:Interes {nombre: 'Deportes'});
MERGE (:Interes {nombre: 'Lectura'});
MERGE (:Interes {nombre: 'Arte'});
MERGE (:Interes {nombre: 'Tecnologia'});
MERGE (:Interes {nombre: 'Animales'});
MERGE (:Interes {nombre: 'Jardineria'});
MERGE (:Interes {nombre: 'Baile'});

// ======================================================================
// PASO 2: Crear Nodos de Publicaciones (26 en total)
// ======================================================================
// Publicaciones originales
MERGE (:Publicacion {postId: 'p1', engagementScore: 80, tiempoLectura: 3});
MERGE (:Publicacion {postId: 'p2', engagementScore: 40, tiempoLectura: 2});
MERGE (:Publicacion {postId: 'p3', engagementScore: 100, tiempoLectura: 4});
MERGE (:Publicacion {postId: 'p4', engagementScore: 120, tiempoLectura: 5});
MERGE (:Publicacion {postId: 'p5', engagementScore: 30, tiempoLectura: 1});
MERGE (:Publicacion {postId: 'p6', engagementScore: 75, tiempoLectura: 3});
// 20 nuevas publicaciones
MERGE (:Publicacion {postId: 'p7', engagementScore: 90, tiempoLectura: 4});
MERGE (:Publicacion {postId: 'p8', engagementScore: 50, tiempoLectura: 2});
MERGE (:Publicacion {postId: 'p9', engagementScore: 110, tiempoLectura: 5});
MERGE (:Publicacion {postId: 'p10', engagementScore: 25, tiempoLectura: 1});
MERGE (:Publicacion {postId: 'p11', engagementScore: 85, tiempoLectura: 3});
MERGE (:Publicacion {postId: 'p12', engagementScore: 65, tiempoLectura: 12});
MERGE (:Publicacion {postId: 'p13', engagementScore: 130, tiempoLectura: 6});
MERGE (:Publicacion {postId: 'p14', engagementScore: 45, tiempoLectura: 2});
MERGE (:Publicacion {postId: 'p15', engagementScore: 95, tiempoLectura: 14});
MERGE (:Publicacion {postId: 'p16', engagementScore: 55, tiempoLectura: 3});
MERGE (:Publicacion {postId: 'p17', engagementScore: 150, tiempoLectura: 7});
MERGE (:Publicacion {postId: 'p18', engagementScore: 20, tiempoLectura: 1});
MERGE (:Publicacion {postId: 'p19', engagementScore: 70, tiempoLectura: 3});
MERGE (:Publicacion {postId: 'p20', engagementScore: 105, tiempoLectura: 5});
MERGE (:Publicacion {postId: 'p21', engagementScore: 60, tiempoLectura: 2});
MERGE (:Publicacion {postId: 'p22', engagementScore: 140, tiempoLectura: 6});
MERGE (:Publicacion {postId: 'p23', engagementScore: 35, tiempoLectura: 11});
MERGE (:Publicacion {postId: 'p24', engagementScore: 88, tiempoLectura: 4});
MERGE (:Publicacion {postId: 'p25', engagementScore: 98, tiempoLectura: 24});
MERGE (:Publicacion {postId: 'p26', engagementScore: 115, tiempoLectura: 15});


// ======================================================================
// PASO 3: Asignar Relaciones
// ======================================================================

// --- Relaciones de Amistad (ES_AMIGO_DE) ---
MATCH (u1:Usuario {userId: 'u1'}), (u2:Usuario {userId: 'u2'}) MERGE (u1)-[:ES_AMIGO_DE {peso: 5}]->(u2) MERGE (u2)-[:ES_AMIGO_DE {peso: 5}]->(u1);
MATCH (u1:Usuario {userId: 'u1'}), (u3:Usuario {userId: 'u3'}) MERGE (u1)-[:ES_AMIGO_DE {peso: 2}]->(u3) MERGE (u3)-[:ES_AMIGO_DE {peso: 2}]->(u1);
MATCH (u2:Usuario {userId: 'u2'}), (u4:Usuario {userId: 'u4'}) MERGE (u2)-[:ES_AMIGO_DE {peso: 8}]->(u4) MERGE (u4)-[:ES_AMIGO_DE {peso: 8}]->(u2);
MATCH (u3:Usuario {userId: 'u3'}), (u4:Usuario {userId: 'u4'}) MERGE (u3)-[:ES_AMIGO_DE {peso: 1}]->(u4) MERGE (u4)-[:ES_AMIGO_DE {peso: 1}]->(u3);
MATCH (u3:Usuario {userId: 'u3'}), (u5:Usuario {userId: 'u5'}) MERGE (u3)-[:ES_AMIGO_DE {peso: 10}]->(u5) MERGE (u5)-[:ES_AMIGO_DE {peso: 10}]->(u3);
MATCH (u5:Usuario {userId: 'u5'}), (u6:Usuario {userId: 'u6'}) MERGE (u5)-[:ES_AMIGO_DE {peso: 3}]->(u6) MERGE (u6)-[:ES_AMIGO_DE {peso: 3}]->(u5);
MATCH (u6:Usuario {userId: 'u6'}), (u7:Usuario {userId: 'u7'}) MERGE (u6)-[:ES_AMIGO_DE {peso: 2}]->(u7) MERGE (u7)-[:ES_AMIGO_DE {peso: 2}]->(u6);
MATCH (u7:Usuario {userId: 'u7'}), (u8:Usuario {userId: 'u8'}) MERGE (u7)-[:ES_AMIGO_DE {peso: 4}]->(u8) MERGE (u8)-[:ES_AMIGO_DE {peso: 4}]->(u7);
MATCH (u8:Usuario {userId: 'u8'}), (u9:Usuario {userId: 'u9'}) MERGE (u8)-[:ES_AMIGO_DE {peso: 6}]->(u9) MERGE (u9)-[:ES_AMIGO_DE {peso: 6}]->(u8);
MATCH (u9:Usuario {userId: 'u9'}), (u10:Usuario {userId: 'u10'}) MERGE (u9)-[:ES_AMIGO_DE {peso: 1}]->(u10) MERGE (u10)-[:ES_AMIGO_DE {peso: 1}]->(u9);
MATCH (u1:Usuario {userId: 'u1'}), (u10:Usuario {userId: 'u10'}) MERGE (u1)-[:ES_AMIGO_DE {peso: 3}]->(u10) MERGE (u10)-[:ES_AMIGO_DE {peso: 3}]->(u1);

// --- Relaciones de Intereses (TIENE_INTERES) ---
UNWIND [
    {userId: 'u1', intereses: ['Programacion', 'Videojuegos', 'Musica', 'Senderismo', 'Cocina', 'Cine']},
    {userId: 'u2', intereses: ['Cocina', 'Cine', 'Fotografia', 'Viajes', 'Deportes', 'Lectura', 'Arte']},
    {userId: 'u3', intereses: ['Musica', 'Cine', 'Lectura', 'Arte', 'Baile', 'Fotografia']},
    {userId: 'u4', intereses: ['Programacion', 'Tecnologia', 'Videojuegos', 'Cine', 'Deportes', 'Viajes', 'Fotografia', 'Lectura']},
    {userId: 'u5', intereses: ['Senderismo', 'Viajes', 'Animales', 'Jardineria', 'Fotografia', 'Cocina']},
    {userId: 'u6', intereses: ['Deportes', 'Senderismo', 'Cine', 'Musica']},
    {userId: 'u7', intereses: ['Videojuegos', 'Arte', 'Baile']},
    {userId: 'u8', intereses: ['Musica', 'Lectura', 'Cine', 'Cocina', 'Deportes']},
    {userId: 'u9', intereses: ['Programacion', 'Tecnologia', 'Lectura', 'Arte']},
    {userId: 'u10', intereses: ['Cocina', 'Viajes', 'Deportes', 'Animales', 'Jardineria']}
] AS userData
MATCH (u:Usuario {userId: userData.userId})
UNWIND userData.intereses AS interesNombre
MATCH (i:Interes {nombre: interesNombre})
MERGE (u)-[:TIENE_INTERES]->(i);

// --- Relaciones de Publicaciones (CREO) ---
UNWIND [
    {userId: 'u1', postIds: ['p1', 'p2']},
    {userId: 'u2', postIds: ['p3']},
    {userId: 'u3', postIds: ['p4', 'p5']},
    {userId: 'u4', postIds: ['p6']},
    {userId: 'u5', postIds: ['p7', 'p8', 'p9']},
    {userId: 'u6', postIds: ['p10', 'p11', 'p12', 'p13']},
    {userId: 'u7', postIds: ['p14', 'p15']},
    {userId: 'u8', postIds: ['p16', 'p17', 'p18', 'p19']},
    {userId: 'u9', postIds: ['p20', 'p21', 'p22']},
    {userId: 'u10', postIds: ['p23', 'p24', 'p25', 'p26']}
] AS userPubs
MATCH (u:Usuario {userId: userPubs.userId})
UNWIND userPubs.postIds AS postId
MATCH (p:Publicacion {postId: postId})
MERGE (u)-[:CREO]->(p);

// --- Relaciones de Vivienda (VIVE_EN) ---
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

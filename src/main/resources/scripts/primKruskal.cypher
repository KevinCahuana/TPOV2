// ======================================================================
// PASO 1: Crear Nodos de Ciudad
// ======================================================================
MERGE (c1:Ciudad {ciudadId: 'c1', nombre: 'Buenos Aires'});
MERGE (c2:Ciudad {ciudadId: 'c2', nombre: 'Cordoba'});
MERGE (c3:Ciudad {ciudadId: 'c3', nombre: 'Rosario'});
MERGE (c4:Ciudad {ciudadId: 'c4', nombre: 'Mendoza'});
MERGE (c5:Ciudad {ciudadId: 'c5', nombre: 'Salta'});

// ======================================================================
// PASO 2: Crear Rutas (Aristas con peso de 'distancia')
// ======================================================================
// Buenos Aires <-> Rosario (Muy barata)
MATCH (c1:Ciudad {ciudadId: 'c1'}), (c3:Ciudad {ciudadId: 'c3'})
MERGE (c1)-[r1:RUTA_ENTRE {distancia: 300}]->(c3)
MERGE (c3)-[r2:RUTA_ENTRE {distancia: 300}]->(c1);

// Buenos Aires <-> Cordoba
MATCH (c1:Ciudad {ciudadId: 'c1'}), (c2:Ciudad {ciudadId: 'c2'})
MERGE (c1)-[r1:RUTA_ENTRE {distancia: 700}]->(c2)
MERGE (c2)-[r2:RUTA_ENTRE {distancia: 700}]->(c1);

// Rosario <-> Cordoba (Más barata que BA-Cordoba)
MATCH (c3:Ciudad {ciudadId: 'c3'}), (c2:Ciudad {ciudadId: 'c2'})
MERGE (c3)-[r1:RUTA_ENTRE {distancia: 400}]->(c2)
MERGE (c2)-[r2:RUTA_ENTRE {distancia: 400}]->(c1);

// Cordoba <-> Mendoza
MATCH (c2:Ciudad {ciudadId: 'c2'}), (c4:Ciudad {ciudadId: 'c4'})
MERGE (c2)-[r1:RUTA_ENTRE {distancia: 450}]->(c4)
MERGE (c4)-[r2:RUTA_ENTRE {distancia: 450}]->(c1);

// Mendoza <-> Salta
MATCH (c4:Ciudad {ciudadId: 'c4'}), (c5:Ciudad {ciudadId: 'c5'})
MERGE (c4)-[r1:RUTA_ENTRE {distancia: 500}]->(c5)
MERGE (c5)-[r2:RUTA_ENTRE {distancia: 500}]->(c1);

// Cordoba <-> Salta (La ruta "trampa", más cara que Cba->Mdz->Salta)
MATCH (c2:Ciudad {ciudadId: 'c2'}), (c5:Ciudad {ciudadId: 'c5'})
MERGE (c2)-[r1:RUTA_ENTRE {distancia: 900}]->(c5)
MERGE (c5)-[r2:RUTA_ENTRE {distancia: 900}]->(c1);

// ======================================================================
// PASO 1: Crear 10 Nodos de Ciudad ADICIONALES
// (Se suman a las 5 ciudades existentes)
// ======================================================================
MERGE (c6:Ciudad {ciudadId: 'c6', nombre: 'Bariloche'});
MERGE (c7:Ciudad {ciudadId: 'c7', nombre: 'Ushuaia'});
MERGE (c8:Ciudad {ciudadId: 'c8', nombre: 'Tucuman'});
MERGE (c9:Ciudad {ciudadId: 'c9', nombre: 'Jujuy'});
MERGE (c10:Ciudad {ciudadId: 'c10', nombre: 'Formosa'});
MERGE (c11:Ciudad {ciudadId: 'c11', nombre: 'Mar del Plata'});
MERGE (c12:Ciudad {ciudadId: 'c12', nombre: 'La Plata'});
MERGE (c13:Ciudad {ciudadId: 'c13', nombre: 'Parana'});
MERGE (c14:Ciudad {ciudadId: 'c14', nombre: 'Santa Fe'});
MERGE (c15:Ciudad {ciudadId: 'c15', nombre: 'Neuquen'});

// ======================================================================
// PASO 2: Crear Rutas para conectar el nuevo cluster y
// crear "puentes" con las 5 ciudades originales.
// ======================================================================

// Conectar "Puentes" (Originales <-> Nuevas)
// c1 (BsAs) -> c11 (MDQ) y c12 (La Plata)
MATCH (c1:Ciudad {ciudadId: 'c1'}), (c11:Ciudad {ciudadId: 'c11'}) MERGE (c1)-[r1:RUTA_ENTRE {distancia: 400}]->(c11) MERGE (c11)-[r2:RUTA_ENTRE {distancia: 400}]->(c1);
MATCH (c1:Ciudad {ciudadId: 'c1'}), (c12:Ciudad {ciudadId: 'c12'}) MERGE (c1)-[r1:RUTA_ENTRE {distancia: 60}]->(c12) MERGE (c12)-[r2:RUTA_ENTRE {distancia: 60}]->(c1);

// c3 (Rosario) -> c13 (Parana) y c14 (Santa Fe)
MATCH (c3:Ciudad {ciudadId: 'c3'}), (c13:Ciudad {ciudadId: 'c13'}) MERGE (c3)-[r1:RUTA_ENTRE {distancia: 150}]->(c13) MERGE (c13)-[r2:RUTA_ENTRE {distancia: 150}]->(c3);
MATCH (c3:Ciudad {ciudadId: 'c3'}), (c14:Ciudad {ciudadId: 'c14'}) MERGE (c3)-[r1:RUTA_ENTRE {distancia: 170}]->(c14) MERGE (c14)-[r2:RUTA_ENTRE {distancia: 170}]->(c3);

// c2 (Cordoba) -> c8 (Tucuman)
MATCH (c2:Ciudad {ciudadId: 'c2'}), (c8:Ciudad {ciudadId: 'c8'}) MERGE (c2)-[r1:RUTA_ENTRE {distancia: 550}]->(c8) MERGE (c8)-[r2:RUTA_ENTRE {distancia: 550}]->(c2);

// c4 (Mendoza) -> c15 (Neuquen)
MATCH (c4:Ciudad {ciudadId: 'c4'}), (c15:Ciudad {ciudadId: 'c15'}) MERGE (c4)-[r1:RUTA_ENTRE {distancia: 520}]->(c15) MERGE (c15)-[r2:RUTA_ENTRE {distancia: 520}]->(c4);

// c5 (Salta) -> c9 (Jujuy)
MATCH (c5:Ciudad {ciudadId: 'c5'}), (c9:Ciudad {ciudadId: 'c9'}) MERGE (c5)-[r1:RUTA_ENTRE {distancia: 120}]->(c9) MERGE (c9)-[r2:RUTA_ENTRE {distancia: 120}]->(c5);

// Conectar el "Nuevo Cluster" entre sí (Creando ciclos)
// Cuyo - Sur
MATCH (c15:Ciudad {ciudadId: 'c15'}), (c6:Ciudad {ciudadId: 'c6'}) MERGE (c15)-[r1:RUTA_ENTRE {distancia: 800}]->(c6) MERGE (c6)-[r2:RUTA_ENTRE {distancia: 800}]->(c15);
MATCH (c6:Ciudad {ciudadId: 'c6'}), (c7:Ciudad {ciudadId: 'c7'}) MERGE (c6)-[r1:RUTA_ENTRE {distancia: 1500}]->(c7) MERGE (c7)-[r2:RUTA_ENTRE {distancia: 1500}]->(c6);

// Norte
MATCH (c8:Ciudad {ciudadId: 'c8'}), (c9:Ciudad {ciudadId: 'c9'}) MERGE (c8)-[r1:RUTA_ENTRE {distancia: 200}]->(c9) MERGE (c9)-[r2:RUTA_ENTRE {distancia: 200}]->(c8);
MATCH (c8:Ciudad {ciudadId: 'c8'}), (c10:Ciudad {ciudadId: 'c10'}) MERGE (c8)-[r1:RUTA_ENTRE {distancia: 800}]->(c10) MERGE (c10)-[r2:RUTA_ENTRE {distancia: 800}]->(c8);

// Litoral
MATCH (c13:Ciudad {ciudadId: 'c13'}), (c14:Ciudad {ciudadId: 'c14'}) MERGE (c13)-[r1:RUTA_ENTRE {distancia: 30}]->(c14) MERGE (c14)-[r2:RUTA_ENTRE {distancia: 30}]->(c13);
MATCH (c13:Ciudad {ciudadId: 'c13'}), (c10:Ciudad {ciudadId: 'c10'}) MERGE (c13)-[r1:RUTA_ENTRE {distancia: 600}]->(c10) MERGE (c10)-[r2:RUTA_ENTRE {distancia: 600}]->(c13);

// Costa Atlántica
MATCH (c11:Ciudad {ciudadId: 'c11'}), (c12:Ciudad {ciudadId: 'c12'}) MERGE (c11)-[r1:RUTA_ENTRE {distancia: 350}]->(c12) MERGE (c12)-[r2:RUTA_ENTRE {distancia: 350}]->(c11);

// ======================================================================
// PASO 3: Crear Rutas "Redundantes" (Caras) para forzar a
// Prim y Kruskal a descartarlas.
// ======================================================================
// Conexión directa Sur (pero cara)
MATCH (c15:Ciudad {ciudadId: 'c15'}), (c7:Ciudad {ciudadId: 'c7'})
MERGE (c15)-[r1:RUTA_ENTRE {distancia: 2500}]->(c7)
MERGE (c7)-[r2:RUTA_ENTRE {distancia: 2500}]->(c15);

// Conexión BsAs a Litoral (redundante)
MATCH (c1:Ciudad {ciudadId: 'c1'}), (c14:Ciudad {ciudadId: 'c14'})
MERGE (c1)-[r1:RUTA_ENTRE {distancia: 480}]->(c14)
MERGE (c14)-[r2:RUTA_ENTRE {distancia: 480}]->(c1);

// Conexión Salta a Tucuman (redundante)
MATCH (c5:Ciudad {ciudadId: 'c5'}), (c8:Ciudad {ciudadId: 'c8'})
MERGE (c5)-[r1:RUTA_ENTRE {distancia: 310}]->(c8)
MERGE (c8)-[r2:RUTA_ENTRE {distancia: 310}]->(c5);
# TPO V2 - API de Algoritmos y Grafos

API REST desarrollada con Spring Boot y Neo4j para demostrar la implementación de diversos algoritmos fundamentales, aplicados sobre un modelo de datos de grafos que simula una red social y una red de ciudades.

El proyecto expone endpoints para ejecutar algoritmos de:
- **Búsqueda en Grafos**: BFS y DFS.
- **Camino Más Corto**: Dijkstra.
- **Árbol de Recubrimiento Mínimo**: Prim y Kruskal.
- **Ordenamiento (Divide y Vencerás)**: Quicksort y Mergesort.

---

## Tecnologías Utilizadas

- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.3.1
- **Base de Datos**: Neo4j
- **Acceso a Datos**: Spring Data Neo4j
- **Gestión de Dependencias**: Maven
- **Documentación de API**: Springdoc OpenAPI (Swagger UI)
- **Logging**: Log4j2

---

## Configuración y Puesta en Marcha

### Prerrequisitos

1.  **JDK 17** o superior.
2.  **Maven 3.x** instalado.
3.  Una instancia de **Neo4j Database** en ejecución. La configuración por defecto del proyecto apunta a:
    -   **URI**: `bolt://localhost:7687`
    -   **Usuario**: `neo4j`
    -   **Contraseña**: `46538857`
    (Puedes cambiar estos valores en `src/main/resources/application.properties`).

### Pasos para Ejecutar

1.  **Clonar el Repositorio**:
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd TPOV2
    ```

2.  **Cargar los Datos en Neo4j**:
    -   Abre Neo4j Browser (generalmente en `http://localhost:7474`).
    -   Copia y ejecuta el contenido de los siguientes archivos `.cypher` que se encuentran en `src/main/resources/scripts/`:
        -   `carga.cypher`: Carga la red social de usuarios, sus amistades, intereses y ciudades.
        -   `primKruskal.cypher`: Carga una red de ciudades y rutas con distancias, diseñada para probar los algoritmos de MST.

3.  **Construir el Proyecto**:
    ```bash
    mvn clean install
    ```

4.  **Ejecutar la Aplicación**:
    ```bash
    mvn spring-boot:run
    ```

Una vez ejecutada, la API estará disponible en `http://localhost:8080`.

---

## Documentación de la API (Swagger)

La documentación interactiva de la API está disponible a través de Swagger UI en la siguiente URL:

-   **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## Algoritmos Implementados

A continuación se detallan los algoritmos expuestos en la API.

### Algoritmos de Grafos (`/algoritmos`)

Estos algoritmos operan sobre el grafo de `Usuarios` o `Ciudades`.

#### 1. Búsqueda en Anchura (BFS)
-   **Teoría**: Explora el grafo nivel por nivel. Es ideal para encontrar el camino más corto en términos de número de aristas (no de peso).
-   **Implementación en el Proyecto**: Encuentra todos los usuarios alcanzables (amigos, amigos de amigos, etc.) desde un usuario inicial.
-   **Endpoint**: `GET /algoritmos/bfs/{userId}/alcanzables`

#### 2. Búsqueda en Profundidad (DFS)
-   **Teoría**: Explora tan profundo como sea posible por cada rama antes de retroceder. Es útil para detectar ciclos o simplemente para recorrer un grafo completo.
-   **Implementación en el Proyecto**: Al igual que BFS, encuentra todos los usuarios alcanzables, pero el orden de descubrimiento será diferente, siguiendo la lógica de profundidad.
-   **Endpoint**: `GET /algoritmos/dfs/{userId}/alcanzables`

#### 3. Algoritmo de Dijkstra
-   **Teoría**: Encuentra el camino de menor coste (ponderado) desde un nodo de origen a todos los demás nodos.
-   **Implementación en el Proyecto**: Calcula el "camino de amistad" más cercano entre dos usuarios, utilizando la propiedad `peso` de la relación `:ES_AMIGO_DE` como el coste. Un peso menor significa una amistad más "cercana".
-   **Endpoint**: `GET /algoritmos/dijkstra/camino-minimo`

#### 4. Algoritmo de Prim
-   **Teoría**: Algoritmo voraz que encuentra un Árbol de Recubrimiento Mínimo (MST) para un grafo ponderado no dirigido. Crece el árbol desde un nodo inicial, añadiendo siempre la arista más barata que conecta un nodo del árbol con un nodo fuera de él.
-   **Implementación en el Proyecto**: Opera sobre el grafo de `:Ciudad` y las relaciones `:RUTA_ENTRE`. Calcula la red de rutas de menor `distancia` total que conecta todas las ciudades.
-   **Endpoint**: `GET /algoritmos/prim/mst`

#### 5. Algoritmo de Kruskal
-   **Teoría**: Otro algoritmo voraz para encontrar un MST. Ordena todas las aristas del grafo de menor a mayor peso y las añade al árbol siempre y cuando no formen un ciclo.
-   **Implementación en el Proyecto**: Al igual que Prim, calcula el MST del grafo de `:Ciudad`, pero con una estrategia diferente. Es especialmente eficiente en grafos dispersos.
-   **Endpoint**: `GET /algoritmos/kruskal/mst`

### Algoritmos de Ordenamiento (`/algoritmos/ordenamiento`)

Estos algoritmos se basan en la estrategia **Divide y Vencerás** y se aplican sobre la lista completa de usuarios obtenidos de la base de datos.

#### 1. Quicksort
-   **Teoría**: Elige un elemento como "pivote" y particiona la lista, colocando los elementos menores a un lado y los mayores al otro. Luego, aplica el mismo proceso recursivamente a las dos sub-listas. Es conocido por su eficiencia en el caso promedio.
-   **Implementación en el Proyecto**: Ordena la lista de todos los `:Usuario`s. Se puede elegir ordenar por `nombre` (alfabéticamente) o por `edad` (numéricamente).
-   **Endpoint**: `GET /algoritmos/ordenamiento/quicksort`

#### 2. Mergesort
-   **Teoría**: Divide la lista a la mitad repetidamente hasta que solo quedan listas de un elemento. Luego, fusiona (merge) esas sub-listas de manera ordenada hasta reconstruir la lista completa. Su rendimiento es muy estable.
-   **Implementación en el Proyecto**: Ofrece una alternativa a Quicksort para ordenar la lista de `:Usuario`s por `nombre` o `edad`.
-   **Endpoint**: `GET /algoritmos/ordenamiento/mergesort`

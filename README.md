# TPO V2 - API de Algoritmos y Grafos

API REST desarrollada con Spring Boot y Neo4j para demostrar la implementación de diversos algoritmos fundamentales, aplicados sobre un modelo de datos de grafos que simula una red social y una red de ciudades.

El proyecto expone endpoints para ejecutar algoritmos de:
- **Búsqueda en Grafos**: BFS y DFS.
- **Camino Más Corto**: Dijkstra.
- **Árbol de Recubrimiento Mínimo**: Prim y Kruskal.
- **Ordenamiento (Divide y Vencerás)**: Quicksort y Mergesort.
- **Búsqueda Exhaustiva**: Backtracking.
- **Optimización Combinatoria**: Branch & Bound, Programación Dinámica.
- **Estrategias Voraces**: Greedy.

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
    -   **Importante**: Limpia la base de datos ejecutando `MATCH (n) DETACH DELETE n;`.
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

## Análisis de Implementación y Complejidad

A continuación se detallan los algoritmos implementados, su lógica en el proyecto y su análisis de complejidad computacional.

### Búsqueda en Grafos (`GrafoService`)

- **BFS (Búsqueda en Anchura)**
  - **Implementación**: Utiliza una `Queue` para una exploración por niveles y un `HashSet` para evitar ciclos. Es un enfoque iterativo.
  - **Complejidad**: **O(V + E)**, donde V es el número de usuarios y E el de amistades. Cada usuario y amistad se procesa una vez.

- **DFS (Búsqueda en Profundidad)**
  - **Implementación**: Utiliza recursión para explorar cada rama hasta el final. Un `HashSet` de visitados previene ciclos infinitos.
  - **Complejidad**: **O(V + E)**, por las mismas razones que BFS.

### Árbol de Recubrimiento Mínimo (`GrafoService`)

- **Algoritmo de Prim**
  - **Implementación**: Construye una lista de adyacencia y usa una `PriorityQueue` para seleccionar siempre la arista de menor peso que conecta un nodo visitado con uno no visitado.
  - **Complejidad**: **O(E log V)**. La eficiencia proviene de las operaciones logarítmicas de la cola de prioridad.

- **Algoritmo de Kruskal**
  - **Implementación**: Ordena todas las aristas del grafo por peso y las añade al árbol si no forman un ciclo, lo cual se verifica eficientemente con una estructura Union-Find.
  - **Complejidad**: **O(E log E)**. El paso dominante es la ordenación inicial de todas las aristas.

### Ordenamiento (`OrdenamientoService`)

- **Quicksort**
  - **Implementación**: Algoritmo recursivo que particiona el array en torno a un pivote.
  - **Complejidad**:
    - **Caso Promedio/Mejor**: **O(N log N)**.
    - **Peor Caso**: **O(N²)**, si los pivotes elegidos son consistentemente malos (ej., en una lista ya ordenada).

- **Mergesort**
  - **Implementación**: Algoritmo recursivo que divide la lista por la mitad y luego fusiona las mitades ordenadas.
  - **Complejidad**: **O(N log N)** en todos los casos, lo que lo hace más predecible que Quicksort.

### Algoritmos de Búsqueda de Caminos

- **Dijkstra (`GrafoService`)**
  - **Implementación**: Similar a Prim, usa una `PriorityQueue` para explorar siempre el camino parcial de menor coste acumulado desde el origen.
  - **Complejidad**: **O(E log V)**, al igual que Prim.

- **Backtracking (`BacktrackingService`)**
  - **Implementación**: Búsqueda en profundidad (DFS) recursiva que explora todos los caminos posibles y los "poda" si violan restricciones (coste máximo o nodos repetidos).
  - **Complejidad**: **O(b^d)** (Exponencial), donde `b` es el factor de ramificación y `d` la profundidad. Es exhaustivo pero ineficiente para grafos grandes.

- **Ramificación y Poda (Branch & Bound) (`BranchAndBoundService`)**
  - **Implementación**: Resuelve el problema del camino óptimo con paradas obligatorias. Usa una `PriorityQueue` para explorar siempre el camino parcial más prometedor (de menor coste). La clave es la poda: descarta ramas cuyo coste actual ya supera la mejor solución encontrada hasta ahora.
  - **Complejidad**: **O(b^d)** (Exponencial) en el peor de los casos, pero en la práctica es mucho más rápido que el backtracking gracias a su poda "inteligente".

### Otros Algoritmos

- **Greedy (`GreedyService`)**
  - **Implementación**: Para recomendar un amigo, itera sobre todos los usuarios no-amigos y elige "vorazmente" al que maximiza una métrica simple: el número de intereses en común.
  - **Complejidad**: **O(U * I_max)**, donde U es el número de usuarios e I_max el número máximo de intereses por usuario.

- **Programación Dinámica (`DinamicaService`)**
  - **Implementación**: Resuelve el problema de la mochila 0/1. Construye una tabla 2D de tamaño `N x W` para almacenar las soluciones óptimas de los subproblemas.
  - **Complejidad**: **O(N * W)**, donde N es el número de publicaciones y W el tiempo máximo. Es una complejidad pseudo-polinomial.

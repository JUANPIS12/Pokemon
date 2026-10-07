## Autores

- Juan Pablo Osorio Arias
- Jhoan Londoño


# Pokémon

Aplicación de escritorio desarrollada en Java Swing que simula un combate entre dos Pokémon utilizando información obtenida en tiempo real desde la API pública PokeAPI.

El usuario puede seleccionar dos Pokémon mediante su nombre o mediante una selección aleatoria. La aplicación muestra la imagen, nombre, tipos y estadísticas principales de cada Pokémon y permite iniciar un combate por turnos.

## Tecnologías utilizadas

- Java 11+
- Java Swing
- IntelliJ IDEA
- PokeAPI
- Java HttpClient
- JSON con la librería `org.json`

## Funcionalidades

- Selección de dos Pokémon.
- Búsqueda de Pokémon por nombre.
- Selección aleatoria de Pokémon.
- Consulta de información directamente desde PokeAPI.
- Visualización de imagen, nombre, tipos, HP, ataque, defensa y velocidad.
- Determinación del primer turno según la velocidad.
- Combate por turnos.
- Cálculo y aplicación de daño.
- Actualización del HP durante el combate.
- Registro de ataques y daños en el historial de batalla.
- Determinación del Pokémon ganador.
- Manejo de errores cuando el Pokémon no existe o se presenta un problema de conexión.

## Diseño del proyecto

El proyecto está organizado utilizando programación orientada a objetos, separando las responsabilidades principales en diferentes clases. La clase `Pokemon` representa la información y el estado de cada Pokémon. La clase `PokeApiClient` se encarga de realizar las solicitudes HTTP a PokeAPI, procesar la respuesta JSON y convertir los datos obtenidos en objetos `Pokemon`.

La lógica del combate se encuentra en la clase `Battle`, que determina qué Pokémon comienza según su velocidad, calcula el daño, cambia los turnos y determina el ganador. La clase `PokeApiGui` se encarga de la interfaz gráfica desarrollada con Java Swing y conecta las acciones del usuario con la lógica del programa. Para realizar las consultas a la API sin bloquear la interfaz gráfica se utiliza `SwingWorker`.

## Estructura del proyecto

```text
Pokemon
│
├── .gitignore
├── README.md
│
└── src
    ├── Battle.java
    ├── Main.java
    ├── PokeApi.java
    ├── PokeApiClient.java
    ├── PokeApiGui.form
    ├── PokeApiGui.java
    └── Pokemon.java
# Sistema de Inventario

Aplicación web para la gestión de productos y categorías, desarrollada con Java y Spring Boot. El sistema permite administrar el inventario desde una interfaz web y expone una API REST para realizar operaciones CRUD.

El proyecto se desarrolló como práctica de arquitectura backend, persistencia de datos, validación de entradas y pruebas automatizadas.

## Funcionalidades

### Productos
- Crear, consultar, actualizar y eliminar productos.
- Buscar productos por nombre.
- Filtrar productos por categoría.
- Administrar precios y existencias.
- Identificar productos con stock bajo.
- Validar datos y mostrar mensajes de error.

### Categorías
- Crear, consultar, actualizar y eliminar categorías.
- Asociar productos con sus categorías.
- Validar la información recibida.
- Gestionar errores cuando una categoría no existe.

### Dashboard
- Total de productos y categorías.
- Cantidad total de unidades en inventario.
- Valor total del inventario.
- Listado de productos con stock bajo.

### Interfaz web
- Diseño adaptable a diferentes tamaños de pantalla.
- Formularios y modales para crear y editar registros.
- Búsqueda y filtros dinámicos.
- Notificaciones para confirmar operaciones y mostrar errores.

## Tecnologías utilizadas

**Backend**
- Java 21
- Spring Boot 4.0.6
- Spring MVC
- Spring Data JPA
- Hibernate
- Jakarta Bean Validation
- Lombok

**Base de datos**
- MySQL
- H2 para pruebas automatizadas

**Frontend**
- HTML5
- CSS3
- JavaScript
- Font Awesome

**Herramientas**
- Maven
- Git y GitHub
- SpringDoc OpenAPI
- Swagger UI
- JUnit y Mockito

## Arquitectura

El backend está organizado en capas para separar las responsabilidades de la aplicación.

```text
Cliente web
    |
    | HTTP / JSON
    v
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
Base de datos
```

Los controladores gestionan las peticiones HTTP, los servicios contienen la lógica de negocio y los repositorios se encargan del acceso a los datos mediante Spring Data JPA.

Las entidades representan los datos persistidos y los DTOs permiten definir la información que recibe y devuelve la API. El manejo de excepciones está centralizado para mantener respuestas consistentes.

## Estructura del proyecto

```text
inventario/
├── src/
│   ├── main/
│   │   ├── java/com/ricardo/inventario/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── static/
│   │       └── application.properties
│   └── test/
│       ├── java/
│       └── resources/
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Requisitos

- Java 21
- MySQL
- Git

El proyecto incluye Maven Wrapper, por lo que no es necesario instalar Maven globalmente.

## Instalación y ejecución

### 1. Clonar el repositorio

Reemplaza la URL de ejemplo por la dirección real del repositorio.

```bash
git clone <URL_DEL_REPOSITORIO>
cd inventario
```

### 2. Crear la base de datos

Ejecuta la siguiente instrucción en MySQL:

```sql
CREATE DATABASE inventario;
```

### 3. Configurar las variables de entorno

La aplicación obtiene las credenciales de MySQL mediante estas variables:

| Variable | Descripción |
|---|---|
| `DB_URL` | URL de conexión JDBC |
| `DB_USER` | Usuario de MySQL |
| `DB_PASSWORD` | Contraseña de MySQL |

En PowerShell puedes configurarlas para la sesión actual:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/inventario"
$env:DB_USER = "tu_usuario"
$env:DB_PASSWORD = "tu_contraseña"
```

Utiliza las credenciales de tu entorno local. No guardes contraseñas reales en el repositorio.

### 4. Ejecutar la aplicación

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en:

http://localhost:8008

## Endpoints de la API

### Productos

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/productos` | Obtener todos los productos |
| GET | `/productos/{id}` | Obtener un producto por ID |
| POST | `/productos` | Crear un producto |
| PUT | `/productos/{id}` | Actualizar un producto |
| DELETE | `/productos/{id}` | Eliminar un producto |

### Categorías

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/categorias` | Obtener todas las categorías |
| GET | `/categorias/{id}` | Obtener una categoría por ID |
| POST | `/categorias` | Crear una categoría |
| PUT | `/categorias/{id}` | Actualizar una categoría |
| DELETE | `/categorias/{id}` | Eliminar una categoría |

## Documentación de la API

El proyecto utiliza SpringDoc OpenAPI para documentar los endpoints.

Con la aplicación en ejecución, accede a Swagger UI:

http://localhost:8008/swagger-ui/index.html

Desde allí puedes consultar y probar las operaciones disponibles.

## Validaciones y manejo de errores

La API utiliza Jakarta Bean Validation para validar los datos recibidos. Entre las reglas implementadas se encuentran:

- El nombre del producto es obligatorio.
- El precio debe ser mayor que cero.
- La categoría es obligatoria al registrar un producto.
- El stock no puede ser negativo.
- El nombre de la categoría es obligatorio.
- Los recursos inexistentes devuelven respuestas HTTP 404.
- Los errores de validación devuelven respuestas HTTP 400.

## Pruebas

El proyecto incluye pruebas para controladores, servicios e integración HTTP.

Para ejecutar la suite completa en Windows:

```powershell
.\mvnw.cmd clean test
```

En Linux o macOS:

```bash
./mvnw clean test
```

En la última ejecución se completaron 37 pruebas sin fallos ni errores.

Las pruebas de integración utilizan H2 en memoria para mantener los datos de prueba separados de la base de datos de desarrollo.

## Configuración de persistencia

La conexión a la base de datos se define en `src/main/resources/application.properties`:

```properties
spring.application.name=inventario
server.port=8008

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

La propiedad `spring.jpa.hibernate.ddl-auto=update` permite actualizar el esquema durante el desarrollo. Para producción sería recomendable utilizar migraciones controladas y revisar la configuración de Hibernate.

## Aprendizajes

Durante el desarrollo se practicaron los siguientes conceptos:

- Desarrollo de APIs REST con Spring Boot.
- Arquitectura por capas y separación de responsabilidades.
- Persistencia con JPA e Hibernate.
- Relaciones entre entidades.
- Uso de DTOs.
- Validación de datos y manejo global de excepciones.
- Consumo de APIs desde JavaScript.
- Pruebas automatizadas con JUnit y Mockito.
- Gestión del código con Git y GitHub.

## Autor

**Ricardo Gamez Elizalde**

Proyecto desarrollado como parte de mi aprendizaje en desarrollo de software, con enfoque en Java, Spring Boot y desarrollo de aplicaciones web.

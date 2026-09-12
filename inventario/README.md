# 📦 Sistema de Inventario

Sistema web de gestión de inventario desarrollado con **Java y Spring Boot**.

La aplicación permite administrar productos y categorías mediante una API REST, además de proporcionar una interfaz web para consultar, crear, editar y eliminar información del inventario.

---

## ✨ Funcionalidades

### 📦 Gestión de productos
- Crear, consultar, editar y eliminar productos
- Buscar productos por nombre
- Filtrar productos por categoría
- Control de stock
- Validación de datos
- Productos con stock bajo
- Notificaciones mediante Toasts
- Manejo de errores

### 🏷️ Gestión de categorías
- Crear, consultar, editar y eliminar categorías
- Validación de datos
- Manejo de errores

### 📊 Dashboard
- Total de productos
- Total de categorías
- Stock total
- Valor total del inventario
- Productos con stock bajo

### 📱 Interfaz
- Diseño responsive
- Menú lateral
- Modales para crear y editar
- Estados vacíos
- Notificaciones visuales
- HTML, CSS y JavaScript

---

## 🛠️ Tecnologías

### Backend
- **Java 21**
- **Spring Boot 4.0.6**
- Spring MVC
- Spring Data JPA
- Hibernate
- MySQL
- Bean Validation
- Lombok
- Maven

### API
- REST API
- Swagger / OpenAPI
- SpringDoc OpenAPI 2.8.9

### Frontend
- HTML5
- CSS3
- JavaScript
- Font Awesome

---

## 🏗️ Arquitectura

El proyecto utiliza una arquitectura por capas:

```text
Cliente
   │
   │ HTTP / JSON
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
MySQL
```

Los DTOs controlan los datos que entran y salen de la API, mientras que las entidades representan la información almacenada en la base de datos.

---

## 📁 Estructura del proyecto

```text
inventario/
│
├── .github/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ricardo/inventario/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── img/
│   │       ├── static/
│   │       └── templates/
│   │
│   └── test/
│       └── java/
│           └── com/ricardo/inventario/
│
├── pom.xml
└── README.md
```

### 📂 Principales paquetes

| Paquete | Responsabilidad |
|---|---|
| `controller` | Maneja las peticiones HTTP |
| `service` | Contiene la lógica de negocio |
| `repository` | Acceso a la base de datos mediante JPA |
| `model` | Entidades del sistema |
| `dto` | Objetos utilizados para transferir datos |
| `exception` | Manejo de errores y excepciones |

---

## ⚙️ Requisitos

- Java 21
- Maven
- MySQL
- Git

---

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd inventario
```

### 2. Crear la base de datos

```sql
CREATE DATABASE inventario;
```

### 3. Configurar las variables de entorno

La aplicación utiliza:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Ejemplo:

```text
DB_URL=jdbc:mysql://localhost:3306/inventario
DB_USER=tu_usuario
DB_PASSWORD=tu_password
```

Las credenciales no se almacenan directamente en el código.

### 4. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

El servidor estará disponible en:

```text
http://localhost:8008
```

---

## 🔌 Endpoints

### Productos

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/productos` | Obtener todos los productos |
| GET | `/productos/{id}` | Obtener un producto |
| POST | `/productos` | Crear un producto |
| PUT | `/productos/{id}` | Actualizar un producto |
| DELETE | `/productos/{id}` | Eliminar un producto |

### Categorías

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/categorias` | Obtener todas las categorías |
| GET | `/categorias/{id}` | Obtener una categoría |
| POST | `/categorias` | Crear una categoría |
| PUT | `/categorias/{id}` | Actualizar una categoría |
| DELETE | `/categorias/{id}` | Eliminar una categoría |

---

## 📚 Swagger

Con el servidor iniciado:

```text
http://localhost:8008/swagger-ui/index.html
```

Desde Swagger puedes consultar y probar los endpoints disponibles.

---

## 🗃️ Configuración de la base de datos

El archivo `application.properties` utiliza variables de entorno:

```properties
spring.application.name=inventario
server.port=8008

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Hibernate está configurado para actualizar el esquema de la base de datos automáticamente durante el desarrollo.

---

## 🧪 Validaciones

La API utiliza **Bean Validation** para validar los datos recibidos.

Entre las validaciones implementadas:

- El nombre del producto es obligatorio.
- El precio debe ser mayor a `0`.
- La categoría es obligatoria.
- El stock no puede ser negativo.
- El nombre de la categoría es obligatorio.

---

## 🎯 Objetivo del proyecto

Este proyecto fue desarrollado como práctica para aplicar conceptos de desarrollo backend y frontend:

- Desarrollo de APIs REST
- Arquitectura por capas
- Spring Boot
- Spring Data JPA
- Hibernate
- Relaciones entre entidades
- DTOs
- Validación de datos
- Manejo de excepciones
- Operaciones CRUD
- Consumo de APIs mediante JavaScript
- Diseño responsive
- MySQL
- Git y GitHub

---

## 👨‍💻 Autor

**Ricardo Gamez Elizalde**

Proyecto desarrollado como parte de mi aprendizaje y práctica en desarrollo de software.

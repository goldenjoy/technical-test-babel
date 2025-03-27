# 🚀 API de Gestión de Empleados

Aplicación Spring Boot con PostgreSQL para operaciones CRUD de empleados, diseñada para Docker.

## 📋 Requisitos Previos
- Java 17
- Docker
- Maven
- Postman (para pruebas)

## 🛠️ Configuración Inicial

### 1. Clonar el repositorio
```Terminal
git clone https://github.com/goldenjoy/technical-test-babel.git
cd technical-test-babel
```

### 2. Levantar PostgreSQL en Docker
```Terminal
docker run --name postgres-empleados \
  -e POSTGRES_PASSWORD=postgres123 \
  -e POSTGRES_DB=empleados_db \
  -p 5432:5432 \
  -d postgres
```

## 📚 Endpoints API
Método	Endpoint	            Descripción
POST	/api/empleados/	        Crear uno o múltiples empleados
GET	    /api/empleados	        Obtener todos los empleados
PUT	    /api/empleados/{id}	    Actualizar un empleado
DELETE	/api/empleados/{id}	    Eliminar un empleado


## 🧪 Pruebas con Postman
cd docs\postman_collection
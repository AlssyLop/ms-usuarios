# ms-usuarios

Microservicio de gestion de usuarios para la plataforma Plaza de Comidas. Implementa autenticacion y creacion de cuentas para diferentes roles (Administrador, Propietario, Empleado, Cliente).

## Stack

- Java 25 + Spring Boot 4.0.6 + Maven (mvnw wrapper)
- MySQL 8 (JPA con Hibernate, `ddl-auto=validate`)
- Spring Security + SpringDoc OpenAPI 3.0.2
- Lombok + MapStruct 1.6.3
- Pruebas: JUnit 5 + Mockito

## Arquitectura Hexagonal con Vertical Slicing

Cada caso de uso es autocontenido en su propio paquete vertical. Lo compartido entre features vive en `common/`.

```
com.plazoleta.user_services/
  common/                          # compartido entre features
    domain/
      model/        Usuario, Rol
      spi/          IUsuarioRepositoryPort, IRolRepositoryPort
    application/
      exception/    ErrorResponse
    infrastructure/
      config/       BeanConfiguration
      persistence/  entity/ mapper/ repository/ adapter/
      endpoint/
        handler/    GlobalExceptionHandler

  crear_propietario/               # HU-1
    domain/
      api/          ICrearPropietarioUseCase
      usecase/      CrearPropietarioUseCase
    application/
      dto/          CrearPropietarioRequest, CrearPropietarioResponse
      handler/      CrearPropietarioHandler
      factory/      UsuarioFactory
    infrastructure/
      endpoint/     UsuarioController
```

## Base de Datos

Esquema MySQL en `db/init.sql`:

- `rol` - ADMINISTRADOR, PROPIETARIO, EMPLEADO, CLIENTE (seed data fija)
- `usuario` - datos personales, clave encriptada con BCrypt, referencia a rol

Conexion local: `root/root` en `localhost:3306/plazoleta_usuarios`.

## Ejecucion

```bash
./mvnw spring-boot:run    # Puerto 8081
./mvnw clean test         # Pruebas unitarias (9 tests)
```

## Endpoints Implementados

| Metodo | Ruta                   | Descripcion              |
|--------|------------------------|--------------------------|
| POST   | `/usuarios/propietario` | Crear cuenta de propietario |

Documentacion OpenAPI disponible en `/swagger-ui.html` y `/v3/api-docs`.

## HU-1: Crear Propietario

Crea un usuario con rol PROPIETARIO. Endpoint publico (sin seguridad JWT en esta version).

### Validaciones de dominio

- Documento de identidad: solo numerico, unico en el sistema
- Celular: comienza con `+`, maximo 13 caracteres
- Correo: formato valido, unico en el sistema
- Clave: minimo 8 caracteres
- Edad: mayor o igual a 18 anos (calculada via `Period.between`)
- Unicidades (correo, documento) verificadas via SPI antes de persistir

### Respuestas

- **201**: propietario creado (mensaje + datos del usuario)
- **400**: errores de validacion (lista de errores)
- **409**: conflicto (correo o documento duplicado)

## Proximas HU (pendientes)

- H5: Autenticacion JWT (`/auth/login`)
- H6: Propietario crea empleado
- H8: Cliente crea cuenta

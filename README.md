# ms-usuarios

Microservicio de gestion de usuarios para la plataforma Plaza de Comidas. Implementa autenticacion y creacion de cuentas para diferentes roles (Administrador, Propietario, Empleado, Cliente).

## Stack

- Java 25 + Spring Boot 4.0.6 + Maven (mvnw wrapper)
- MySQL 8 (JPA con Hibernate, `ddl-auto=validate`)
- Spring Security + jjwt 0.12.6 + SpringDoc OpenAPI 3.0.2
- Lombok + MapStruct 1.6.3
- Pruebas: JUnit 5 + Mockito

## Arquitectura Hexagonal

```
com.plazoleta.usuarios/
  dominio/                          # Nucleo del negocio
    api/            CrearUsuarioPort, ConsultarUsuarioPort
    exception/      CredencialesInvalidasException
    modelo/         Usuario + value objects (Correo, Clave, TipoRol, etc.)
    spi/            UsuarioRespositoryPort, AutenticarUsuarioPort
    usecase/        CrearUsuario, ConsultarUsuario, AutenticarUsuario

  application/                      # Puertos de entrada
    dto/            request/ response/
    exception/      ErrorResponse
    factory/        UsuarioFactory
    handle/         UsuarioHandle, ConsultarUsuarioHandle, AutenticarHandle

  infrastructure/                   # Adaptadores
    config/         BeanConfiguration
    endpoint/       UsuarioController, AuthController
    endpoint/handler/  GlobalExceptionHandler
    entity/         EntidadUsuario, EntidadRol
    persistence/    adapter/ mapper/ repository/
    security/       SecurityConfig
    security/jwt/   JwtTokenProvider, JwtAuthenticationFilter
```

## Base de Datos

Esquema MySQL en `db/init.sql`:

- `rol` - ADMINISTRADOR, PROPIETARIO, EMPLEADO, CLIENTE (seed data fija)
- `usuario` - datos personales, clave encriptada con BCrypt, referencia a rol

Conexion local: `root/root` en `localhost:3306/plazoleta_usuarios`.

## Ejecucion

```bash
./mvnw spring-boot:run    # Puerto 8081
./mvnw clean test         # Pruebas unitarias (13 tests)
```

## Endpoints Implementados

| Metodo | Ruta                   | Descripcion              | Autenticacion |
|--------|------------------------|--------------------------|---------------|
| POST   | `/auth/login`          | Iniciar sesion (publico) | No requiere   |
| POST   | `/usuarios/propietario` | Crear cuenta de propietario | JWT (Administrador) |
| GET    | `/usuarios/{id}`       | Consultar usuario por ID | JWT           |

Documentacion OpenAPI disponible en `/swagger-ui.html` y `/v3/api-docs`.

## HU-5: Autenticacion JWT

Inicio de sesion con correo y clave, retorna un token JWT firmado.

### Politicas de expiracion por rol

| Rol | Politica |
|-----|----------|
| ADMINISTRADOR / PROPIETARIO | Sliding — 1 hora, se renueva si quedan <10 min |
| EMPLEADO / CLIENTE | 12 horas, se renueva si pasaron >8 horas desde creacion |

### Respuestas

- **200**: token JWT (`{"token": "..."}`)
- **401**: credenciales invalidas (`{"mensaje": "Credenciales invalidas"}`)

### Admin por defecto

Creado via `data.sql` al arrancar la aplicacion:
- **Usuario:** `admin`
- **Clave:** `admin`

---

## HU-1: Crear Propietario

Crea un usuario con rol PROPIETARIO. Endpoint protegido (requiere JWT de Administrador).

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

- H6: Propietario crea empleado
- H8: Cliente crea cuenta

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
    api/            CrearUsuarioPort, CrearEmpleadoPort, ConsultarUsuarioPort
    exception/      CredencialesInvalidasException, ValidacionException
    modelo/         Usuario + value objects (Correo, Clave, TipoRol, etc.)
    spi/            UsuarioRespositoryPort, AutenticarUsuarioPort
    usecase/        CrearUsuario, CrearEmpleado, ConsultarUsuario, AutenticarUsuario

  application/                      # Puertos de entrada
    dto/            request/ (UsuarioPost, EmpleadoPost) response/ (UsuarioCreado, EmpleadoResponse)
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
    restaurante/    RestauranteRestClienteAdapter (comunicacion con ms-restaurantes)
```

## Base de Datos

Esquema MySQL en `db/init.sql`:

- `rol` - ADMINISTRADOR, PROPIETARIO, EMPLEADO, CLIENTE (seed data fija)
- `usuario` - datos personales, clave encriptada con BCrypt, referencia a rol

Conexion local: `root/root` en `localhost:3306/plazoleta_usuarios`.

## Ejecucion

```bash
./mvnw spring-boot:run    # Puerto 8081
./mvnw clean test         # Pruebas unitarias (18 tests)
```

## Endpoints Implementados

| Metodo | Ruta                   | Descripcion              | Autenticacion |
|--------|------------------------|--------------------------|---------------|
| POST   | `/auth/login`          | Iniciar sesion (publico) | No requiere   |
| POST   | `/usuarios/propietario` | Crear cuenta de propietario | JWT (Administrador) |
| POST   | `/usuarios/empleado`   | Crear cuenta de empleado    | JWT (Propietario) |
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
- **Clave:** `admin@admin.com`

---

## HU-6: Crear Empleado

Crea un usuario con rol EMPLEADO. Endpoint protegido (requiere JWT de Propietario autenticado). El empleado se asocia automaticamente al restaurante del propietario mediante una llamada RestTemplate a `POST /restaurantes/empleados` de ms-restaurantes, forwardeando el token JWT del propietario autenticado.

### Validaciones de dominio

- **Nombre**: requerido, solo letras (2-100 caracteres)
- **Apellido**: requerido, solo letras (2-100 caracteres)
- **Documento**: solo numerico, unico en el sistema
- **Celular**: comienza con `+`, maximo 13 caracteres
- **Correo**: formato valido, unico en el sistema
- **Clave**: minimo 8 caracteres
- **idRol**: requerido, debe ser un numero positivo (tipo de empleado: Chef, Mesero, Domiciliario)
- Las validaciones se ejecutan de forma secuencial — el primer error detiene el proceso y retorna `{campo: mensaje}`

### Request body

```json
{
  "nombre": "string",
  "apellido": "string",
  "documentoDeIdentidad": "string",
  "celular": "string",
  "correo": "string",
  "idRol": 1,
  "clave": "string"
}
```

### Respuestas

- **201**: `{"mensaje": "Empleado creado exitosamente"}`
- **400**: `{campo: "mensaje de error"}` (primer campo que falla)
- **401**: credenciales invalidas (sin cuerpo)

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

- H8: Cliente crea cuenta

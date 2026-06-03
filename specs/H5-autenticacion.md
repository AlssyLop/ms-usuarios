# HU-5: Autenticación

## Historia de usuario

> **Rol:** Administrador / Cliente / Propietario / Empleado
> **Funcionalidad:** Agregar autenticación al sistema
> **Motivo:** Poder acceder a las funcionalidades que le corresponden a mi rol y exponer los servicios solo a usuarios logueados

---

## Criterios de aceptación

### Inicio de sesión

- El inicio de sesión es a través de **correo** y **clave**.
- Se debe validar que el usuario exista y la contraseña sea correcta.
- El número de intentos es ilimitado.
- No está contemplada la recuperación de contraseña.
- El servicio de inicio de sesión es **público** (no requiere autenticación).
- Al iniciar sesión correctamente, el sistema entrega un **token de acceso** de acuerdo al rol del usuario, el cual debe incluirse en cada petición posterior.
- El token se renueva automáticamente según las políticas de expiración de cada rol descritas a continuación.

### Políticas de expiración de token por rol

| Rol | Política de expiración |
|---|---|
| **ADMINISTRADOR** | Sliding expiration — cada petición exitosa extiende la validez del token a 1 hora desde la fecha actual. Si no hay actividad por más de 1 hora, el token expira. |
| **PROPIETARIO** | Sliding expiration — cada petición exitosa extiende la validez del token a 1 hora desde la fecha actual. Si no hay actividad por más de 1 hora, el token expira. |
| **EMPLEADO** | Refresco condicional — el token tiene duración de 12 horas. Si han pasado más de 8 horas desde su creación, se extiende por 12 horas adicionales. |
| **CLIENTE** | Refresco condicional — el token tiene duración de 12 horas. Si han pasado más de 8 horas desde su creación, se extiende por 12 horas adicionales. |

### Control de acceso

Una vez iniciada la sesión, cada usuario tiene los permisos para realizar las acciones que le correspondan a su rol:

| Endpoint | Rol requerido |
|---|---|
| Crear usuario propietario | **ADMINISTRADOR** |
| Crear restaurante | **ADMINISTRADOR** |
| Crear usuario empleado | **PROPIETARIO** |
| Crear / modificar plato | **PROPIETARIO** (debe ser el propietario del restaurante al que pertenece el plato) |

### Respuestas del sistema

- Si alguien **no ha iniciado sesión** o su **rol no tiene permiso** para el servicio que intenta usar, el sistema responde con **401 Unauthorized** sin mensaje de error.
- **Inicio de sesión exitoso:** Responde con el token de acceso.

---

## Administrador por defecto

Para poder empezar a usar el sistema, se crea automáticamente un administrador por defecto al arrancar la aplicación mediante un archivo `data.sql`:

- **Usuario:** `admin`
- **Contraseña:** `admin` (encriptada con bcrypt)

Este archivo se elimina o se desactiva (`spring.sql.init.mode=never`) al pasar a producción.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/auth/login` | Iniciar sesión |

### Request body (JSON)

```json
{
  "correo": "string",
  "clave": "string"
}
```

### Response 200 — Inicio de sesión exitoso

```json
{
  "token": "string",
}
```

### Response 401 — No autorizado

*(Sin cuerpo en la respuesta)*

---

## Suposiciones validadas

1. ✅ Al iniciar sesión correctamente se entrega un token de acceso según el rol para identificarse en cada petición.
2. ✅ Ese token tiene una duración limitada.
3. ✅ Si alguien no ha iniciado sesión o no tiene el rol necesario, responde 401 Unauthorized sin mensaje de error.
4. ✅ El servicio de inicio de sesión es público.
5. ✅ Se crea un administrador por defecto vía `data.sql` con usuario `admin` y contraseña `admin` (bcrypt), que se elimina/desactiva en producción.

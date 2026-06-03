# HU-8: Crear cuenta cliente

## Historia de usuario

> **Rol:** Cliente de la plazoleta de comida
> **Funcionalidad:** Crear mi cuenta
> **Motivo:** Poder acceder al sistema y realizar pedidos

---

## Criterios de aceptación

### Campos obligatorios

| Campo | Tipo | Validaciones |
|---|---|---|
| Nombre | Texto | — |
| Apellido | Texto | — |
| DocumentoDeIdentidad | Texto | Solo numérico. Debe ser único en el sistema |
| Celular | Texto | Máx. 13 caracteres. Debe comenzar con `+` seguido de solo números |
| Correo | Texto | Debe ser una estructura de email válida. Debe ser único en el sistema |
| Clave | Texto | Mínimo 8 caracteres. Se almacena encriptada con bcrypt |

### Reglas de negocio

- El registro es público — el cliente se registra por sí mismo, sin necesidad de un administrador o propietario.
- El usuario creado tendrá el rol **CLIENTE** (se asigna automáticamente, no se envía en la petición).
- Las validaciones de campos compartidos (correo único, celular con +, documento numérico único, clave mínimo 8 caracteres) son las mismas que en creaciones anteriores.

### Validación campo por campo

El sistema valida cada campo de forma secuencial y se detiene en el primero que falle, devolviendo un solo error por respuesta.

### Respuestas del sistema

- **Creación exitosa:** El sistema responde con un mensaje de confirmación.
- **Error de validación:** El sistema responde con el error del primer campo que no cumpla las validaciones.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `usuarios/cliente` | Registrar cuenta de cliente (público) |

### Request body (JSON)

```json
{
  "nombre": "string",
  "apellido": "string",
  "documentoDeIdentidad": "string",
  "celular": "string",
  "correo": "string",
  "clave": "string"
}
```

### Response 201 — Creado exitosamente

```json
{
  "mensaje": "Cliente creado exitosamente"
}
```

### Response 400 — Error de validación

```json
{
  "correo": "El correo ya está registrado"
}
```

---

## Suposiciones validadas

1. ✅ El cliente se registra por sí mismo — endpoint público.
2. ✅ Las validaciones de campos compartidos son las mismas que en creaciones anteriores.
3. ✅ Si la cuenta se crea correctamente, el sistema responde con un mensaje de confirmación.
4. ✅ Si hay errores de validación, el sistema responde con el error del primer campo que falle.
5. ✅ No se envía idRol — el rol CLIENTE se asigna automáticamente.

# HU-1: Crear cuenta de propietario

## Historia de usuario

> **Rol:** Administrador de la plataforma de plazoleta de comidas
> **Funcionalidad:** Crear en el sistema la cuenta para un propietario
> **Motivo:** Poder crear un restaurante a cargo de un propietario

---

## Criterios de aceptación

### Campo — Datos obligatorios

La creación de la cuenta debe solicitar los siguientes campos, todos obligatorios:

| Campo | Tipo | Validaciones |
|---|---|---|
| Nombre | Texto | — |
| Apellido | Texto | — |
| DocumentoDeIdentidad | Texto | Solo numérico. Debe ser único en el sistema |
| Celular | Texto | Máx. 13 caracteres. Puede contener el símbolo `+`. Debe comenzar con `+`. Ej: `+573005698325` |
| FechaNacimiento | Fecha | Formato DD/MM/AAAA. El usuario debe ser mayor de edad (≥ 18 años) |
| Correo | Texto | Debe ser una estructura de email válida. Debe ser único en el sistema |
| Clave | Texto | Mínimo 8 caracteres. Se envía una sola vez (sin confirmación). Se almacenará encriptada con bcrypt |

### Reglas de negocio

- El usuario creado tendrá el rol **PROPIETARIO**.
- El administrador debe estar autenticado en el sistema para acceder a esta funcionalidad.
- El correo electrónico debe ser único (no pueden existir dos propietarios con el mismo correo).
- El documento de identidad debe ser único (no pueden existir dos propietarios con el mismo documento).
- No se requiere confirmación de clave — se envía una sola vez en la petición.

### Respuestas del sistema

- **Creación exitosa:** El sistema responde con un mensaje "Propietario creado exitosamente" y los datos del propietario creado.
- **Error de validación:** El sistema responde con mensajes de error indicando qué campo no cumple las validaciones y por qué.
- **Error de duplicado:** Si el correo o el documento ya existen, el sistema responde con un mensaje de error indicando el conflicto.

---

## Interfaz del sistema

No existe interfaz gráfica (frontend). El administrador interactúa con el sistema a través de servicios REST documentados con OpenAPI (Swagger). Los datos se envían en una petición estructurada (JSON) a un endpoint del microservicio correspondiente.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/usuarios/propietario` | Crear cuenta de propietario |

### Request body (JSON)

```json
{
  "nombre": "string",
  "apellido": "string",
  "documentoDeIdentidad": "string",
  "celular": "string",
  "fechaNacimiento": "DD/MM/AAAA",
  "correo": "string",
  "clave": "string"
}
```

### Response 201 — Creado exitosamente

```json
{
  "mensaje": "Propietario creado exitosamente",
  "id": 1,
  "nombre": "string",
  "apellido": "string",
  "documentoDeIdentidad": "string",
  "celular": "string",
  "correo": "string",
  "rol": "PROPIETARIO"
}
```

### Response 400 — Error de validación

```json
{
  "mensaje": "Errores de validación",
  "errores": [
    "El correo no tiene un formato válido",
    "El documento de identidad debe ser numérico"
  ]
}
```

### Response 409 — Conflicto (duplicado)

```json
{
  "mensaje": "El correo electrónico ya está registrado"
}

{
  "mensaje": "El Documento de Identidad ya está registrado"
}
```

---

## Suposiciones validadas

1. ✅ El administrador envía los datos a través de servicios REST documentados con Swagger/OpenAPI (no hay frontend).
2. ✅ Al crear exitosamente, el sistema responde con mensaje de confirmación.
3. ✅ Si hay errores de validación, el sistema responde con mensajes de error indicando el campo incorrecto.
4. ✅ El correo electrónico debe ser único.
5. ✅ El documento de identidad debe ser único.
6. ✅ El celular debe comenzar con `+`.
7. ✅ La clave debe tener mínimo 8 caracteres.
8. ✅ Mayor de edad = 18 años o más.
9. ✅ El administrador debe estar autenticado.
10. ✅ No se necesita confirmación de clave — se envía una sola vez.
11. ✅ La fecha de nacimiento se ingresa en formato DD/MM/AAAA.

# HU-6: Crear cuenta empleado

## Historia de usuario

> **Rol:** Propietario de un restaurante
> **Funcionalidad:** Crear cuentas para los empleados
> **Motivo:** Que puedan acceder al sistema y administrar los pedidos

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
| IdRol | Numérico | Tipo de empleado (ej. Chef, Mesero) |
| Clave | Texto | Mínimo 8 caracteres. Se almacena encriptada con bcrypt |

### Reglas de negocio

- Solo el propietario puede crear cuentas para los empleados de su restaurante.
- El propietario debe estar autenticado y tener el rol **PROPIETARIO**.
- El empleado se asocia automáticamente al restaurante del propietario autenticado.
- El usuario creado tendrá el rol base **EMPLEADO**, además del sub-tipo indicado en `idRol`.
- Las validaciones de correo, celular y documento son las mismas que en la creación del propietario.

### Validación campo por campo

El sistema valida cada campo de forma secuencial y se detiene en el primero que falle, devolviendo un solo error por respuesta.

### Respuestas del sistema

- **Creación exitosa:** El sistema responde con un mensaje de confirmación.
- **Error de validación:** El sistema responde con el error del primer campo que no cumpla las validaciones.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `usuarios/empleado` | Crear cuenta de empleado |

### Request body (JSON)

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

### Response 201 — Creado exitosamente

```json
{
  "mensaje": "Empleado creado exitosamente"
}
```

### Response 400 — Error de validación

```json
{
  "correo": "El correo ya está registrado"
}
```

### Response 401 — No autorizado

*(Sin cuerpo en la respuesta)*

---

## Suposiciones validadas

1. ✅ El propietario envía los datos del empleado a través de servicios REST (sin frontend).
2. ✅ Si la cuenta se crea correctamente, el sistema responde con un mensaje de confirmación.
3. ✅ Si hay errores de validación, el sistema responde con el error del primer campo que falle.
4. ✅ El propietario debe estar autenticado y tener el rol PROPIETARIO.
5. ✅ El empleado se asocia automáticamente al restaurante del propietario autenticado.
6. ✅ Las validaciones de campos compartidos (correo único, celular con +, documento numérico único) son las mismas que en creación de propietario.
7. ✅ El campo idRol es el tipo de empleado (Chef, Mesero) y el sistema asigna el rol EMPLEADO como base.
8. ✅ La clave debe tener mínimo 8 caracteres.
9. ✅ El empleado se asocia al restaurante del propietario autenticado.

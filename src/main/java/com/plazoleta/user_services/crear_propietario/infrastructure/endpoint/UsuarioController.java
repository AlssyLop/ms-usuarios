package com.plazoleta.user_services.crear_propietario.infrastructure.endpoint;

import com.plazoleta.user_services.crear_propietario.application.dto.CrearPropietarioRequest;
import com.plazoleta.user_services.crear_propietario.application.dto.CrearPropietarioResponse;
import com.plazoleta.user_services.crear_propietario.application.handler.CrearPropietarioHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios del sistema")
public class UsuarioController {

    private final CrearPropietarioHandler crearPropietarioHandler;

    public UsuarioController(CrearPropietarioHandler crearPropietarioHandler) {
        this.crearPropietarioHandler = crearPropietarioHandler;
    }

    @PostMapping("/propietario")
    @Operation(summary = "Crear cuenta de propietario",
            description = "Crea una cuenta con rol PROPIETARIO. Requiere autenticacion como ADMINISTRADOR.")
    @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
            content = @Content(schema = @Schema(implementation = CrearPropietarioResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "409", description = "Conflicto (correo o documento duplicado)")
    public ResponseEntity<CrearPropietarioResponse> crearPropietario(
            @Valid @RequestBody CrearPropietarioRequest request) {
        CrearPropietarioResponse response = crearPropietarioHandler.handle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

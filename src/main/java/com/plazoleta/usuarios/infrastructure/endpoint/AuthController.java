package com.plazoleta.usuarios.infrastructure.endpoint;

import com.plazoleta.usuarios.application.dto.request.LoginRequest;
import com.plazoleta.usuarios.application.dto.response.LoginResponse;
import com.plazoleta.usuarios.application.exception.ErrorResponse;
import com.plazoleta.usuarios.application.handle.AutenticarHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticacion", description = "Inicio de sesion y generacion de tokens JWT")
public class AuthController {

    private final AutenticarHandle autenticarHandle;

    public AuthController(AutenticarHandle autenticarHandle) {
        this.autenticarHandle = autenticarHandle;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion",
            description = "Autentica un usuario por correo y clave, retorna un token JWT de acceso.")
    @ApiResponse(responseCode = "200", description = "Inicio de sesion exitoso",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Credenciales invalidas",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = autenticarHandle.login(request);
        return ResponseEntity.ok(response);
    }
}

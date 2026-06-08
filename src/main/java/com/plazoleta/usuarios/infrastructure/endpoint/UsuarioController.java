/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.infrastructure.endpoint;

import com.plazoleta.usuarios.application.dto.request.ClientePost;
import com.plazoleta.usuarios.application.dto.response.ClienteResponse;
import com.plazoleta.usuarios.application.dto.request.EmpleadoPost;
import com.plazoleta.usuarios.application.dto.response.EmpleadoResponse;
import com.plazoleta.usuarios.application.handle.UsuarioHandle;
import com.plazoleta.usuarios.application.handle.ConsultarUsuarioHandle;
import com.plazoleta.usuarios.application.dto.response.UsuarioCreado;
import com.plazoleta.usuarios.application.dto.response.UsuarioConsultaResponse;
import com.plazoleta.usuarios.application.dto.request.UsuarioPost;
import com.plazoleta.usuarios.application.exception.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios del sistema")
public class UsuarioController {
    private final UsuarioHandle usuarioHandle;
    private final ConsultarUsuarioHandle consultarUsuarioHandle;

    public UsuarioController(UsuarioHandle usuarioHandle,
                             ConsultarUsuarioHandle consultarUsuarioHandle) {
        this.usuarioHandle = usuarioHandle;
        this.consultarUsuarioHandle = consultarUsuarioHandle;
    }
    
    @PostMapping("/propietario")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Crear cuenta de propietario",
            description = "Crea una cuenta con rol PROPIETARIO. Requiere autenticacion como ADMINISTRADOR.")
    @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioCreado.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflicto (correo o documento duplicado)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<UsuarioCreado> crearPropietario(
            @Valid @RequestBody UsuarioPost request) {
        UsuarioCreado response = usuarioHandle.crearPropietario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'PROPIETARIO')")
    @Operation(summary = "Consultar usuario por ID",
            description = "Retorna los datos basicos de un usuario por su ID. Requiere rol ADMINISTRADOR o PROPIETARIO.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioConsultaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<UsuarioConsultaResponse> consultarUsuario(@PathVariable Long id) {
        UsuarioConsultaResponse response = consultarUsuarioHandle.consultarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cliente")
    @Operation(summary = "Crear cuenta de cliente",
            description = "Crea una cuenta con rol CLIENTE. Endpoint publico, no requiere autenticacion.")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(schema = @Schema(implementation = ClienteResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ClienteResponse> crearCliente(@RequestBody ClientePost request) {
        ClienteResponse response = usuarioHandle.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/empleado")
    @PreAuthorize("hasRole('PROPIETARIO')")
    @Operation(summary = "Crear cuenta de empleado",
            description = "Crea una cuenta con rol EMPLEADO. Requiere autenticacion como PROPIETARIO.")
    @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente",
            content = @Content(schema = @Schema(implementation = EmpleadoResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<EmpleadoResponse> crearEmpleado(
            @RequestBody EmpleadoPost request,
            @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        EmpleadoResponse response = usuarioHandle.crearEmpleado(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

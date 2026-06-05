/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.infrastructure.endpoint;

import com.plazoleta.usuarios.application.handle.UsuarioHandle;
import com.plazoleta.usuarios.application.dto.response.UsuarioCreado;
import com.plazoleta.usuarios.application.dto.request.UsuarioPost;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios del sistema")
public class UsuarioController {
    private final UsuarioHandle usuarioHandle;

    public UsuarioController(UsuarioHandle usuarioHandle) {
        this.usuarioHandle = usuarioHandle;
    }
    
    @PostMapping("/propietario")
    @Operation(summary = "Crear cuenta de propietario",
            description = "Crea una cuenta con rol PROPIETARIO. Requiere autenticacion como ADMINISTRADOR.")
    @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioCreado.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "409", description = "Conflicto (correo o documento duplicado)")
    public ResponseEntity<UsuarioCreado> crearPropietario(
            @Valid @RequestBody UsuarioPost request) {
        UsuarioCreado response = usuarioHandle.crearPropietario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.application.handle;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.application.factory.UsuarioFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.plazoleta.usuarios.application.dto.request.UsuarioPost;
import com.plazoleta.usuarios.application.dto.response.UsuarioCreado;
import com.plazoleta.usuarios.dominio.api.CrearUsuarioPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
@Service
@Transactional
public class UsuarioHandle {
    private final CrearUsuarioPort crearUsuario;
    private final UsuarioFactory usuarioFactory;
    private final PasswordEncoder passwordEncoder;

    public UsuarioHandle(CrearUsuarioPort crearPropietarioUseCase,
                                   UsuarioFactory usuario,
                                   PasswordEncoder passwordEncoder) {
        this.crearUsuario = crearPropietarioUseCase;
        this.usuarioFactory = usuario;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioCreado crearPropietario(UsuarioPost request) {
        Usuario usuario = usuarioFactory.sendToDomainPropietario(request);
        usuario.getClave().setValor(passwordEncoder.encode(usuario.getClave().getValor()));

        Usuario creado = crearUsuario.crearPropietario(usuario);

        return new UsuarioCreado(
                "Propietario creado exitosamente",
                creado.getId(),
                creado.getNombre().getValor(),
                creado.getApellido().getValor(),
                creado.getDocumentoDeIdentidad().getValor(),
                creado.getCelular().getValor(),
                creado.getCorreo().getValor(),
                creado.getRol().name()
        );
    }
}

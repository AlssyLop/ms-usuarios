/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.application.handle;

import com.plazoleta.usuarios.application.dto.request.EmpleadoPost;
import com.plazoleta.usuarios.application.dto.response.EmpleadoResponse;
import com.plazoleta.usuarios.dominio.api.CrearEmpleadoPort;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.application.factory.UsuarioFactory;
import com.plazoleta.usuarios.infrastructure.restaurante.RestauranteRestClienteAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CrearEmpleadoPort crearEmpleado;
    private final UsuarioFactory usuarioFactory;
    private final PasswordEncoder passwordEncoder;
    private final RestauranteRestClienteAdapter restauranteClient;

    public UsuarioHandle(CrearUsuarioPort crearPropietarioUseCase,
                         CrearEmpleadoPort crearEmpleado,
                         UsuarioFactory usuario,
                         PasswordEncoder passwordEncoder,
                         RestauranteRestClienteAdapter restauranteClient) {
        this.crearUsuario = crearPropietarioUseCase;
        this.crearEmpleado = crearEmpleado;
        this.usuarioFactory = usuario;
        this.passwordEncoder = passwordEncoder;
        this.restauranteClient = restauranteClient;
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

    public EmpleadoResponse crearEmpleado(EmpleadoPost request, String token) {
        Long idPropietario = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Usuario usuario = usuarioFactory.sendToDomainEmpleado(request);
        usuario.getClave().setValor(passwordEncoder.encode(usuario.getClave().getValor()));

        Usuario creado = crearEmpleado.crearEmpleado(usuario, idPropietario);
        restauranteClient.asociarEmpleado(creado.getId(), request.getIdRol().longValue(), token);

        return new EmpleadoResponse("Empleado creado exitosamente");
    }
}

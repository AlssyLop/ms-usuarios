package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.api.ConsultarUsuarioPort;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;

public class ConsultarUsuario implements ConsultarUsuarioPort {

    private final UsuarioRespositoryPort usuarioRepository;

    public ConsultarUsuario(UsuarioRespositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario consultarPorId(Long id) {
        return usuarioRepository.obtenerUsuario(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}

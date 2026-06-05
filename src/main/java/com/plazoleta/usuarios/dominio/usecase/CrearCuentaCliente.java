package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.api.CrearCuentaClientePort;
import com.plazoleta.usuarios.dominio.exception.ValidacionException;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;

public class CrearCuentaCliente implements CrearCuentaClientePort {

    private final UsuarioRespositoryPort usuarioRepository;

    public CrearCuentaCliente(UsuarioRespositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crearCliente(Usuario usuario) {
        validarCorreo(usuario);
        validarDocumento(usuario);
        usuario.setRol(TipoRol.CLIENTE);
        return usuarioRepository.create(usuario);
    }

    private void validarCorreo(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new ValidacionException("correo", "El correo ya esta registrado");
        }
    }

    private void validarDocumento(Usuario usuario) {
        if (usuarioRepository.existsByDocumentoDeIdentidad(usuario.getDocumentoDeIdentidad())) {
            throw new ValidacionException("documentoDeIdentidad",
                    "El documento de identidad ya esta registrado");
        }
    }
}

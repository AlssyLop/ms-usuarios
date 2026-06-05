/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import org.springframework.dao.DuplicateKeyException;
import com.plazoleta.usuarios.dominio.api.CrearUsuarioPort;

/**
 *
 * @author Usuario
 */
public class CrearUsuario implements CrearUsuarioPort{

    private final UsuarioRespositoryPort usuarioRepository;

    public CrearUsuario(UsuarioRespositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crearPropietario(Usuario usuario) {
        validarCorreoDocumento(usuario);
        return usuarioRepository.create(usuario);
    }

    private void validarCorreoDocumento(Usuario usuario){
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new DuplicateKeyException("El correo electronico ya esta en uso");
        }
        if (usuarioRepository.existsByDocumentoDeIdentidad(usuario.getDocumentoDeIdentidad())) {
            throw new DuplicateKeyException("El Documento de Identidad ya se encuentra registrado");
        }
    }
}

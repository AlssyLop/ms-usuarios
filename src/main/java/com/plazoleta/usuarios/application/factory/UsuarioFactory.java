/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.application.factory;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.application.dto.request.UsuarioPost;
import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class UsuarioFactory {
    public Usuario sendToDomainPropietario(UsuarioPost usuario) {
        return new Usuario(
                null,
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getDocumentoDeIdentidad(),
                usuario.getCelular(),
                usuario.getFechaNacimiento(),
                usuario.getCorreo(),
                usuario.getClave(),
                TipoRol.PROPIETARIO,
                true
        );
    }
}

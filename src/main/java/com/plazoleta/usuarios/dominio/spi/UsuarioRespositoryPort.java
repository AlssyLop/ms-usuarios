/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.dominio.spi;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.modelo.value.Documento;

/**
 *
 * @author Usuario
 */
public interface UsuarioRespositoryPort {
    Usuario create(Usuario usuario);
    boolean existsByCorreo(Correo correo);
    boolean existsByDocumentoDeIdentidad(Documento documento);
}

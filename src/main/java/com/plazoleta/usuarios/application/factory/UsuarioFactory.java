/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plazoleta.usuarios.application.factory;

import com.plazoleta.usuarios.application.dto.request.EmpleadoPost;
import com.plazoleta.usuarios.dominio.exception.ValidacionException;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.application.dto.request.UsuarioPost;
import com.plazoleta.usuarios.dominio.modelo.value.*;
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

    public Usuario sendToDomainEmpleado(EmpleadoPost empleado) {
        Nombre nombre;
        Apellido apellido;
        Documento documento;
        Celular celular;
        Correo correo;
        Clave clave;

        try { nombre = new Nombre(empleado.getNombre()); }
        catch (IllegalArgumentException e) { throw new ValidacionException("nombre", e.getMessage()); }

        try { apellido = new Apellido(empleado.getApellido()); }
        catch (IllegalArgumentException e) { throw new ValidacionException("apellido", e.getMessage()); }

        try { documento = new Documento(empleado.getDocumentoDeIdentidad()); }
        catch (IllegalArgumentException e) { throw new ValidacionException("documentoDeIdentidad", e.getMessage()); }

        try { celular = new Celular(empleado.getCelular()); }
        catch (IllegalArgumentException e) { throw new ValidacionException("celular", e.getMessage()); }

        try { correo = new Correo(empleado.getCorreo()); }
        catch (IllegalArgumentException e) { throw new ValidacionException("correo", e.getMessage()); }

        try { clave = new Clave(empleado.getClave()); }
        catch (IllegalArgumentException e) { throw new ValidacionException("clave", e.getMessage()); }

        if (empleado.getIdRol() == null || empleado.getIdRol() <= 0) {
            throw new ValidacionException("idRol", "El id del rol de empleado es requerido");
        }

        return new Usuario(
                null, nombre, apellido, documento, celular,
                new FechaNacimiento("01/01/1990"),
                correo, clave, TipoRol.EMPLEADO, true
        );
    }
}

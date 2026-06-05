package com.plazoleta.usuarios.dominio.api;

import com.plazoleta.usuarios.dominio.modelo.Usuario;

public interface CrearEmpleadoPort {
    Usuario crearEmpleado(Usuario usuario, Long idPropietario);
}

package com.plazoleta.usuarios.dominio.api;

import com.plazoleta.usuarios.dominio.modelo.Usuario;

public interface CrearCuentaClientePort {
    Usuario crearCliente(Usuario usuario);
}

package com.plazoleta.usuarios.dominio.api;

import com.plazoleta.usuarios.dominio.modelo.Usuario;

public interface ConsultarUsuarioPort {
    Usuario consultarPorId(Long id);
}

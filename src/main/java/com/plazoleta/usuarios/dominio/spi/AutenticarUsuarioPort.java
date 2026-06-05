package com.plazoleta.usuarios.dominio.spi;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;

public interface AutenticarUsuarioPort {
    Usuario autenticar(Correo correo, String claveIngresada);
}

package com.plazoleta.user_services.crear_propietario.domain.api;

import com.plazoleta.user_services.common.domain.model.Usuario;

public interface ICrearPropietarioUseCase {
    Usuario crearPropietario(Usuario usuario);
}

package com.plazoleta.user_services.common.domain.spi;

import com.plazoleta.user_services.common.domain.model.Rol;
import java.util.Optional;

public interface IRolRepositoryPort {
    Optional<Rol> findByNombre(String nombre);
}

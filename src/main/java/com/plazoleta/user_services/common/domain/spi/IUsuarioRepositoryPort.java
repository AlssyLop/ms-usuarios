package com.plazoleta.user_services.common.domain.spi;

import com.plazoleta.user_services.common.domain.model.Usuario;

public interface IUsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    boolean existsByCorreo(String correo);
    boolean existsByDocumentoDeIdentidad(String documento);
}

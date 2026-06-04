package com.plazoleta.user_services.common.infrastructure.persistence.repository;

import com.plazoleta.user_services.common.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByDocumentoDeIdentidad(String documento);
}

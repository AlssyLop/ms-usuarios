package com.plazoleta.usuarios.infrastructure.persistence.repository;

import com.plazoleta.usuarios.infrastructure.entity.EntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioJpaRepository extends JpaRepository<EntidadUsuario, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByDocumentoDeIdentidad(String documento);
}

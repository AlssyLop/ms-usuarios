package com.plazoleta.user_services.common.infrastructure.persistence.repository;

import com.plazoleta.user_services.common.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolJpaRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}

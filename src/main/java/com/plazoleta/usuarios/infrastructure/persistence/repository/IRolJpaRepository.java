package com.plazoleta.usuarios.infrastructure.persistence.repository;

import com.plazoleta.usuarios.infrastructure.entity.EntidadRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolJpaRepository extends JpaRepository<EntidadRol, Long> {
}

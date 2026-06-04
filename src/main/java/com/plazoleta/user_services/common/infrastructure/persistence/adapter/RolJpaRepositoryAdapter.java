package com.plazoleta.user_services.common.infrastructure.persistence.adapter;

import com.plazoleta.user_services.common.domain.model.Rol;
import com.plazoleta.user_services.common.domain.spi.IRolRepositoryPort;
import com.plazoleta.user_services.common.infrastructure.persistence.mapper.IRolEntityMapper;
import com.plazoleta.user_services.common.infrastructure.persistence.repository.IRolJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RolJpaRepositoryAdapter implements IRolRepositoryPort {

    private final IRolJpaRepository jpaRepository;
    private final IRolEntityMapper mapper;

    public RolJpaRepositoryAdapter(IRolJpaRepository jpaRepository, IRolEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre)
                .map(mapper::toDomain);
    }
}

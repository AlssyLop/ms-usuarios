package com.plazoleta.user_services.common.infrastructure.persistence.adapter;

import com.plazoleta.user_services.common.domain.model.Usuario;
import com.plazoleta.user_services.common.domain.spi.IUsuarioRepositoryPort;
import com.plazoleta.user_services.common.infrastructure.persistence.entity.RolEntity;
import com.plazoleta.user_services.common.infrastructure.persistence.mapper.IUsuarioEntityMapper;
import com.plazoleta.user_services.common.infrastructure.persistence.repository.IRolJpaRepository;
import com.plazoleta.user_services.common.infrastructure.persistence.repository.IUsuarioJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJpaRepositoryAdapter implements IUsuarioRepositoryPort {

    private final IUsuarioJpaRepository jpaRepository;
    private final IUsuarioEntityMapper mapper;
    private final IRolJpaRepository rolJpaRepository;

    public UsuarioJpaRepositoryAdapter(IUsuarioJpaRepository jpaRepository,
                                       IUsuarioEntityMapper mapper,
                                       IRolJpaRepository rolJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.rolJpaRepository = rolJpaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        RolEntity rolEntity = rolJpaRepository.findByNombre(usuario.getRol().name())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + usuario.getRol().name()));
        var entity = mapper.toEntity(usuario, rolEntity);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return jpaRepository.existsByCorreo(correo);
    }

    @Override
    public boolean existsByDocumentoDeIdentidad(String documento) {
        return jpaRepository.existsByDocumentoDeIdentidad(documento);
    }
}

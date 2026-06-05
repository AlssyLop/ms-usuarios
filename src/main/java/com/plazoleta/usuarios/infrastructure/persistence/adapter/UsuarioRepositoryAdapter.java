package com.plazoleta.usuarios.infrastructure.persistence.adapter;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.modelo.value.Documento;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import com.plazoleta.usuarios.infrastructure.entity.EntidadRol;
import com.plazoleta.usuarios.infrastructure.entity.EntidadUsuario;
import com.plazoleta.usuarios.infrastructure.persistence.mapper.IUsuarioEntityMapper;
import com.plazoleta.usuarios.infrastructure.persistence.repository.IRolJpaRepository;
import com.plazoleta.usuarios.infrastructure.persistence.repository.IUsuarioJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UsuarioRepositoryAdapter implements UsuarioRespositoryPort {

    private final IUsuarioJpaRepository jpaRepository;
    private final IUsuarioEntityMapper mapper;
    private final IRolJpaRepository rolJpaRepository;

    public UsuarioRepositoryAdapter(IUsuarioJpaRepository jpaRepository,
                                    IUsuarioEntityMapper mapper,
                                    IRolJpaRepository rolJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.rolJpaRepository = rolJpaRepository;
    }

    @Override
    public Usuario create(Usuario usuario) {
        Long rolId = (long) usuario.getRol().ordinal() + 1;
        EntidadRol rolEntity = rolJpaRepository.getReferenceById(rolId);
        EntidadUsuario entity = mapper.toEntity(usuario, rolEntity);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByCorreo(Correo correo) {
        return jpaRepository.existsByCorreo(correo.getValor());
    }

    @Override
    public boolean existsByDocumentoDeIdentidad(Documento documento) {
        return jpaRepository.existsByDocumentoDeIdentidad(documento.getValor());
    }
}

package com.plazoleta.user_services.common.infrastructure.persistence.mapper;

import com.plazoleta.user_services.common.domain.model.Usuario;
import com.plazoleta.user_services.common.infrastructure.persistence.entity.RolEntity;
import com.plazoleta.user_services.common.infrastructure.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUsuarioEntityMapper {

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "nombre", source = "domain.nombre")
    @Mapping(target = "rol", source = "rolEntity")
    @Mapping(target = "fechaCreacion", ignore = true)
    UsuarioEntity toEntity(Usuario domain, RolEntity rolEntity);

    @Mapping(target = "rol", source = "rol.nombre")
    Usuario toDomain(UsuarioEntity entity);
}

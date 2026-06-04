package com.plazoleta.user_services.common.infrastructure.persistence.mapper;

import com.plazoleta.user_services.common.domain.model.Rol;
import com.plazoleta.user_services.common.infrastructure.persistence.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRolEntityMapper {

    default Rol toDomain(RolEntity entity) {
        if (entity == null) return null;
        return Rol.valueOf(entity.getNombre());
    }

    default RolEntity toEntity(Rol domain) {
        if (domain == null) return null;
        return new RolEntity(null, domain.name());
    }
}

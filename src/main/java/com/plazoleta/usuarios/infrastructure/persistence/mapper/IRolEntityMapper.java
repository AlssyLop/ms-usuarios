package com.plazoleta.usuarios.infrastructure.persistence.mapper;

import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import com.plazoleta.usuarios.infrastructure.entity.EntidadRol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRolEntityMapper {

    default TipoRol toDomain(EntidadRol entity) {
        if (entity == null) return null;
        return TipoRol.valueOf(entity.getNombre());
    }
}

package com.plazoleta.usuarios.infrastructure.persistence.mapper;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Apellido;
import com.plazoleta.usuarios.dominio.modelo.value.Celular;
import com.plazoleta.usuarios.dominio.modelo.value.Clave;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.modelo.value.Documento;
import com.plazoleta.usuarios.dominio.modelo.value.FechaNacimiento;
import com.plazoleta.usuarios.dominio.modelo.value.Nombre;
import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import com.plazoleta.usuarios.infrastructure.entity.EntidadRol;
import com.plazoleta.usuarios.infrastructure.entity.EntidadUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUsuarioEntityMapper {

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "nombre", source = "domain.nombre.valor")
    @Mapping(target = "apellido", source = "domain.apellido.valor")
    @Mapping(target = "documentoDeIdentidad", source = "domain.documentoDeIdentidad.valor")
    @Mapping(target = "celular", source = "domain.celular.valor")
    @Mapping(target = "fechaNacimiento", source = "domain.fechaNacimiento.valor")
    @Mapping(target = "correo", source = "domain.correo.valor")
    @Mapping(target = "clave", source = "domain.clave.valor")
    @Mapping(target = "rol", source = "rolEntity")
    @Mapping(target = "fechaCreacion", ignore = true)
    EntidadUsuario toEntity(Usuario domain, EntidadRol rolEntity);

    default Usuario toDomain(EntidadUsuario entity) {
        if (entity == null) return null;
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new Usuario(
                entity.getId(),
                new Nombre(entity.getNombre()),
                new Apellido(entity.getApellido()),
                new Documento(entity.getDocumentoDeIdentidad()),
                new Celular(entity.getCelular()),
                new FechaNacimiento(formatter.format(entity.getFechaNacimiento())),
                new Correo(entity.getCorreo()),
                new Clave(entity.getClave()),
                TipoRol.valueOf(entity.getRol().getNombre()),
                entity.isActivo()
        );
    }
}

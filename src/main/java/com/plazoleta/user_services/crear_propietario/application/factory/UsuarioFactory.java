package com.plazoleta.user_services.crear_propietario.application.factory;

import com.plazoleta.user_services.crear_propietario.application.dto.CrearPropietarioRequest;
import com.plazoleta.user_services.common.domain.model.Rol;
import com.plazoleta.user_services.common.domain.model.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsuarioFactory {

    public Usuario toDomain(CrearPropietarioRequest request) {
        LocalDate fechaNacimiento = LocalDate.parse(request.getFechaNacimiento(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return new Usuario(
                null,
                request.getNombre(),
                request.getApellido(),
                request.getDocumentoDeIdentidad(),
                request.getCelular(),
                fechaNacimiento,
                request.getCorreo(),
                request.getClave(),
                Rol.PROPIETARIO,
                true
        );
    }
}

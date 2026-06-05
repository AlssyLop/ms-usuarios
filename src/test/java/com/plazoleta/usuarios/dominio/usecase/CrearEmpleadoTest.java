package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.exception.ValidacionException;
import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.*;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearEmpleadoTest {

    @Mock
    private UsuarioRespositoryPort usuarioRepository;

    private CrearEmpleado crearEmpleado;

    private Usuario empleado;

    @BeforeEach
    void setUp() {
        crearEmpleado = new CrearEmpleado(usuarioRepository);
        empleado = new Usuario(
                null,
                new Nombre("Juan"),
                new Apellido("Perez"),
                new Documento("987654321"),
                new Celular("+573001234567"),
                new FechaNacimiento("01/01/1990"),
                new Correo("juan@email.com"),
                new Clave("MiClave123"),
                TipoRol.EMPLEADO,
                true
        );
    }

    @Test
    @DisplayName("Should create empleado successfully with all valid fields")
    void crearEmpleado_AllValid_Success() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(any())).thenReturn(false);
        Usuario saved = new Usuario(
                1L,
                new Nombre("Juan"),
                new Apellido("Perez"),
                new Documento("987654321"),
                new Celular("+573001234567"),
                new FechaNacimiento("01/01/1990"),
                new Correo("juan@email.com"),
                new Clave("MiClave123"),
                TipoRol.EMPLEADO,
                true
        );
        when(usuarioRepository.create(empleado)).thenReturn(saved);

        Usuario result = crearEmpleado.crearEmpleado(empleado, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(TipoRol.EMPLEADO, result.getRol());
        verify(usuarioRepository).existsByCorreo(any());
        verify(usuarioRepository).existsByDocumentoDeIdentidad(any());
        verify(usuarioRepository).create(empleado);
    }

    @Test
    @DisplayName("Should throw ValidacionException when correo already exists")
    void crearEmpleado_CorreoDuplicado_ThrowsException() {
        when(usuarioRepository.existsByCorreo(empleado.getCorreo())).thenReturn(true);

        ValidacionException ex = assertThrows(ValidacionException.class,
                () -> crearEmpleado.crearEmpleado(empleado, 1L));
        assertEquals("correo", ex.getCampo());
        assertEquals("El correo ya esta registrado", ex.getMessage());
        verify(usuarioRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw ValidacionException when documento already exists")
    void crearEmpleado_DocumentoDuplicado_ThrowsException() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(empleado.getDocumentoDeIdentidad())).thenReturn(true);

        ValidacionException ex = assertThrows(ValidacionException.class,
                () -> crearEmpleado.crearEmpleado(empleado, 1L));
        assertEquals("documentoDeIdentidad", ex.getCampo());
        assertEquals("El documento de identidad ya esta registrado", ex.getMessage());
        verify(usuarioRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should set role EMPLEADO before saving")
    void crearEmpleado_AsignaRolEmpleado_Success() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(any())).thenReturn(false);
        Usuario saved = new Usuario(
                1L,
                new Nombre("Juan"),
                new Apellido("Perez"),
                new Documento("987654321"),
                new Celular("+573001234567"),
                new FechaNacimiento("01/01/1990"),
                new Correo("juan@email.com"),
                new Clave("MiClave123"),
                TipoRol.EMPLEADO,
                true
        );
        when(usuarioRepository.create(empleado)).thenReturn(saved);

        Usuario result = crearEmpleado.crearEmpleado(empleado, 1L);

        assertEquals(TipoRol.EMPLEADO, result.getRol());
        verify(usuarioRepository).create(empleado);
    }

    @Test
    @DisplayName("Should stop at first validation failure (correo checked before documento)")
    void crearEmpleado_CorreoDuplicado_NoVerificaDocumento() {
        when(usuarioRepository.existsByCorreo(empleado.getCorreo())).thenReturn(true);

        assertThrows(ValidacionException.class,
                () -> crearEmpleado.crearEmpleado(empleado, 1L));

        verify(usuarioRepository, never()).existsByDocumentoDeIdentidad(any());
        verify(usuarioRepository, never()).create(any());
    }
}

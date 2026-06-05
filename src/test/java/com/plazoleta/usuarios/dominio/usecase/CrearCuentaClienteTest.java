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
class CrearCuentaClienteTest {

    @Mock
    private UsuarioRespositoryPort usuarioRepository;

    private CrearCuentaCliente crearCuentaCliente;

    private Usuario cliente;

    @BeforeEach
    void setUp() {
        crearCuentaCliente = new CrearCuentaCliente(usuarioRepository);
        cliente = new Usuario(
                null,
                new Nombre("Maria"),
                new Apellido("Lopez"),
                new Documento("123456789"),
                new Celular("+573001234567"),
                new FechaNacimiento("01/01/1990"),
                new Correo("maria@email.com"),
                new Clave("MiClave123"),
                TipoRol.CLIENTE,
                true
        );
    }

    @Test
    @DisplayName("Should create cliente successfully with all valid fields")
    void crearCliente_AllValid_Success() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(any())).thenReturn(false);
        Usuario saved = new Usuario(
                1L,
                new Nombre("Maria"),
                new Apellido("Lopez"),
                new Documento("123456789"),
                new Celular("+573001234567"),
                new FechaNacimiento("01/01/1990"),
                new Correo("maria@email.com"),
                new Clave("MiClave123"),
                TipoRol.CLIENTE,
                true
        );
        when(usuarioRepository.create(cliente)).thenReturn(saved);

        Usuario result = crearCuentaCliente.crearCliente(cliente);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(TipoRol.CLIENTE, result.getRol());
        verify(usuarioRepository).existsByCorreo(any());
        verify(usuarioRepository).existsByDocumentoDeIdentidad(any());
        verify(usuarioRepository).create(cliente);
    }

    @Test
    @DisplayName("Should throw ValidacionException when correo already exists")
    void crearCliente_CorreoDuplicado_ThrowsException() {
        when(usuarioRepository.existsByCorreo(cliente.getCorreo())).thenReturn(true);

        ValidacionException ex = assertThrows(ValidacionException.class,
                () -> crearCuentaCliente.crearCliente(cliente));
        assertEquals("correo", ex.getCampo());
        assertEquals("El correo ya esta registrado", ex.getMessage());
        verify(usuarioRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw ValidacionException when documento already exists")
    void crearCliente_DocumentoDuplicado_ThrowsException() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(cliente.getDocumentoDeIdentidad())).thenReturn(true);

        ValidacionException ex = assertThrows(ValidacionException.class,
                () -> crearCuentaCliente.crearCliente(cliente));
        assertEquals("documentoDeIdentidad", ex.getCampo());
        assertEquals("El documento de identidad ya esta registrado", ex.getMessage());
        verify(usuarioRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should set role CLIENTE before saving")
    void crearCliente_AsignaRolCliente_Success() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(any())).thenReturn(false);
        Usuario saved = new Usuario(
                1L,
                new Nombre("Maria"),
                new Apellido("Lopez"),
                new Documento("123456789"),
                new Celular("+573001234567"),
                new FechaNacimiento("01/01/1990"),
                new Correo("maria@email.com"),
                new Clave("MiClave123"),
                TipoRol.CLIENTE,
                true
        );
        when(usuarioRepository.create(cliente)).thenReturn(saved);

        Usuario result = crearCuentaCliente.crearCliente(cliente);

        assertEquals(TipoRol.CLIENTE, result.getRol());
        verify(usuarioRepository).create(cliente);
    }

    @Test
    @DisplayName("Should stop at first validation failure (correo checked before documento)")
    void crearCliente_CorreoDuplicado_NoVerificaDocumento() {
        when(usuarioRepository.existsByCorreo(cliente.getCorreo())).thenReturn(true);

        assertThrows(ValidacionException.class,
                () -> crearCuentaCliente.crearCliente(cliente));

        verify(usuarioRepository, never()).existsByDocumentoDeIdentidad(any());
        verify(usuarioRepository, never()).create(any());
    }
}

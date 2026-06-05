package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.modelo.Usuario;
import com.plazoleta.usuarios.dominio.modelo.value.Apellido;
import com.plazoleta.usuarios.dominio.modelo.value.Celular;
import com.plazoleta.usuarios.dominio.modelo.value.Clave;
import com.plazoleta.usuarios.dominio.modelo.value.Correo;
import com.plazoleta.usuarios.dominio.modelo.value.Documento;
import com.plazoleta.usuarios.dominio.modelo.value.FechaNacimiento;
import com.plazoleta.usuarios.dominio.modelo.value.Nombre;
import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import com.plazoleta.usuarios.dominio.spi.UsuarioRespositoryPort;
import com.plazoleta.usuarios.dominio.usecase.CrearUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearUsuarioTest {

    @Mock
    private UsuarioRespositoryPort usuarioRepository;

    private CrearUsuario propietarioUseCase;

    private Usuario propietario;

    @BeforeEach
    void setUp() {
        propietarioUseCase = new CrearUsuario(usuarioRepository);
        propietario = new Usuario(
                null,
                new Nombre("Carlos"),
                new Apellido("Lopez"),
                new Documento("123456789"),
                new Celular("+573005698325"),
                new FechaNacimiento("15/05/1990"),
                new Correo("carlos@email.com"),
                new Clave("MiClave123"),
                TipoRol.PROPIETARIO,
                true
        );
    }

    @Test
    @DisplayName("Should create propietario successfully with all valid fields")
    void crearPropietario_AllValid_Success() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(any())).thenReturn(false);
        Usuario saved = new Usuario(
                1L,
                new Nombre("Carlos"),
                new Apellido("Lopez"),
                new Documento("123456789"),
                new Celular("+573005698325"),
                new FechaNacimiento("15/05/1990"),
                new Correo("carlos@email.com"),
                new Clave("MiClave123"),
                TipoRol.PROPIETARIO,
                true
        );
        when(usuarioRepository.create(propietario)).thenReturn(saved);

        Usuario result = propietarioUseCase.crearPropietario(propietario);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CARLOS", result.getNombre().getValor());
        assertEquals(TipoRol.PROPIETARIO, result.getRol());
        verify(usuarioRepository).existsByCorreo(any());
        verify(usuarioRepository).existsByDocumentoDeIdentidad(any());
        verify(usuarioRepository).create(propietario);
    }

    @Test
    @DisplayName("Should throw exception when user is under 18")
    void crearPropietario_MenorEdad_ThrowsException() {
        propietario = new Usuario(
                null,
                new Nombre("Menor"),
                new Apellido("Edad"),
                new Documento("123456789"),
                new Celular("+573005698325"),
                new FechaNacimiento("01/01/2010"),
                new Correo("menor@email.com"),
                new Clave("MiClave123"),
                TipoRol.PROPIETARIO,
                true
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El usuario debe ser mayor de edad (>= 18 anos)", ex.getMessage());
        verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when correo already exists")
    void crearPropietario_CorreoDuplicado_ThrowsException() {
        when(usuarioRepository.existsByCorreo(propietario.getCorreo())).thenReturn(true);

        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El correo electronico ya esta en uso", ex.getMessage());
        verify(usuarioRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when documento already exists")
    void crearPropietario_DocumentoDuplicado_ThrowsException() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(usuarioRepository.existsByDocumentoDeIdentidad(propietario.getDocumentoDeIdentidad())).thenReturn(true);

        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El Documento de Identidad ya se encuentra registrado", ex.getMessage());
        verify(usuarioRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when documento is invalid")
    void crearPropietario_DocumentoInvalido_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Documento("ABC123"));
        verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when celular does not start with +")
    void crearPropietario_CelularInvalido_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Celular("573005698325"));
        verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when correo format is invalid")
    void crearPropietario_CorreoInvalido_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Correo("correo-sin-arroba"));
        verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when clave is less than 8 characters")
    void crearPropietario_ClaveCorta_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Clave("1234567"));
        verifyNoInteractions(usuarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when fechaNacimiento format is invalid")
    void crearPropietario_FechaInvalida_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FechaNacimiento("15-13-2020"));
        verifyNoInteractions(usuarioRepository);
    }
}

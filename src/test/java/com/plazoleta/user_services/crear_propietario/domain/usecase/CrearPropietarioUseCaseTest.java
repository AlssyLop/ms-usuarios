package com.plazoleta.user_services.crear_propietario.domain.usecase;

import com.plazoleta.user_services.common.domain.model.Rol;
import com.plazoleta.user_services.common.domain.model.Usuario;
import com.plazoleta.user_services.common.domain.spi.IUsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearPropietarioUseCaseTest {

    @Mock
    private IUsuarioRepositoryPort propietarioRepository;

    private CrearPropietarioUseCase propietarioUseCase;

    private Usuario propietario;

    @BeforeEach
    void setUp() {
        propietarioUseCase = new CrearPropietarioUseCase(propietarioRepository);
        propietario = new Usuario(
                null, "Carlos", "Lopez", "123456789",
                "+573005698325", LocalDate.of(1990, 5, 15),
                "carlos@email.com", "MiClave123",
                Rol.PROPIETARIO, true
        );
    }

    @Test
    @DisplayName("Should create propietario successfully with all valid fields")
    void crearPropietario_AllValid_Success() {
        when(propietarioRepository.existsByCorreo(anyString())).thenReturn(false);
        when(propietarioRepository.existsByDocumentoDeIdentidad(anyString())).thenReturn(false);
        Usuario saved = new Usuario(
                1L, "Carlos", "Lopez", "123456789",
                "+573005698325", LocalDate.of(1990, 5, 15),
                "carlos@email.com", "MiClave123",
                Rol.PROPIETARIO, true
        );
        when(propietarioRepository.save(propietario)).thenReturn(saved);

        Usuario result = propietarioUseCase.crearPropietario(propietario);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Carlos", result.getNombre());
        assertEquals(Rol.PROPIETARIO, result.getRol());
        verify(propietarioRepository).existsByCorreo("carlos@email.com");
        verify(propietarioRepository).existsByDocumentoDeIdentidad("123456789");
        verify(propietarioRepository).save(propietario);
    }

    @Test
    @DisplayName("Should throw exception when documento is not numeric")
    void crearPropietario_DocumentoInvalido_ThrowsException() {
        propietario.setDocumentoDeIdentidad("ABC123");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El documento de identidad debe ser numerico", ex.getMessage());
        verifyNoInteractions(propietarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when celular does not start with +")
    void crearPropietario_CelularInvalido_ThrowsException() {
        propietario.setCelular("573005698325");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El celular debe comenzar con '+' y tener maximo 13 caracteres", ex.getMessage());
        verifyNoInteractions(propietarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when correo format is invalid")
    void crearPropietario_CorreoInvalido_ThrowsException() {
        propietario.setCorreo("correo-sin-arroba");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El correo no tiene un formato valido", ex.getMessage());
        verifyNoInteractions(propietarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when clave is less than 8 characters")
    void crearPropietario_ClaveCorta_ThrowsException() {
        propietario.setClave("1234567");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("La clave debe tener minimo 8 caracteres", ex.getMessage());
        verifyNoInteractions(propietarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when user is under 18")
    void crearPropietario_MenorEdad_ThrowsException() {
        propietario.setFechaNacimiento(LocalDate.of(2010, 1, 1));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El usuario debe ser mayor de edad (>= 18 anos)", ex.getMessage());
        verifyNoInteractions(propietarioRepository);
    }

    @Test
    @DisplayName("Should throw exception when correo already exists")
    void crearPropietario_CorreoDuplicado_ThrowsException() {
        when(propietarioRepository.existsByCorreo(propietario.getCorreo())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El correo electronico ya esta registrado", ex.getMessage());
        verify(propietarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when documento already exists")
    void crearPropietario_DocumentoDuplicado_ThrowsException() {
        when(propietarioRepository.existsByCorreo(anyString())).thenReturn(false);
        when(propietarioRepository.existsByDocumentoDeIdentidad(propietario.getDocumentoDeIdentidad())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> propietarioUseCase.crearPropietario(propietario));
        assertEquals("El Documento de Identidad ya esta registrado", ex.getMessage());
        verify(propietarioRepository, never()).save(any());
    }
}

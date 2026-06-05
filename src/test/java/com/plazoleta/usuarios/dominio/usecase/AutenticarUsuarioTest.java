package com.plazoleta.usuarios.dominio.usecase;

import com.plazoleta.usuarios.dominio.exception.CredencialesInvalidasException;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticarUsuarioTest {

    @Mock
    private UsuarioRespositoryPort usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AutenticarUsuario autenticarUsuario;

    private Usuario usuario;
    private Correo correoValido;

    @BeforeEach
    void setUp() {
        autenticarUsuario = new AutenticarUsuario(usuarioRepository, passwordEncoder);
        correoValido = new Correo("carlos@email.com");
        usuario = new Usuario(
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
    }

    @Test
    @DisplayName("Should authenticate successfully with valid credentials")
    void autenticar_CredencialesValidas_RetornaUsuario() {
        when(usuarioRepository.obtenerPorCorreo(correoValido)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("MiClave123", usuario.getClave().getValor())).thenReturn(true);

        Usuario result = autenticarUsuario.autenticar(correoValido, "MiClave123");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CARLOS", result.getNombre().getValor());
        assertEquals(TipoRol.PROPIETARIO, result.getRol());
        verify(usuarioRepository).obtenerPorCorreo(correoValido);
        verify(passwordEncoder).matches("MiClave123", usuario.getClave().getValor());
    }

    @Test
    @DisplayName("Should throw exception when email does not exist")
    void autenticar_CorreoNoExistente_LanzaExcepcion() {
        when(usuarioRepository.obtenerPorCorreo(correoValido)).thenReturn(Optional.empty());

        CredencialesInvalidasException ex = assertThrows(CredencialesInvalidasException.class,
                () -> autenticarUsuario.autenticar(correoValido, "MiClave123"));

        assertEquals("Credenciales invalidas", ex.getMessage());
        verify(usuarioRepository).obtenerPorCorreo(correoValido);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("Should throw exception when password is incorrect")
    void autenticar_ClaveIncorrecta_LanzaExcepcion() {
        when(usuarioRepository.obtenerPorCorreo(correoValido)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("ClaveIncorrecta", usuario.getClave().getValor())).thenReturn(false);

        CredencialesInvalidasException ex = assertThrows(CredencialesInvalidasException.class,
                () -> autenticarUsuario.autenticar(correoValido, "ClaveIncorrecta"));

        assertEquals("Credenciales invalidas", ex.getMessage());
        verify(usuarioRepository).obtenerPorCorreo(correoValido);
        verify(passwordEncoder).matches("ClaveIncorrecta", usuario.getClave().getValor());
    }
}

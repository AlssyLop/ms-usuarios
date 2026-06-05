package com.plazoleta.usuarios.infrastructure.security.jwt;

import com.plazoleta.usuarios.dominio.modelo.value.TipoRol;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final long refreshThreshold;

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            @Value("${jwt.refresh-threshold}") long refreshThreshold) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshThreshold = refreshThreshold;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (!jwtTokenProvider.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long idUsuario = jwtTokenProvider.obtenerIdUsuario(token);
        String rolStr = jwtTokenProvider.obtenerRol(token);

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + rolStr));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(idUsuario, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String nuevoToken = refrescarTokenSiEsNecesario(token, rolStr);
        if (nuevoToken != null) {
            response.setHeader("Authorization", "Bearer " + nuevoToken);
        }

        filterChain.doFilter(request, response);
    }

    private String refrescarTokenSiEsNecesario(String token, String rolStr) {
        TipoRol rol = TipoRol.valueOf(rolStr);
        Date ahora = new Date();
        Date iat = jwtTokenProvider.obtenerFechaCreacion(token);
        long elapsed = ahora.getTime() - iat.getTime();

        if ((rol == TipoRol.ADMINISTRADOR || rol == TipoRol.PROPIETARIO)) {
            Date exp = jwtTokenProvider.obtenerExpiracion(token);
            long tiempoRestante = exp.getTime() - ahora.getTime();
            if (tiempoRestante < 600000) {
                Long idUsuario = jwtTokenProvider.obtenerIdUsuario(token);
                return jwtTokenProvider.generarToken(idUsuario, rol);
            }
        } else if (elapsed > refreshThreshold) {
            Long idUsuario = jwtTokenProvider.obtenerIdUsuario(token);
            return jwtTokenProvider.generarToken(idUsuario, rol);
        }

        return null;
    }
}

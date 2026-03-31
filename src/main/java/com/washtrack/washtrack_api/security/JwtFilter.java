package com.washtrack.washtrack_api.security;

import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
  
  private final JwtUtil jwtUtil;
  
  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }
  
  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    
    log.info("[JwtFilter | Metodo: {} | URI: {}]", request.getMethod(), request.getRequestURI());
    
    String authHeader = request.getHeader("Authorization");
    
    if ( authHeader != null && authHeader.startsWith("Bearer ") ) {
      String token = authHeader.substring(ConstantesNumericas.SIETE);
      
      log.info("[JwtFilter | Authorization header: {}]", authHeader);
      
      try {
        if ( jwtUtil.validarToken(token) ) {
          String idUsuario = jwtUtil.extraerIdUsuario(token);
          String tenantId = jwtUtil.extraerTenantId(token);
          String rol = jwtUtil.extraerRol(token);
          
          // Guardar tenantId en el request para usarlo en los controllers
          request.setAttribute("tenantId", tenantId);
          request.setAttribute("idUsuario", idUsuario);
          request.setAttribute("rol", rol);
          
          // Registrar en el contexto de seguridad
          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(
                  idUsuario,
                  null,
                  List.of(new SimpleGrantedAuthority("ROLE_" + rol))
              );
          
          SecurityContextHolder.getContext().setAuthentication(auth);
          log.info("[JWT valido | Usuario: {} | TenantId: {} | Rol: {}]", idUsuario, tenantId, rol);
        }
      }
      catch ( Exception e ) {
        log.error("[JWT invalido o expirado: {}]", e.getMessage());
        SecurityContextHolder.clearContext();
      }
    }
    
    filterChain.doFilter(request, response);
  }
}

package com.washtrack.washtrack_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
  
  // Clave secreta — en produccion muevela a application.properties
  private static final String SECRET_KEY = "washtrack-secret-key-2026-muy-segura-larga";
  // 24 horas
  private static final long EXPIRATION_MS = 86400000;
  
  private Key getSigningKey() {
    byte[] keyBytes = SECRET_KEY.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }
  
  // Generar token
  public String generarToken(String idUsuario, String tenantId, String email, String rol) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("tenantId", tenantId);
    claims.put("email", email);
    claims.put("rol", rol);
    
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(idUsuario)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  // Extraer claims del token
  public Claims extraerClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
  
  // Extraer tenantId del token
  public String extraerTenantId(String token) {
    return (String) extraerClaims(token).get("tenantId");
  }
  
  // Extraer rol del token
  public String extraerRol(String token) {
    return (String) extraerClaims(token).get("rol");
  }
  
  // Extraer idUsuario del token
  public String extraerIdUsuario(String token) {
    return extraerClaims(token).getSubject();
  }
  
  // Verificar si el token esta expirado
  public boolean tokenExpirado(String token) {
    return extraerClaims(token).getExpiration().before(new Date());
  }
  
  // Validar token
  public boolean validarToken(String token) {
    try {
      extraerClaims(token);
      return !tokenExpirado(token);
    }
    catch ( Exception e ) {
      return false;
    }
  }
}

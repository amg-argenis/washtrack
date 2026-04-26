package com.washtrack.washtrack_api.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUsuarioResponse {
  private String idUsuario;
  private String tenantId;
  private String nombre;
  private String email;
  private String rol;
  private boolean activo;
  private String token;
  private String nombreTenant;
}

package com.washtrack.washtrack_api.usuarios.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioEntity {
  private String idUsuario;
  private String tenantId;
  private String nombre;
  private String email;
  private String rol;
  private boolean activo;
  private String createdAt;
  private String token;
}

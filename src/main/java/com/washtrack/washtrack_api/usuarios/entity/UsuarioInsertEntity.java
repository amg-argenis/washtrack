package com.washtrack.washtrack_api.usuarios.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioInsertEntity {
  private String idUsuario;
  private String password;
  private String tenantId;
  private String nombre;
  private String email;
  private String rol;
}

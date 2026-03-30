package com.washtrack.washtrack_api.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuscarUsuarioRequest {
  private String idUsuario;
  private String nombre;
  private String email;
  private String tenantId;
}

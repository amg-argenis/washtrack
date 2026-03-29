package com.washtrack.washtrack_api.usuarios.dto;

import lombok.Data;

@Data
public class BuscarUsuarioRequest {
  private String idUsuario;
  private String nombre;
  private String email;
}

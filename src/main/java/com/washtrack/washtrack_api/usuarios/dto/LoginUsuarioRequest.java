package com.washtrack.washtrack_api.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUsuarioRequest {
  
  @NotBlank(message = "El email de usuario es requerido para ingresar a la plataforma")
  private String email;
  @NotBlank(message = "La claves de usuario es requerida para ingresar a la plataforma")
  private String password;
}

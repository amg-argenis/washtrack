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
public class UsuarioInsertDto {
  
  private String tenantId;
  
  @NotBlank(message = "El campo nombre es requerido, favor de verificar")
  private String nombre;
  @NotBlank(message = "El campo email es requerido, favor de verificar")
  private String email;
  @NotBlank(message = "El campo password es requerido, favor de verificar")
  private String password;
  @NotBlank(message = "El campo rol es requerido, favor de verificar")
  private String rol;
  
}

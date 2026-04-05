package com.washtrack.washtrack_api.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioActualizarDto {
  @NotBlank(message = "Campo Id usuario requerido, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String idUsuario;
  private String tenantId;
  @NotBlank(message = "Campo nombre requerido, favor de verificar")
  private String nombre;
  @NotBlank(message = "Campo email requerido, favor de verificar")
  private String email;
  @NotBlank(message = "Campo password requerido, favor de verificar")
  private String password;
  @NotBlank(message = "Campo rol requerido, favor de verificar")
  private String rol;
}

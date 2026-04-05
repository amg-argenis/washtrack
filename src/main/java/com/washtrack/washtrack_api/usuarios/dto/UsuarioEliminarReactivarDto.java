package com.washtrack.washtrack_api.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioEliminarReactivarDto {
  
  @NotBlank(message = "Campo Id usuario requerido, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String idUsuario;
  @NotBlank(message = "Campo email es requerido, favor de verificar")
  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
      message = "Formato de email no valido")
  private String email;
  private String tenantId;
}

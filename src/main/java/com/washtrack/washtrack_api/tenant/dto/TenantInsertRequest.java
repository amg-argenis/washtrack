package com.washtrack.washtrack_api.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TenantInsertRequest {
  @NotBlank(message = "El nombre del tenant es requerido, favor de verificar")
  @Size(max = 150, message = "El nombre del tenant no puede exceder 150 caracteres")
  private String nombre;
}

package com.washtrack.washtrack_api.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EliminarClienteRequest {
  
  @NotBlank(message = "Id cliente requerido")
  @Size(max = 36, message = "El numero de caracteres es mayor al permitido para el Id")
  private String idCliente;
  private String tenantId;
}

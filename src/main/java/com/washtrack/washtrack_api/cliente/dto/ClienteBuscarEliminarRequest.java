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
public class ClienteBuscarEliminarRequest {
  
  @NotBlank(message = "Id cliente requerido")
  @Size(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String idCliente;
  private String tenantId;
}

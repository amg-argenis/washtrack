package com.washtrack.washtrack_api.entregas.dto;

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
public class EntregaInsertRequest {
  
  private String tenantId;
  @NotBlank(message = "Campo requerido para la operacion")
  @Size(max = 36, message = "El numero de caracteres es mayor al permitido para el Id")
  private String ordenId;
  @NotBlank(message = "Campo requerido para la operacion")
  private String fechaEntrega;
  @NotBlank(message = "Campo requerido para la operacion")
  @Size(min = 1, message = "Dene capturar al menos un elemento")
  private int totalEntregado;
  @NotBlank(message = "Campo requerido para la operacion")
  private boolean conformidadCliente;
  private String observaciones;
  private String estado;
}

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
public class EntregaRequest {
  
  @NotBlank(message = "Debe proporcionar el Id de la entrega a buscar")
  @Size(max = 36, message = "El numero de caracteres es mayor al permitido para el Id")
  String idEntrega;
  String ordenId;
}

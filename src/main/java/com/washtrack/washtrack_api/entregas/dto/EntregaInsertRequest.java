package com.washtrack.washtrack_api.entregas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntregaInsertRequest {
  
  private String tenantId;
  
  @NotBlank(message = "Campo requerido para la operacion, favor de verificar")
  @Size(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String ordenId;
  
  @NotBlank(message = "Campo requerido para la operacion, favor de verificar")
  private String tipo;
  
  @NotNull(message = "Campo fechaEntrega es requerido, favor de verificar")
  private LocalDate fechaEntrega;
  
  @NotNull(message = "Campo totalEntregado es requerido, favor de verificar")
  @Min(value = 1, message = "El total entregado debe ser mayor a 0, favor de verificar")
  private Integer totalEntregado;
  
  @NotNull(message = "Campo requerido para la operacion, favor de verificar")
  private Boolean conformidadCliente;
  
  private String observaciones;
  
}

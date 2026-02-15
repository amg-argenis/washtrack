package com.washtrack.washtrack_api.orden.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertarOrdenRequest {
  
  private String idOrden; // not null para actualizar
  
  @NotBlank(message = "Id cliente es obligatorio, favor de verificar")
  private String clienteId; // not null para actualizar
  
  @NotBlank(message = "Fecha de ingreso es obligatorio")
  private String fechaIngreso; // not null para actualizar
  
  @NotBlank(message = "Estado de la orden obligatorio")
  private String estado; // not null para actualizar
  
  @NotBlank(message = "Total de prendas es obligatorio")
  @Min(value = 1, message = "Total prendas es obligatorio")
  private int totalPrendas; // not null para actualizar
  
  private String observaciones;
  
  private String tenantId; // este es temporal desde el FRONT, not null para actualizar
  
  @NotBlank(message = "Fecha de entrega es obligatorio")
  private String fechaEntrega; // not null para actualizar
  
}

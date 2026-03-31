package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarOrdenServicioRequest {
  
  @NotBlank(message = "Id orden es obligatorio, favor de verificar")
  private String idOrden; // not null para actualizar
  
  @NotBlank(message = "Id cliente es obligatorio, favor de verificar")
  private String clienteId; // not null para actualizar
  
  @NotBlank(message = "Folio de la orden servicio es obligatorio, favor de verificar")
  private String folio;
  
  @NotBlank(message = "Fecha de ingreso es obligatorio")
  private String fechaIngreso; // not null para actualizar
  
  @NotBlank(message = "Estado de la orden obligatorio")
  private String estado; // not null para actualizar
  
  @Min(value = 1, message = "Total prendas es obligatorio")
  private int totalPrendas; // not null para actualizar
  
  private String observaciones;
  
  @NotBlank(message = "Fecha de entrega es obligatorio")
  private String fechaEntrega; // not null para actualizar
  
  private String tenantId;
  
}

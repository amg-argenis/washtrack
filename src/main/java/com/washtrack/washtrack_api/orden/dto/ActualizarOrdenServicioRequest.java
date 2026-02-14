package com.washtrack.washtrack_api.orden.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActualizarOrdenServicioRequest {
  
  @NotNull(message = "Id orden es obligatorio, favor de verificar")
  private String idOrden; // not null para actualizar
  
  @NotNull(message = "Id cliente es obligatorio, favor de verificar")
  private String clienteId; // not null para actualizar
  
  @NotNull(message = "Folio de la orden servicio es obligatorio, favor de verificar")
  private String folio;
  
  @NotNull(message = "Fecha de ingreso es obligatorio")
  private String fechaIngreso; // not null para actualizar
  
  @NotNull(message = "Estado de la orden obligatorio")
  private String estado; // not null para actualizar
  
  @Min(value = 1, message = "Total prendas es obligatorio")
  private int totalPrendas; // not null para actualizar
  
  private String observaciones;
  
  @NotNull(message = "Fecha de entrega es obligatorio")
  private String fechaEntrega; // not null para actualizar
  
}

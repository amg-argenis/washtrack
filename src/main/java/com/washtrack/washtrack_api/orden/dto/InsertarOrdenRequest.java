package com.washtrack.washtrack_api.orden.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class InsertarOrdenRequest {
  
  private String idOrden;
  @NotNull(message = "Cliente Id es obligatorio, favor de verificar")
  private String clienteId;
  @NotNull(message = "Fecha de ingreso es obligatorio")
  private String fechaIngreso; // not null para actualizar e insertar
  @NotNull(message = "Estado de la orden obligatorio")
  private String estado; // not null para actualizar e insertar
  @NotNull(message = "Total prendas es obligatorio")
  @Min(value = 1, message = "Total prendas es obligatorio")
  private int totalPrendas; // not null para actualizar e insertar
  private String observaciones;
  private String tenantId; // este es temporal
  @NotNull(message = "Fecha de entrega es obligatorio")
  private String fechaEntrega; // not null para actualizar e insertar
  
}

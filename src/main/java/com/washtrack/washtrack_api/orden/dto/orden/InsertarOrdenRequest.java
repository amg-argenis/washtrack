package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Clase que se una para el insert y para enviar datos al cliente
 */
@Data
public class InsertarOrdenRequest {
  
  private String idOrden;
  
  @NotBlank(message = "Id cliente es obligatorio, favor de verificar")
  private String clienteId;
  
  @NotBlank(message = "Fecha de ingreso es obligatorio")
  private String fechaIngreso;
  
  @NotBlank(message = "Estado de la orden obligatorio")
  private String estado;
  
  @Min(value = 1, message = "Total prendas es obligatorio")
  private int totalPrendas;
  
  private String observaciones;
  private String tenantId;
  private String fechaEntrega;
  
}

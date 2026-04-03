package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Clase que se una para el insert y para enviar datos al cliente
 */
@Data
public class InsertarOrdenRequest {
  
  private String idOrden;
  
  @NotBlank(message = "Id cliente es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id cliente")
  private String clienteId;
  
  @NotBlank(message = "Fecha de ingreso es requerida")
  @Pattern(
      regexp = "^\\d{4}-\\d{2}-\\d{2}$",
      message = "El formato de fecha debe ser yyyy-MM-dd"
  )
  private String fechaIngreso;
  
  @NotBlank(message = "Estado de la orden obligatorio")
  private String estado;
  
  @Min(value = 1, message = "Total de prendas es obligatorio")
  private int totalPrendas;
  
  private String observaciones;
  private String tenantId;
  
  @NotBlank(message = "La fecha de entrega es requerida")
  @Pattern(
      regexp = "^\\d{4}-\\d{2}-\\d{2}$",
      message = "El formato de fecha debe ser yyyy-MM-dd"
  )
  private String fechaEntrega;
  
}

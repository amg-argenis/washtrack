package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ActualizarOrdenServicioRequest {
  
  @NotBlank(message = "Id orden es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id orden")
  private String idOrden;
  
  @NotBlank(message = "Id cliente es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id cliente")
  private String clienteId;
  
  private String nombreCliente;
  
  @NotBlank(message = "Folio de la orden servicio es obligatorio, favor de verificar")
  @Length(min = 1, max = 14, message = "El numero de caracteres es invalido al permitido para el folio")
  private String folio;
  
  @NotBlank(message = "Fecha de ingreso es requerida")
  @Pattern(
      regexp = "^\\d{4}-\\d{2}-\\d{2}$",
      message = "El formato de fecha debe ser yyyy-MM-dd"
  )
  private String fechaIngreso;
  
  @NotBlank(message = "Estado de la orden obligatorio")
  private String estado; // not null para actualizar
  
  @Min(value = 1, message = "Total prendas es obligatorio")
  private int totalPrendas;
  
  private String observaciones;
  
  @NotBlank(message = "Fecha de entrega es requerida")
  @Pattern(
      regexp = "^\\d{4}-\\d{2}-\\d{2}$",
      message = "El formato de fecha debe ser yyyy-MM-dd"
  )
  private String fechaEntrega;
  
  private String tenantId;
  
}

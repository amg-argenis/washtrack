package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EliminarOrdenServicioRequest {
  
  @NotBlank(message = "Id orden servicio es obligatorio")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id orden")
  private String idOrden;
  
  @NotBlank(message = "Folio orden servicio es obligatorio")
  @Length(min = 1, max = 14, message = "El numero de caracteres es invalido al permitido para el folio")
  private String folio;
  
  private String tenantId;
}

package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BuscarOrdenRequest {
  
  @NotNull(message = "Id Orden es obligatorio para la busqueda")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String idOrden;
  
  @NotNull(message = "El folio de la orden es obligatorio para la busqueda")
  @Length(min = 1, max = 14, message = "El numero de caracteres es invalido al permitido para el folio")
  private String folio;
  
  private String tenantId;
  
}

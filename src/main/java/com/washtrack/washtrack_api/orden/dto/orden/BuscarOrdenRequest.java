package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BuscarOrdenRequest {
  
  @NotNull(message = "Id Orden es obligatorio para la busqueda")
  @Length(min = 10, max = 36, message = "El id orden exede los caracteres, favor de validar")
  private String idOrden;
  @NotNull(message = "El folio de la orden es obligatorio para la busqueda")
  private String folio;
  
  private String tenantId;
  
}

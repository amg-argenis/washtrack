package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuscarOrdenRequest {
  
  @NotNull(message = "Id Orden es obligatorio para la busqueda")
  private String idOrden;
  @NotNull(message = "El folio de la orden es obligatorio para la busqueda")
  private String folio;
  
}

package com.washtrack.washtrack_api.orden.dto.orden;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EliminarOrdenServicioRequest {
  
  @NotBlank(message = "Id orden servicio es obligatorio")
  private String idOrden;
  
  @NotBlank(message = "Folio orden servicio es obligatorio")
  private String folio;
}

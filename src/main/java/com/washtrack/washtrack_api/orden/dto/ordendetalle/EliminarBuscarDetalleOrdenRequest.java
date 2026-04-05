package com.washtrack.washtrack_api.orden.dto.ordendetalle;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EliminarBuscarDetalleOrdenRequest {
  
  @NotBlank(message = "Id detalle orden es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id detalle orden")
  private String idDetalleOrden;
  
  @NotBlank(message = "Id orden es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id orden")
  private String ordenId;
  
  private String tenantId;
  
}

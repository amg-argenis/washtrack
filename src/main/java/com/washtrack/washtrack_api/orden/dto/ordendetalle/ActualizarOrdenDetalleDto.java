package com.washtrack.washtrack_api.orden.dto.ordendetalle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class ActualizarOrdenDetalleDto {
  
  @NotBlank(message = "Id detalle orden es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id detalle orden")
  private String idDetalleOrden;
  
  @NotBlank(message = "Id orden es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id orden")
  private String ordenId;
  
  @NotBlank(message = "Id proceso es obligatorio, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id proceso")
  private String procesoId;
  
  @NotBlank(message = "El ripo de prenda es obligatorio, favor de verificar")
  private String tipoPrenda;
  
  @Min(value = 1, message = "La cantidad de prendas es requerida, favor de verificar")
  private int cantidad;
  
  @NotBlank(message = "El color de referencia es obligatorio, favor de verificar")
  private String colorReferencia;
  
  private String tenantId;
  
}

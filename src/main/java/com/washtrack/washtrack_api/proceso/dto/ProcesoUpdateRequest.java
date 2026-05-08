package com.washtrack.washtrack_api.proceso.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcesoUpdateRequest {
  private String tenantid;
  @NotBlank(message = "Campo requerido para la operacion, favor de verificar")
  private String nombre;
  @NotBlank(message = "Campo requerido para la operacion, favor de verificar")
  private String descripcion;
  @NotNull(message = "Campo requerido para la operacion, favor de verificar")
  @DecimalMin(value = "1.0", message = "El precio debe ser mayor a 0.0, favor de verificar")
  private BigDecimal preciounitario;
  @NotNull(message = "Campo requerido para la operacion, favor de verificar")
  private String codigo;
}

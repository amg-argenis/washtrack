package com.washtrack.washtrack_api.cliente.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarClienteDto {
  
  @NotBlank(message = "Campo Id cliente requerido, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String idCliente;
  
  private String tenantId;
  
  @NotBlank(message = "Campo nombre cliente requerido, favor de verificar")
  private String nombre;
  
  @NotBlank(message = "Campo contacto cliente requerido, favor de verificar")
  private String contacto;
  
  @NotBlank(message = "Campo telefono cliente requerido, favor de verificar")
  private String telefono;
  
  @NotBlank(message = "Campo email cliente requerido, favor de verificar")
  private String email;
  
  @NotNull(message = "Campo credito cliente requerido, favor de verificar")
  private Boolean creditoHabilitado;
  
  @NotNull(message = "Campo limite de credito requerido, favor de verificar")
  @DecimalMin(value = "0.0", message = "El limite de credito debe ser mayor o igual a 0")
  private Double limiteCredito;
  
  @NotNull(message = "Campo cliente activo es requerido, favor de verificar")
  private Boolean activo;
  
}

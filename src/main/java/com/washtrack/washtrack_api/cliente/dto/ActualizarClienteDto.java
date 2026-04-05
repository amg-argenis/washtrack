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
  
  @NotBlank(message = "Campo Id cliente requerido")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
  private String idCliente;
  
  private String tenantId;
  
  @NotBlank(message = "Campo nombre cliente requerido")
  private String nombre;
  
  @NotBlank(message = "Campo contacto cliente requerido")
  private String contacto;
  
  @NotBlank(message = "Campo telefono cliente requerido")
  private String telefono;
  
  @NotBlank(message = "Campo email cliente requerido")
  private String email;
  
  @NotNull(message = "Campo credito cliente requerido")
  private Boolean creditoHabilitado;
  
  @NotNull(message = "Campo limite de credito requerido")
  @DecimalMin(value = "0.0", message = "El limite de credito debe ser mayor o igual a 0")
  private Double limiteCredito;
  
  @NotNull(message = "Campo activo de cliente requerido")
  private Boolean activo;
  
}

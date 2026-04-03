package com.washtrack.washtrack_api.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
  
  @NotBlank(message = "Campo Id cliente requerido")
  @Size(max = 36, message = "El numero de caracteres es mayor al permitido para el Id")
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
  @NotBlank(message = "Campo credito cliente requerido")
  private boolean creditoHabilitado;
  @NotBlank(message = "Campo limite de credito de clitente requerido")
  private Double limiteCredito;
  @NotBlank(message = "Campo activo/inactivo de clitente requerido")
  private boolean activo;
  
}

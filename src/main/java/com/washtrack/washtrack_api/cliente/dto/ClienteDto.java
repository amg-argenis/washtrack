package com.washtrack.washtrack_api.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
  
  private String idCliente;
  private String tenantId;
  private String nombre;
  private String contacto;
  private String telefono;
  private String email;
  private boolean creditoHabilitado;
  private Double limiteCredito;
  private boolean activo;
  
}

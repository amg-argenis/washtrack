package com.washtrack.washtrack_api.cliente.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientesEntity {
  private String idCliente;
  private String tenantId;
  private String nombre;
  private String contacto;
  private String telefono;
  private String email;
  private Boolean creditoHabilitado;
  private Double limiteCredito;
  private Boolean activo;
}

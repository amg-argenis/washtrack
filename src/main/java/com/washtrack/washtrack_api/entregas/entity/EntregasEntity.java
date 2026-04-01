package com.washtrack.washtrack_api.entregas.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntregasEntity {
  private String idEntrega;
  private String tenantId;
  private String ordenId;
  private String fechaEntrega;
  private String totalEntregado;
  private String conformidadCliente;
  private String observaciones;
}

package com.washtrack.washtrack_api.orden.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdenesEntity {
  
  private String idOrden;
  private String clienteId;
  private String folio;
  private String fechaIngreso;
  private String estado;
  private int totalPrendas;
  private String observaciones;
  private String tenantId;
  private String fechaEntrega;
  
}

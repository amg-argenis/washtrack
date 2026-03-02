package com.washtrack.washtrack_api.orden.dto.orden;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdenesDto {
  
  private String idOrden;
  private String clienteId;
  private String folio;
  private String fechaIngreso;
  private String estado;
  private int totalPrendas;
  private String observaciones;
  private String createdAt;
  private String tenantId; // este es temporal
  private String fechaEntrega;
  
}

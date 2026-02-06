package com.washtrack.washtrack_api.orden.dto;

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
  private String tenantId; // este es temporal
  
}

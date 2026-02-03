package com.washtrack.washtrack_api.orden.dto;

import lombok.Data;

@Data
public class InsertarOrdenRequest {
  
  private String idOrden;
  private String clienteId;
  private String fechaIngreso;
  private String estado;
  private int totalPrendas;
  private String observaciones;
  private String tenantId; // este es temporal
  
}

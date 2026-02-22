package com.washtrack.washtrack_api.orden.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DetalleOrdenEntity {
  
  private String idDetalleOrden;
  private String ordenId;
  private String procesoId;
  private String tipoPrenda;
  private int cantidad;
  private String colorReferencia;
  private String tenantId;
  
}

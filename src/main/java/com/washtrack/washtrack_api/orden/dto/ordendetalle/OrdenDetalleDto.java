package com.washtrack.washtrack_api.orden.dto.ordendetalle;

import lombok.Data;

@Data
public class OrdenDetalleDto {
  
  private String idDetalleOrden;
  private String ordenId;
  private String procesoId;
  private String tipoPrenda;
  private int cantidad;
  private String colorReferencia;
  private String tenantId;
  
}

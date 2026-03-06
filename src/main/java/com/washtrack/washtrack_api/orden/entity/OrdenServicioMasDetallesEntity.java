package com.washtrack.washtrack_api.orden.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrdenServicioMasDetallesEntity {
  
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
  private List<DetalleOrdenEntity> ordenesDetalleDto;
  
}

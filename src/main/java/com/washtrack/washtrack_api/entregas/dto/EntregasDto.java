package com.washtrack.washtrack_api.entregas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntregasDto {
  private String idEntrega;
  private String ordenId;
  private String fechaEntrega;
  private int totalEntregado;
  private boolean conformidadCliente;
  private String observaciones;
  private String estado;
  private String fechaCreacion;
  private String tipo;
}

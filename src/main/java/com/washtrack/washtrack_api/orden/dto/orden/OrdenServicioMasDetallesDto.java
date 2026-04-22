package com.washtrack.washtrack_api.orden.dto.orden;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.ActualizarOrdenDetalleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrdenServicioMasDetallesDto {
  private String idOrden;
  private String clienteId;
  private String nombreCliente;
  private String folio;
  private String fechaIngreso;
  private String estado;
  private int totalPrendas;
  private String observaciones;
  private String createdAt;
  private String tenantId;
  private String fechaEntrega;
  private List<ActualizarOrdenDetalleDto> ordenesDetalleDto;
  private int totalDetalle;
}

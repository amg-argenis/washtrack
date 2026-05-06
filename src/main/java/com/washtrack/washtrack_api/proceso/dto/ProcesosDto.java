package com.washtrack.washtrack_api.proceso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProcesosDto {
  private String idproceso;
  private String tenantid;
  private String nombre;
  private String descripcion;
  private BigDecimal preciounitario;
  private boolean activo;
}

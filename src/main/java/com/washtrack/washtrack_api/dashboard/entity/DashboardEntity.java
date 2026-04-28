package com.washtrack.washtrack_api.dashboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardEntity {
  private String ordenesActivas;
  private String clientesActivos;
  private String entregasMes;
  private String prendasEnProceso;
  private String ordenesListas;
  private String ordenesEntregadasMes;
}

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
  private int ordenesActivas;
  private int clientesActivos;
  private int entregasMes;
  private int prendasEnProceso;
  private int ordenesListas;
  private int ordenesEntregadasMes;
}

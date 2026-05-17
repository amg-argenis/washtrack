package com.washtrack.washtrack_api.tenant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TenantEntity {
  private String idtenant;
  private String nombre;
  private boolean activo;
  private String createdat;
}

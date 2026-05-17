package com.washtrack.washtrack_api.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TenantDto {
  private String idtenant;
  private String nombre;
  private boolean activo;
  private String createdat;
}

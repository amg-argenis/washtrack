package com.washtrack.washtrack_api.tenant.response;

import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TenantResponseRepository {
  private Integer codigobd;
  private TenantEntity tenantEntity;
  private List<TenantEntity> entityList;
}
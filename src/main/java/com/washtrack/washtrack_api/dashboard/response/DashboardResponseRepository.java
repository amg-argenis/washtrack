package com.washtrack.washtrack_api.dashboard.response;

import com.washtrack.washtrack_api.dashboard.entity.DashboardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardResponseRepository {
  private Integer codigobd;
  private DashboardEntity dashboardEntity;
}

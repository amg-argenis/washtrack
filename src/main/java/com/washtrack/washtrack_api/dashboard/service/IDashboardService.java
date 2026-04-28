package com.washtrack.washtrack_api.dashboard.service;

import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface IDashboardService {
  ServiceResult<Object> obtenerDashboardService(String tenantId);
}

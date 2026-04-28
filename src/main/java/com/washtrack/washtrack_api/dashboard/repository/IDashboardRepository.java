package com.washtrack.washtrack_api.dashboard.repository;

import com.washtrack.washtrack_api.dashboard.response.DashboardResponseRepository;

public interface IDashboardRepository {
  DashboardResponseRepository buscarDashboardRepository(String tenantId);
}

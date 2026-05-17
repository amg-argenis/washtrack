package com.washtrack.washtrack_api.tenant.repository;

import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import com.washtrack.washtrack_api.tenant.response.TenantResponseRepository;

public interface ITenantRepository {
  TenantResponseRepository insertarTenantRepository(TenantEntity tenant);
  
  TenantResponseRepository actualizarTenantRepository(TenantEntity tenant);
  
  TenantResponseRepository eliminarTenantRepository(String idtenant);
  
  TenantResponseRepository buscarTenantRepository(String idtenant);
  
  TenantResponseRepository listarTenantsRepository();
}
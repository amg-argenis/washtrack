package com.washtrack.washtrack_api.tenant.service;

import com.washtrack.washtrack_api.tenant.dto.TenantInsertRequest;
import com.washtrack.washtrack_api.tenant.dto.TenantUpdateRequest;
import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface ITenantService {
  ServiceResult<Object> insertarTenantService(TenantInsertRequest request);
  
  ServiceResult<Object> actualizarTenantService(TenantUpdateRequest request);
  
  ServiceResult<Object> eliminarTenantService(String idtenant);
  
  ServiceResult<Object> buscarTenantService(String idtenant);
  
  ServiceResult<Object> listarTenantsService();
}
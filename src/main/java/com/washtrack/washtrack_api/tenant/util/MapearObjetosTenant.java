package com.washtrack.washtrack_api.tenant.util;

import com.washtrack.washtrack_api.tenant.dto.TenantDto;
import com.washtrack.washtrack_api.tenant.dto.TenantInsertRequest;
import com.washtrack.washtrack_api.tenant.dto.TenantUpdateRequest;
import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetosTenant {
  
  public Map<String, Object> parametrizarTenantInsert(TenantEntity tenant) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pa_idtenant", tenant.getIdtenant());
    paramMap.put("pa_nombre", tenant.getNombre());
    return paramMap;
  }
  
  public Map<String, Object> parametrizarTenantUpdate(TenantEntity tenant) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pa_idtenant", tenant.getIdtenant());
    paramMap.put("pa_nombre", tenant.getNombre());
    return paramMap;
  }
  
  public TenantEntity mapearFromInsertRequestToEntity(TenantInsertRequest request) {
    return TenantEntity.builder()
        .nombre(request.getNombre())
        .build();
  }
  
  public TenantEntity mapearFromUpdateRequestToEntity(TenantUpdateRequest request) {
    return TenantEntity.builder()
        .idtenant(request.getIdtenant())
        .nombre(request.getNombre())
        .build();
  }
  
  public TenantDto mapearFromEntityToDto(TenantEntity entity) {
    return TenantDto.builder()
        .idtenant(entity.getIdtenant())
        .nombre(entity.getNombre())
        .activo(entity.isActivo())
        .createdat(entity.getCreatedat())
        .build();
  }
}
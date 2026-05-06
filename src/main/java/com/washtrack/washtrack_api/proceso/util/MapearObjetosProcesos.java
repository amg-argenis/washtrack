package com.washtrack.washtrack_api.proceso.util;

import com.washtrack.washtrack_api.proceso.dto.ProcesosDto;
import com.washtrack.washtrack_api.proceso.dto.ProcesosRequest;
import com.washtrack.washtrack_api.proceso.entity.ProcesosEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetosProcesos {
  
  public Map<String, Object> parametrizarProcesosInsert(ProcesosEntity procesos) {
    Map<String, Object> paramMap = new HashMap<>();
    
    paramMap.put("pa_idproceso", procesos.getIdproceso());
    paramMap.put("pa_tenantid", procesos.getTenantid());
    paramMap.put("pa_nombre", procesos.getNombre());
    paramMap.put("pa_descripcion", procesos.getDescripcion());
    paramMap.put("pa_preciounitario", procesos.getPreciounitario());
    paramMap.put("pa_activo", procesos.isActivo());
    
    return paramMap;
  }
  
  // MAPEAR OBJETOS
  public ProcesosEntity mapearFromDtoToEntity(ProcesosRequest procesosRequest) {
    return ProcesosEntity.builder()
        .idproceso(procesosRequest.getTenantid())
        .tenantid(procesosRequest.getTenantid())
        .nombre(procesosRequest.getNombre())
        .descripcion(procesosRequest.getDescripcion())
        .preciounitario(procesosRequest.getPreciounitario())
        .build();
  }
  
  public ProcesosDto mapearFromEntityToDto(ProcesosEntity procesosEntity) {
    return ProcesosDto.builder()
        .idproceso(procesosEntity.getIdproceso())
        .tenantid(procesosEntity.getTenantid())
        .nombre(procesosEntity.getNombre())
        .descripcion(procesosEntity.getDescripcion())
        .preciounitario(procesosEntity.getPreciounitario())
        .activo(procesosEntity.isActivo())
        .build();
  }
}

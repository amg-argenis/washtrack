package com.washtrack.washtrack_api.entregas.util;

import com.washtrack.washtrack_api.entregas.dto.EntregaActualizarRequest;
import com.washtrack.washtrack_api.entregas.dto.EntregaInsertRequest;
import com.washtrack.washtrack_api.entregas.dto.EntregasDto;
import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetosEntregas {
  
  // PARAMETRIZAR OBJETOS **********************************************************************************************
  
  public Map<String, Object> insertarEntregaParams(EntregasEntity entregasEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_identrega", entregasEntity.getIdEntrega());
    params.put("pa_tenantid", entregasEntity.getTenantId());
    params.put("pa_ordenid", entregasEntity.getOrdenId());
    params.put("pa_tipo", entregasEntity.getTipo());
    params.put("pa_fechaentrega", entregasEntity.getFechaEntrega());
    params.put("pa_totalentregado", entregasEntity.getTotalEntregado());
    params.put("pa_conformidad", entregasEntity.getConformidadCliente());
    params.put("pa_observaciones", entregasEntity.getObservaciones());
    
    return params;
  }
  
  public Map<String, Object> actualizarEntregaParams(EntregasEntity entregasEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_identrega", entregasEntity.getIdEntrega());
    params.put("pa_tenantid", entregasEntity.getTenantId());
    params.put("pa_ordenid", entregasEntity.getOrdenId());
    params.put("pa_tipo", entregasEntity.getTipo());
    params.put("pa_fechaentrega", entregasEntity.getFechaEntrega());
    params.put("pa_totalentregado", entregasEntity.getTotalEntregado());
    params.put("pa_conformidad", entregasEntity.getConformidadCliente());
    params.put("pa_observaciones", entregasEntity.getObservaciones());
    params.put("pa_estado", entregasEntity.getEstado());
    
    return params;
  }
  
  // MAPEAR OBJETOS ***************************************************************************************************
  
  public EntregasEntity entregasEntityFromDto(EntregaInsertRequest entregasDto) {
    return EntregasEntity.builder()
        .ordenId(entregasDto.getOrdenId())
        .tenantId(entregasDto.getTenantId())
        .tipo(entregasDto.getTipo())
        .fechaEntrega(String.valueOf(entregasDto.getFechaEntrega()))
        .totalEntregado(entregasDto.getTotalEntregado())
        .conformidadCliente(entregasDto.getConformidadCliente())
        .observaciones(entregasDto.getObservaciones())
        .fechaEntrega(String.valueOf(entregasDto.getFechaEntrega()))
        .build();
  }
  
  public EntregasEntity entregasEntityFromDto(EntregaActualizarRequest entregasDto) {
    return EntregasEntity.builder()
        .idEntrega(entregasDto.getIdEntrega())
        .tenantId(entregasDto.getTenantId())
        .ordenId(entregasDto.getOrdenId())
        .tipo(entregasDto.getTipo())
        .fechaEntrega(String.valueOf(entregasDto.getFechaEntrega()))
        .totalEntregado(entregasDto.getTotalEntregado())
        .conformidadCliente(entregasDto.getConformidadCliente())
        .observaciones(entregasDto.getObservaciones())
        .estado(entregasDto.getEstado())
        .build();
  }
  
  public EntregasDto entregasDtoFromEntity(EntregasEntity entregasEntity) {
    return EntregasDto.builder()
        .idEntrega(entregasEntity.getIdEntrega())
        .ordenId(entregasEntity.getOrdenId())
        .fechaEntrega(entregasEntity.getFechaEntrega())
        .totalEntregado(entregasEntity.getTotalEntregado())
        .conformidadCliente(entregasEntity.getConformidadCliente())
        .observaciones(entregasEntity.getObservaciones())
        .estado(entregasEntity.getEstado())
        .fechaCreacion(entregasEntity.getFechaCreacion())
        .tipo(entregasEntity.getTipo())
        .build();
  }
  
}

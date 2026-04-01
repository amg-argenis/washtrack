package com.washtrack.washtrack_api.entregas.util;

import com.washtrack.washtrack_api.entregas.dto.EntregasDto;
import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import lombok.extern.slf4j.Slf4j;
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
    params.put("pa_fechaentrega", entregasEntity.getFechaEntrega());
    params.put("pa_totalentregado", entregasEntity.getTotalEntregado());
    params.put("pa_conformidad", entregasEntity.isConformidadCliente());
    params.put("pa_observaciones", entregasEntity.getObservaciones());
    
    return params;
  }
  
  // MAPEAR OBJETOS ***************************************************************************************************
  
  public EntregasEntity entregasEntityFromDto(EntregasDto entregasDto) {
    return EntregasEntity.builder()
        .idEntrega(entregasDto.getIdEntrega())
        .tenantId(entregasDto.getTenantId())
        .ordenId(entregasDto.getOrdenId())
        .fechaEntrega(entregasDto.getFechaEntrega())
        .totalEntregado(entregasDto.getTotalEntregado())
        .conformidadCliente(entregasDto.isConformidadCliente())
        .observaciones(entregasDto.getObservaciones())
        .estado(entregasDto.getEstado())
        .fechaEntrega(entregasDto.getFechaEntrega())
        .build();
  }
  
  public EntregasDto entregasDtoFromEntity(EntregasEntity entregasEntity) {
    return EntregasDto.builder()
        .idEntrega(entregasEntity.getIdEntrega())
        .ordenId(entregasEntity.getOrdenId())
        .fechaEntrega(entregasEntity.getFechaEntrega())
        .totalEntregado(entregasEntity.getTotalEntregado())
        .conformidadCliente(entregasEntity.isConformidadCliente())
        .observaciones(entregasEntity.getObservaciones())
        .estado(entregasEntity.getEstado())
        .fechaCreacion(entregasEntity.getFechaCreacion())
        .build();
  }
  
}

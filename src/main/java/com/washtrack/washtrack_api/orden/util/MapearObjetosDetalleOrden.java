package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.EliminarBuscarDetalleOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.InsertDetalleOrden;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.ActualizarOrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MapearObjetosDetalleOrden {
  
  public DetalleOrdenEntity mapearInsertDetalleOrden(InsertDetalleOrden insertDetalleOrden) {
    return DetalleOrdenEntity.builder()
        .idDetalleOrden(insertDetalleOrden.getIdDetalleOrden())
        .ordenId(insertDetalleOrden.getOrdenId())
        .procesoId(insertDetalleOrden.getProcesoId())
        .tipoPrenda(insertDetalleOrden.getTipoPrenda())
        .cantidad(insertDetalleOrden.getCantidad())
        .colorReferencia(insertDetalleOrden.getColorReferencia())
        .tenantId(insertDetalleOrden.getTenantId())
        .build();
  }
  
  public DetalleOrdenEntity mapearDtoToentityDetalleOrden(ActualizarOrdenDetalleDto actualizarOrdenDetalleDto) {
    return DetalleOrdenEntity.builder()
        .idDetalleOrden(actualizarOrdenDetalleDto.getIdDetalleOrden())
        .ordenId(actualizarOrdenDetalleDto.getOrdenId())
        .procesoId(actualizarOrdenDetalleDto.getProcesoId())
        .tipoPrenda(actualizarOrdenDetalleDto.getTipoPrenda())
        .cantidad(actualizarOrdenDetalleDto.getCantidad())
        .colorReferencia(actualizarOrdenDetalleDto.getColorReferencia())
        .tenantId(actualizarOrdenDetalleDto.getTenantId())
        .build();
  }
  
  /**
   * Mapear objeto para buscar y eliminar detalle orden en la BD
   *
   * @param ordenDetalleDto
   * @return
   */
  public DetalleOrdenEntity mapearDtoToentityDetalleOrden(EliminarBuscarDetalleOrdenRequest ordenDetalleDto) {
    return DetalleOrdenEntity.builder()
        .idDetalleOrden(ordenDetalleDto.getIdDetalleOrden())
        .ordenId(ordenDetalleDto.getOrdenId())
        .tenantId(ordenDetalleDto.getTenantId())
        .build();
  }
  
  /**
   * Mapear para serviceResult con obneto encontrado en la BD
   *
   * @param ordenEntity
   * @return
   */
  public ActualizarOrdenDetalleDto mapearEntityToDtoOrdenDetalle(DetalleOrdenEntity ordenEntity) {
    log.info("[Mapeando a DTO objeto orden detalle...]");
    
    return ActualizarOrdenDetalleDto.builder()
        .idDetalleOrden(ordenEntity.getIdDetalleOrden())
        .ordenId(ordenEntity.getOrdenId())
        .procesoId(ordenEntity.getProcesoId())
        .tipoPrenda(ordenEntity.getTipoPrenda())
        .cantidad(ordenEntity.getCantidad())
        .colorReferencia(ordenEntity.getColorReferencia())
        .tenantId(ordenEntity.getTenantId())
        .build();
  }
  
  public Map<String, Object> parametrizarDetalleOrdenes(DetalleOrdenEntity detalleOrden) {
    Map<String, Object> paramMap = new HashMap<>();
    
    paramMap.put("pa_idordendetalle", detalleOrden.getIdDetalleOrden());
    paramMap.put("pa_ordenid", detalleOrden.getOrdenId());
    paramMap.put("pa_procesoid", detalleOrden.getProcesoId());
    paramMap.put("pa_tipoprenda", detalleOrden.getTipoPrenda());
    paramMap.put("pa_cantidad", detalleOrden.getCantidad());
    paramMap.put("pa_colorreferencia", detalleOrden.getColorReferencia());
    paramMap.put("pa_tenantid", detalleOrden.getTenantId());
    
    return paramMap;
  }
  
  public Map<String, Object> parametrizarDetalleOrdenBuscarEliminar(DetalleOrdenEntity detalleOrden) {
    Map<String, Object> paramMap = new HashMap<>();
    
    paramMap.put("pa_idordendetalle", detalleOrden.getIdDetalleOrden());
    paramMap.put("pa_ordenid", detalleOrden.getOrdenId());
    paramMap.put("pa_tenantid", detalleOrden.getTenantId());
    
    return paramMap;
  }
  
}

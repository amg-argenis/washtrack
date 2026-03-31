package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MapearObjetosDetalleOrden {
  
  /**
   * Mapear objeto para buscar orden detalle en la BD
   *
   * @param ordenDetalleDto
   * @return
   */
  public DetalleOrdenEntity mapearDtoToentityDetalleOrden(OrdenDetalleDto ordenDetalleDto) {
    log.info("[Mapeando a Entity objeto orden detalle...]");
    return DetalleOrdenEntity.builder()
        .idDetalleOrden(ordenDetalleDto.getIdDetalleOrden())
        .ordenId(ordenDetalleDto.getOrdenId())
        .procesoId(ordenDetalleDto.getProcesoId())
        .tipoPrenda(ordenDetalleDto.getTipoPrenda())
        .cantidad(ordenDetalleDto.getCantidad())
        .colorReferencia(ordenDetalleDto.getColorReferencia())
        .tenantId(ordenDetalleDto.getTenantId())
        .build();
  }
  
  /**
   * Mapear para serviceResult con obneto encontrado en la BD
   *
   * @param ordenEntity
   * @return
   */
  public OrdenDetalleDto mapearEntityToDtoOrdenDetalle(DetalleOrdenEntity ordenEntity) {
    log.info("[Mapeando a DTO objeto orden detalle...]");
    
    return OrdenDetalleDto.builder()
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
  
}

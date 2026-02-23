package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        .build();
  }
  
  /**
   * Mapear para serviceResult con obneto encontrado en la BD
   *
   * @param ordenEntity
   * @return
   */
  public OrdenDetalleDto mapearEntityTodtoOrdenDetalle(DetalleOrdenEntity ordenEntity) {
    log.info("[Mapeando a DTO objeto orden detalle...]");
    
    return OrdenDetalleDto.builder()
        .idDetalleOrden(ordenEntity.getIdDetalleOrden())
        .ordenId(ordenEntity.getOrdenId())
        .procesoId(ordenEntity.getProcesoId())
        .tipoPrenda(ordenEntity.getTipoPrenda())
        .cantidad(ordenEntity.getCantidad())
        .colorReferencia(ordenEntity.getColorReferencia())
        // momentaneo para pruebas el tenant Id
        .tenantId(ordenEntity.getTenantId())
        .build();
  }
  
}

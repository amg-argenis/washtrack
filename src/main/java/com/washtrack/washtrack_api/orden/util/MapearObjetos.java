package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.dto.orden.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.EliminarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenesDto;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetos {
  
  public Map<String, Object> parametrizarOrdenes(OrdenesEntity orden) {
    Map<String, Object> paramMap = new HashMap<>();
    
    paramMap.put("pa_idorden", orden.getIdOrden());
    paramMap.put("pa_clienteid", orden.getClienteId());
    paramMap.put("pa_fechaingreso", orden.getFechaIngreso());
    paramMap.put("pa_estado", orden.getEstado());
    paramMap.put("pa_totalprendas", orden.getTotalPrendas());
    paramMap.put("pa_observaciones", orden.getObservaciones());
    paramMap.put("pa_tenantid", orden.getTenantId());
    paramMap.put("pa_fechaentrega", orden.getFechaEntrega());
    
    return paramMap;
  }
  
  public Map<String, Object> parametrizarActualizarOrdenes(OrdenesEntity orden) {
    Map<String, Object> paramMap = new HashMap<>();
    
    paramMap.put("pa_tenantid", "a051a168-fa2a-11f0-aab7-e66133dbb0de"); // hardcodeado por ahora
    paramMap.put("pa_idorden", orden.getIdOrden());
    paramMap.put("pa_clienteid", orden.getClienteId());
    paramMap.put("pa_folio", orden.getFolio());
    paramMap.put("pa_fechaingreso", orden.getFechaIngreso());
    paramMap.put("pa_estado", orden.getEstado());
    paramMap.put("pa_totalprendas", orden.getTotalPrendas());
    paramMap.put("pa_observaciones", orden.getObservaciones());
    paramMap.put("pa_fechaentrega", orden.getFechaEntrega());
    
    return paramMap;
  }
  
  public Map<String, Object> parametrizarEliminarOrdenes(OrdenesEntity orden) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pa_idorden", orden.getIdOrden());
    paramMap.put("pa_folio", orden.getFolio());
    
    return paramMap;
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenAentity(OrdenesDto orden) {
    
    return OrdenesEntity.builder()
        .idOrden(orden.getIdOrden())
        .clienteId(orden.getClienteId())
        .folio(orden.getFolio())
        .fechaIngreso(orden.getFechaIngreso())
        .estado(orden.getEstado())
        .totalPrendas(orden.getTotalPrendas())
        .observaciones(orden.getObservaciones())
        
        // Este campo por ahora es temporal desde el Front
        .tenantId(orden.getTenantId())
        .fechaEntrega(orden.getFechaEntrega())
        .build();
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenAentity(InsertarOrdenRequest orden) {
    
    return OrdenesEntity.builder()
        .idOrden(orden.getIdOrden())
        .clienteId(orden.getClienteId())
        .fechaIngreso(orden.getFechaIngreso())
        .estado(orden.getEstado())
        .totalPrendas(orden.getTotalPrendas())
        .observaciones(orden.getObservaciones())
        .tenantId(orden.getTenantId())
        .fechaEntrega(orden.getFechaEntrega())
        .build();
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK y Actualizar
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenAentity(ActualizarOrdenServicioRequest orden) {
    
    return OrdenesEntity.builder()
        .idOrden(orden.getIdOrden())
        .clienteId(orden.getClienteId())
        .folio(orden.getFolio())
        .fechaIngreso(orden.getFechaIngreso())
        .estado(orden.getEstado())
        .totalPrendas(orden.getTotalPrendas())
        .observaciones(orden.getObservaciones())
        .fechaEntrega(orden.getFechaEntrega())
        .build();
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK y Eliminar
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenAentity(EliminarOrdenServicioRequest orden) {
    
    return OrdenesEntity.builder()
        .idOrden(orden.getIdOrden())
        .folio(orden.getFolio())
        .build();
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenRequestAentity(BuscarOrdenRequest orden) {
    
    return OrdenesEntity.builder()
        .idOrden(orden.getIdOrden())
        .folio(orden.getFolio())
        .build();
  }
  
  /**
   * Mapeo de objetos para envio y recepcion al Front
   *
   * @param orden
   * @return
   */
  public OrdenesDto mapearOrdenAdto(OrdenesEntity orden) {
    
    return OrdenesDto.builder()
        .idOrden(orden.getIdOrden())
        .clienteId(orden.getClienteId())
        .folio(orden.getFolio())
        .fechaIngreso(orden.getFechaIngreso())
        .estado(orden.getEstado())
        .totalPrendas(orden.getTotalPrendas())
        .observaciones(orden.getObservaciones())
        .createdAt(orden.getCreatedAt())
        // Este campo por ahora es temporal hacia el Front
        .tenantId(orden.getTenantId())
        .fechaEntrega(orden.getFechaEntrega())
        .build();
  }
  
}

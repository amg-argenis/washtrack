package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.dto.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.OrdenesDto;
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
    
    return paramMap;
  }
  
  public Map<String, Object> parametrizarEliminarOrdenes(OrdenesEntity orden) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pa_idOrden", orden.getIdOrden());
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
    OrdenesEntity ordenEntity = new OrdenesEntity();
    
    ordenEntity.setIdOrden(orden.getIdOrden());
    ordenEntity.setClienteId(orden.getClienteId());
    ordenEntity.setFolio(orden.getFolio());
    ordenEntity.setFechaIngreso(orden.getFechaIngreso());
    ordenEntity.setEstado(orden.getEstado());
    ordenEntity.setTotalPrendas(orden.getTotalPrendas());
    ordenEntity.setObservaciones(orden.getObservaciones());
    
    // Este campo por ahora es temporal desde el Front
    ordenEntity.setTenantId(orden.getTenantId());
    
    return ordenEntity;
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenAentity(InsertarOrdenRequest orden) {
    OrdenesEntity ordenEntity = new OrdenesEntity();
    
    ordenEntity.setIdOrden(orden.getIdOrden());
    ordenEntity.setClienteId(orden.getClienteId());
    ordenEntity.setFechaIngreso(orden.getFechaIngreso());
    ordenEntity.setEstado(orden.getEstado());
    ordenEntity.setTotalPrendas(orden.getTotalPrendas());
    ordenEntity.setObservaciones(orden.getObservaciones());
    
    // Este campo por ahora es temporal desde el Front
    ordenEntity.setTenantId(orden.getTenantId());
    
    return ordenEntity;
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK
   *
   * @param orden
   * @return
   */
  public OrdenesEntity mapearOrdenRequestAentity(BuscarOrdenRequest orden) {
    OrdenesEntity ordenEntity = new OrdenesEntity();
    
    ordenEntity.setIdOrden(orden.getIdOrden());
    ordenEntity.setFolio(orden.getFolio());
    
    return ordenEntity;
  }
  
  /**
   * Mapeo de objetos para envio y recepcion al Front
   *
   * @param orden
   * @return
   */
  public OrdenesDto mapearOrdenAdto(OrdenesEntity orden) {
    OrdenesDto ordenDto = new OrdenesDto();
    
    ordenDto.setIdOrden(orden.getIdOrden());
    ordenDto.setClienteId(orden.getClienteId());
    ordenDto.setFolio(orden.getFolio());
    ordenDto.setFechaIngreso(orden.getFechaIngreso());
    ordenDto.setEstado(orden.getEstado());
    ordenDto.setTotalPrendas(orden.getTotalPrendas());
    ordenDto.setObservaciones(orden.getObservaciones());
    
    // Este campo por ahora es temporal hacia el Front
    ordenDto.setTenantId(orden.getTenantId());
    
    return ordenDto;
  }
  
}

package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

public interface IOrdenDetalleService {
  
  ServiceResult<Object> buscarOrdenDetalleService(OrdenDetalleDto detalleDto);
  
  ServiceResult<Object> guardarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto);
  
  interface IDetalleOrdenService {
    
    void agregarDetalleOrden();
    
  }
}

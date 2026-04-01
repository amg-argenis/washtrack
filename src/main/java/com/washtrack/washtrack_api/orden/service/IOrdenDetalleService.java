package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface IOrdenDetalleService {
  
  ServiceResult<Object> buscarOrdenDetalleService(OrdenDetalleDto detalleDto);
  
  ServiceResult<Object> guardarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto);
  
  ServiceResult<Object> actualizarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto);
  
  ServiceResult<Object> eliminarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto);
  
}

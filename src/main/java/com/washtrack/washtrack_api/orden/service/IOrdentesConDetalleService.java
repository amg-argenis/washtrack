package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.util.response.ServiceResult;

/**
 * Interfaz paraoperaciones de Ordenes servicio con sus detalles de orden
 */
public interface IOrdentesConDetalleService {
  
  ServiceResult<Object> obtenerOrdenServicioMasDetallesDto(BuscarOrdenRequest ordenRequest);
  
}

package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.EliminarBuscarDetalleOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.InsertDetalleOrden;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.ActualizarOrdenDetalleDto;
import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface IOrdenDetalleService {
  
  ServiceResult<Object> buscarOrdenDetalleService(EliminarBuscarDetalleOrdenRequest detalleDto);
  
  ServiceResult<Object> guardarOrdenDetalleService(InsertDetalleOrden insertDetalleOrden);
  
  ServiceResult<Object> actualizarOrdenDetalleService(ActualizarOrdenDetalleDto actualizarOrdenDetalleDto);
  
  ServiceResult<Object> eliminarOrdenDetalleService(EliminarBuscarDetalleOrdenRequest ordenDetalleDto);
  
}

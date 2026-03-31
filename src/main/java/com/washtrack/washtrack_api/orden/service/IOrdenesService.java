package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.orden.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.EliminarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

import java.time.LocalDate;

/**
 * Clase para gestionar la parte de negocio relacionada con las ordenes
 */

public interface IOrdenesService {
  
  ServiceResult<Object> listaOrdenesService(String tenantId);
  
  ServiceResult<Object> listaOrdenesFechaIngresoService(LocalDate fechaIngreso, String tenantId);
  
  ServiceResult<Object> buscarOrdenService(BuscarOrdenRequest ordenDto);
  
  ServiceResult<Object> guardarOrdenService(InsertarOrdenRequest ordenDto);
  
  ServiceResult<Object> actualizarOrdenService(ActualizarOrdenServicioRequest ordenDto);
  
  ServiceResult<Object> eliminarOrdenService(EliminarOrdenServicioRequest ordenDto);
  
}

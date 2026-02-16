package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.orden.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.EliminarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenesDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase para gestionar la parte de negocio relacionada con las ordenes
 */

public interface IOrdenesService {
  
  ServiceResult<List<OrdenesDto>> listaOrdenesService();
  
  ServiceResult<List<OrdenesDto>> listaOrdenesFechaIngresoService(LocalDate fechaIngreso);
  
  ServiceResult<OrdenesDto> buscarOrdenService(BuscarOrdenRequest ordenDto);
  
  ServiceResult<Integer> guardarOrdenService(InsertarOrdenRequest ordenDto);
  
  ServiceResult<Integer> actualizarOrdenService(ActualizarOrdenServicioRequest ordenDto);
  
  ServiceResult<Integer> eliminarOrdenService(EliminarOrdenServicioRequest ordenDto);
  
}

package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.OrdenesDto;
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
  
  ServiceResult<Integer> actualizarOrdenService(InsertarOrdenRequest ordenDto);
  
}

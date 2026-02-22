package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

import java.util.List;

public interface IOrdenDetalleService {
  
  public ServiceResult<OrdenDetalleDto> buscarOrdenDetalle(String idOrden);
  
  public ServiceResult<List<OrdenDetalleDto>> listarOrdenDetalle();
  
  public ServiceResult<Integer> guardarOrdenDetalle(OrdenDetalleDto ordenDetalleDto);
}

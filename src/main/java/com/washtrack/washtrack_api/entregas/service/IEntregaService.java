package com.washtrack.washtrack_api.entregas.service;

import com.washtrack.washtrack_api.entregas.dto.EntregaInsertRequest;
import com.washtrack.washtrack_api.entregas.dto.EntregasDto;
import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface IEntregaService {
  
  ServiceResult<Object> listarEntregasService(String tenantId);
  
  ServiceResult<Object> buscarEntregaService(String idEntrega, String tenantId);
  
  ServiceResult<Object> insertarEntregaService(EntregaInsertRequest entregasDto);
  
  ServiceResult<Object> actualizarEntregaService(EntregasDto entregasDto);
}

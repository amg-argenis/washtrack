package com.washtrack.washtrack_api.cliente.service;

import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

public interface IClientesService {
  
  ServiceResult<Object> listarClientesService(String tenantId);
  
  ServiceResult<Object> buscarClienteService(ClienteDto clienteDto);
  
  ServiceResult<Object> guardarClienteService(ClienteDto clienteDto);
  
  ServiceResult<Object> actualizarClienteService(ClienteDto clienteDto);
  
  ServiceResult<Object> eliminarClienteService(ClienteDto clienteDto);
  
}

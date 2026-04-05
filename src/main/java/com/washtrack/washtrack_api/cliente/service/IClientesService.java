package com.washtrack.washtrack_api.cliente.service;

import com.washtrack.washtrack_api.cliente.dto.ActualizarClienteDto;
import com.washtrack.washtrack_api.cliente.dto.InsertarClienteDto;
import com.washtrack.washtrack_api.cliente.dto.ClienteBuscarEliminarRequest;
import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface IClientesService {
  
  ServiceResult<Object> listarClientesService(String tenantId);
  
  ServiceResult<Object> buscarClienteService(ClienteBuscarEliminarRequest clienteBuscarEliminarRequest);
  
  ServiceResult<Object> insertarClienteService(InsertarClienteDto insertarClienteDto);
  
  ServiceResult<Object> actualizarClienteService(ActualizarClienteDto actualizarClienteDto);
  
  ServiceResult<Object> eliminarClienteService(ClienteBuscarEliminarRequest clienteDto);
  
}

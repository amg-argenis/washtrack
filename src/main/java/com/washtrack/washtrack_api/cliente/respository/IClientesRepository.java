package com.washtrack.washtrack_api.cliente.respository;

import com.washtrack.washtrack_api.cliente.dto.ClienteBuscarEliminarRequest;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.response.ResponseClientesRepository;

import java.util.List;

public interface IClientesRepository {
  
  ResponseClientesRepository listarClientesRepository(String tenantId);
  
  ResponseClientesRepository buscarClienteRepository(String idCliente, String tenantId);
  
  ResponseClientesRepository insertarClienteRepository(ClientesEntity cliente);
  
  ResponseClientesRepository actualizarClienteRepository(ClientesEntity cliente);
  
  Integer eliminarClienteRepository(ClienteBuscarEliminarRequest clienteDto);
  
}

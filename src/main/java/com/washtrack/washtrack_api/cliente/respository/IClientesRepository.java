package com.washtrack.washtrack_api.cliente.respository;

import com.washtrack.washtrack_api.cliente.dto.ClienteBuscarEliminarRequest;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;

import java.util.List;

public interface IClientesRepository {
  
  List<ClientesEntity> listarClientesRepository(String tenantId);
  
  ClientesEntity buscarClienteRepository(String idCliente, String tenantId);
  
  ClientesEntity insertarClienteRepository(ClientesEntity cliente);
  
  ClientesEntity actualizarClienteRepository(ClientesEntity cliente);
  
  Integer eliminarClienteRepository(ClienteBuscarEliminarRequest clienteDto);
  
}

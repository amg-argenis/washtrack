package com.washtrack.washtrack_api.cliente.respository;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;

import java.util.List;

public interface IClientesRepository {
  
  List<ClientesEntity> listarClientesRepository(String tenantId);
  
  ClientesEntity buscarClienteRepository(ClientesEntity cliente);
  
  ClientesEntity insertarClienteRepository(ClientesEntity cliente);
  
  Integer actualizarClienteRepository(ClientesEntity cliente);
  
  Integer eliminarClienteRepository(ClientesEntity cliente);
  
}

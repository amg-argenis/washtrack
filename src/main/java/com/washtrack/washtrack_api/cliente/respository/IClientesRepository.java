package com.washtrack.washtrack_api.cliente.respository;

import com.washtrack.washtrack_api.cliente.dto.EliminarClienteRequest;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;

import java.util.List;

public interface IClientesRepository {
  
  List<ClientesEntity> listarClientesRepository(String tenantId);
  
  ClientesEntity buscarClienteRepository(ClientesEntity cliente);
  
  ClientesEntity insertarClienteRepository(ClientesEntity cliente);
  
  ClientesEntity actualizarClienteRepository(ClientesEntity cliente);
  
  Integer eliminarClienteRepository(EliminarClienteRequest clienteDto);
  
}

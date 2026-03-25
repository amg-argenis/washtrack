package com.washtrack.washtrack_api.cliente.respository.impl;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.respository.IClientesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ClientesRepositoryImpl implements IClientesRepository {
  @Override
  public List<ClientesEntity> listarClientesRepository(String tenantId) {
    return List.of();
  }
  
  @Override
  public ClientesEntity buscarClienteRepository(ClientesEntity cliente) {
    return null;
  }
  
  @Override
  public ClientesEntity insertarClienteRepository(ClientesEntity cliente) {
    return null;
  }
  
  @Override
  public Integer actualizarClienteRepository(ClientesEntity cliente) {
    return 0;
  }
  
  @Override
  public Integer eliminarClienteRepository(ClientesEntity cliente) {
    return 0;
  }
}

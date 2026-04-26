package com.washtrack.washtrack_api.entregas.repository;

import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import com.washtrack.washtrack_api.entregas.response.EntregasResponseRepository;

import java.util.List;

public interface IEntregasRepository {
  
  List<EntregasEntity> listarEntregasRepository(String tenantId);
  
  EntregasResponseRepository buscarEntregaRepository(String idEntrega, String tenantId);
  
  EntregasResponseRepository eliminarEntregaRepository(String idEntrega, String tenantId);
  
  EntregasResponseRepository insertarEntregaRepository(EntregasEntity entregasEntity);
  
  EntregasResponseRepository actualizarEntregaRepository(EntregasEntity entregasEntity);
  
}

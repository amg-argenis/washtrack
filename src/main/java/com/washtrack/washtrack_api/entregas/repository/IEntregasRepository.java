package com.washtrack.washtrack_api.entregas.repository;

import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import com.washtrack.washtrack_api.entregas.response.EntregasResponseRepository;

public interface IEntregasRepository {
  
  EntregasResponseRepository listarEntregasRepository(String tenantId);
  
  EntregasResponseRepository buscarEntregaRepository(String idEntrega, String tenantId);
  
  EntregasResponseRepository eliminarEntregaRepository(String idEntrega, String tenantId);
  
  EntregasResponseRepository insertarEntregaRepository(EntregasEntity entregasEntity);
  
  EntregasResponseRepository actualizarEntregaRepository(EntregasEntity entregasEntity);
  
}

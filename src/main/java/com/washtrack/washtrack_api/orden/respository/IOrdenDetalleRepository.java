package com.washtrack.washtrack_api.orden.respository;

import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;

public interface IOrdenDetalleRepository {
  
  DetalleOrdenEntity buscarOrdenDetalleRepository(DetalleOrdenEntity ordenDetalle);
}

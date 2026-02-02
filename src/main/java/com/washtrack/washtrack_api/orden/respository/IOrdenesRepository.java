package com.washtrack.washtrack_api.orden.respository;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;

import java.util.List;

public interface IOrdenesRepository {
  
  List<OrdenesEntity> listarOrdenesRepository();
  
  OrdenesEntity buscarOrdenServicioRepository(OrdenesEntity orden);
  
}

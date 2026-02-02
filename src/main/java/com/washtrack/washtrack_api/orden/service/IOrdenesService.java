package com.washtrack.washtrack_api.orden.service;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

import java.util.List;

/**
 * Clase para gestionar la parte de negocio relacionada con las ordenes
 */

public interface IOrdenesService {
  
  ServiceResult<List<OrdenesEntity>> listaOrdenesService();
  
  ServiceResult<OrdenesEntity> buscarOrdenService(OrdenesEntity orden);
  
}

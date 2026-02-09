package com.washtrack.washtrack_api.orden.respository;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

import java.util.List;

public interface IOrdenesRepository {
  
  List<OrdenesEntity> listarOrdenesRepository();
  
  List<OrdenesEntity> listarOrdenesFechaIngresoRepository(String fechaIngreso);
  
  OrdenesEntity buscarOrdenServicioRepository(OrdenesEntity orden);
  
  ServiceResult<Integer> insertarOrdenRepository(OrdenesEntity orden);
  
}

package com.washtrack.washtrack_api.orden.respository;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;

import java.time.LocalDate;
import java.util.List;

public interface IOrdenesRepository {
  
  List<OrdenesEntity> listarOrdenesRepository();
  
  List<OrdenesEntity> listarOrdenesFechaIngresoRepository(LocalDate fechaIngreso);
  
  OrdenesEntity buscarOrdenServicioRepository(OrdenesEntity orden);
  
  OrdenesEntity insertarOrdenRepository(OrdenesEntity orden);
  
  Integer actualizarOrdenRepository(OrdenesEntity orden);
  
  Integer eliminarOrdenRepository(OrdenesEntity orden);
  
}

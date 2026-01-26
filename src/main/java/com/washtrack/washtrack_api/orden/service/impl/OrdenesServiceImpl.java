package com.washtrack.washtrack_api.orden.service.impl;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenesServiceImpl implements IOrdenesService {
  
  private final IOrdenesRepository ordenesRepository;
  
  public OrdenesServiceImpl(IOrdenesRepository ordenesRepository) {
    this.ordenesRepository = ordenesRepository;
  }
  
  @Override
  public ServiceResult<List<OrdenesEntity>> listaOrdenesService() {
    List<OrdenesEntity> resultadoRepository = this.ordenesRepository.listarOrdenesRepository();
    ServiceResult<List<OrdenesEntity>> result;
    
    if ( resultadoRepository.isEmpty() ) {
      result = new ServiceResult<>(false, "Sin registro de ordenes",
          List.of());
    }
    else {
      result = new ServiceResult<>(true, "Operacion exitosa",
          resultadoRepository);
    }
    return result;
  }
  
}

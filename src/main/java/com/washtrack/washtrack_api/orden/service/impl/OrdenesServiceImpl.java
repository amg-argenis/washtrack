package com.washtrack.washtrack_api.orden.service.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
    
    if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
      result = new ServiceResult<>(false, ConstantesOrdenes.SIN_REGISTROS, List.of());
    }
    else {
      result = new ServiceResult<>(true, ConstantesOrdenes.OPERACION_EXITOSA, resultadoRepository);
    }
    return result;
  }
  
}

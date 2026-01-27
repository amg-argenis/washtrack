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
  
  /**
   * Listar Ordenes servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<List<OrdenesEntity>> listaOrdenesService() {
    log.info("[Iniciando lista de ordenes <Service>]");
    List<OrdenesEntity> resultadoRepository = this.ordenesRepository.listarOrdenesRepository();
    ServiceResult<List<OrdenesEntity>> result;
    
    if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
      result = new ServiceResult<>(false, ConstantesOrdenes.SIN_REGISTROS, List.of());
    }
    else {
      result = new ServiceResult<>(true, ConstantesOrdenes.OPERACION_EXITOSA, resultadoRepository);
    }
    log.info("[Finaliza lista de ordenes <Service>]");
    return result;
  }
  
  /**
   * Buscar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<OrdenesEntity> buscarOrdeneService(String folioOrden) {
    log.info("[Iniciando buscarOrdene <Service>]");
    
    OrdenesEntity resultadoRepository = this.ordenesRepository.buscarOrdeneServicioRepository(folioOrden);
    ServiceResult<OrdenesEntity> result;
    
    if ( resultadoRepository == null ) {
      result = new ServiceResult<>(false, ConstantesOrdenes.SIN_REGISTROS, null);
    }
    else {
      result = new ServiceResult<>(true, ConstantesOrdenes.OPERACION_EXITOSA, resultadoRepository);
    }
    
    log.info("[Finaliza buscarOrdene <Service>]");
    return result;
  }
  
}

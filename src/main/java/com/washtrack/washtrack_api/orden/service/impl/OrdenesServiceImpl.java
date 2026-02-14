package com.washtrack.washtrack_api.orden.service.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.OrdenesDto;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import com.washtrack.washtrack_api.orden.util.MapearObjetos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrdenesServiceImpl implements IOrdenesService {
  
  private final IOrdenesRepository ordenesRepository;
  private final MapearObjetos mapearObjetos;
  
  public OrdenesServiceImpl(IOrdenesRepository ordenesRepository, MapearObjetos mapearObjetos) {
    this.ordenesRepository = ordenesRepository;
    this.mapearObjetos = mapearObjetos;
  }
  
  /**
   * Listar ordenes servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<List<OrdenesDto>> listaOrdenesService() {
    log.info("[Iniciando lista de ordenes <Service>]");
    List<OrdenesEntity> resultadoRepository = this.ordenesRepository.listarOrdenesRepository();
    ServiceResult<List<OrdenesDto>> result;
    
    if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
      result = new ServiceResult<>(false, ConstantesOrdenes.SIN_REGISTROS, ConstantesNumericas.CERO, List.of());
    }
    else {
      // Mapear a OrdenesEntity -> OrdenesDto
      List<OrdenesDto> ordenesDtoList = new ArrayList<>();
      for (OrdenesEntity ordenesEntity : resultadoRepository) {
        ordenesDtoList.add(this.mapearObjetos.mapearOrdenAdto(ordenesEntity));
      }
      result = new ServiceResult<>(true, ConstantesOrdenes.OPERACION_EXITOSA, ordenesDtoList.size(), ordenesDtoList);
    }
    log.info("[Finaliza lista de ordenes <Service>]");
    return result;
  }
  
  /**
   * Listar ordenes servicio por fecha ingreso | Service
   *
   * @return
   */
  @Override
  public ServiceResult<List<OrdenesDto>> listaOrdenesFechaIngresoService(LocalDate fechaIngreso) {
    log.info("[Iniciando lista de ordenes por fecha ingreso <Service>]");
    List<OrdenesEntity> resultadoRepository = this.ordenesRepository.listarOrdenesFechaIngresoRepository(fechaIngreso);
    ServiceResult<List<OrdenesDto>> result;
    
    if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
      result = new ServiceResult<>(false, ConstantesOrdenes.SIN_REGISTROS, ConstantesNumericas.CERO, List.of());
    }
    else {
      // Mapear a OrdenesEntity -> OrdenesDto
      List<OrdenesDto> ordenesDtoList = new ArrayList<>();
      for (OrdenesEntity ordenesEntity : resultadoRepository) {
        ordenesDtoList.add(this.mapearObjetos.mapearOrdenAdto(ordenesEntity));
      }
      result = new ServiceResult<>(true, ConstantesOrdenes.OPERACION_EXITOSA, ordenesDtoList.size(), ordenesDtoList);
    }
    log.info("[Finaliza lista de ordenes fecha ingreso <Service>]");
    return result;
  }
  
  /**
   * Buscar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<OrdenesDto> buscarOrdenService(BuscarOrdenRequest ordenRequest) {
    log.info("[Iniciando buscar orden <Service>]");
    
    // Mapear Request → Entity (solo criterios de búsqueda)
    OrdenesEntity criterioBusqueda = this.mapearObjetos.mapearOrdenRequestAentity(ordenRequest);
    
    OrdenesEntity resultado = ordenesRepository.buscarOrdenServicioRepository(criterioBusqueda);
    
    if ( resultado == null ) {
      log.info("[Orden no encontrada | Service]");
      return new ServiceResult<>(
          false,
          ConstantesOrdenes.SIN_REGISTROS,
          ConstantesNumericas.CERO,
          null
      );
    }
    
    // Mapear Entity → DTO (respuesta)
    OrdenesDto ordenDto = this.mapearObjetos.mapearOrdenAdto(resultado);
    
    log.info("[Finaliza buscar orden <Service>]");
    return new ServiceResult<>(
        true,
        ConstantesOrdenes.OPERACION_EXITOSA,
        ConstantesNumericas.UNO,
        ordenDto
    );
  }
  
  /**
   * Insertar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Integer> guardarOrdenService(InsertarOrdenRequest ordenDto) {
    log.info("[Inicia guardar nueva orden <Service>]");
    try {
      
      /**
       * Obtener el Tenant -- "a051a168-fa2a-11f0-aab7-e66133dbb0de" para pruebas
       * Obtener el UUID -- OK
       */
      
      UUID uuid = UUID.randomUUID();
      ordenDto.setIdOrden(uuid.toString());
      ordenDto.setTenantId("a051a168-fa2a-11f0-aab7-e66133dbb0de");
      
      // Mapear a OrdenesEntity
      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDto);
      
      ServiceResult<Integer> serviceResult = this.ordenesRepository.insertarOrdenRepository(ordenEntity);
      return serviceResult;
      
    }
    catch ( Exception e ) {
      log.error("[Error al guardar nueva orden de servicio, Exception | Service]: {}", e.getMessage(), e);
      return new ServiceResult<>(
          false,
          "Error inesperado en el servicio insertar nueva orden.",
          ConstantesNumericas.CERO,
          null
      );
    }
    finally {
      log.info("[Finaliza guardar nueva orden <Service>]");
    }
  }
  
  /**
   * Actualizar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Integer> actualizarOrdenService(ActualizarOrdenServicioRequest ordenDto) {
    log.info("[Inicia actualizar orden de servicio <Service>]");
    try {
      
      // Mapear a OrdenesEntity
      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDto);
      
      ServiceResult<Integer> serviceResult = this.ordenesRepository.actualizarOrdenRepository(ordenEntity);
      return serviceResult;
      
    }
    catch ( Exception e ) {
      log.error("[Error al actualizar la orden de servicio, Exception | Service]: {}", e.getMessage(), e);
      return new ServiceResult<>(
          false,
          "Error inesperado en el servicio actualizar orden de servicio.",
          ConstantesNumericas.CERO,
          null
      );
    }
    finally {
      log.info("[Finaliza actualizar orden de servicio <Service>]");
    }
  }
  
}

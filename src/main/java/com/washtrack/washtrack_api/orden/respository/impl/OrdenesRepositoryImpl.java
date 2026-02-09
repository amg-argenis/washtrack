package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import com.washtrack.washtrack_api.orden.respository.inicializador.InicializadorSimpleJdbcCall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class OrdenesRepositoryImpl implements IOrdenesRepository {
  
  private final InicializadorSimpleJdbcCall inicializador;
  
  public OrdenesRepositoryImpl(InicializadorSimpleJdbcCall inicializador) {
    this.inicializador = inicializador;
  }
  
  /**
   * Listar ordenes servicio | Repository
   *
   * @return
   */
  @Override
  public List<OrdenesEntity> listarOrdenesRepository() {
    log.info("[Iniciando listarOrdenesRepository | Repository]");
    
    List<OrdenesEntity> lista = new ArrayList<>();
    Map<String, Object> resultado;
    try {
      // Ejecucion
      resultado = this.inicializador.listarOrdenesCallJdbc();
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      log.info("[Respuesta BD: {} | {}]", pamensaje, codigobd);
      
      if ( codigobd == null ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
        codigobd = ConstantesNumericas.UNO;
      }
      else {
        lista = (List<OrdenesEntity>) resultado.get("listaOrdenes");
      }
      log.info("[Codigo BD <listarOrdenesRepository>]: {}", codigobd);
      
    }
    catch ( DataAccessException e ) {
      log.error("[Error al obtener listado de ordenes desde la BD <listarOrdenesRepository>]", e);
    }
    finally {
      log.info("[Finalizando listarOrdenesRepository | Repository]");
    }
    
    // ResultSet
    return lista;
  }
  
  /**
   * Listar ordenes servicio por fecha ingreso| Repository
   *
   * @return
   */
  @Override
  public List<OrdenesEntity> listarOrdenesFechaIngresoRepository(String fechaIngreso) {
    log.info("[Iniciando listar Ordenes Fecha Ingreso Repository fecha ingreso | Repository]");
    
    List<OrdenesEntity> lista = new ArrayList<>();
    Map<String, Object> resultado;
    try {
      // Ejecucion
      resultado = this.inicializador.listarOrdenesFechaIngresoCallJdbc(fechaIngreso);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      log.info("[Respuesta BD: {} | {}]", pamensaje, codigobd);
      
      if ( codigobd == null ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
        codigobd = ConstantesNumericas.UNO;
      }
      else {
        lista = (List<OrdenesEntity>) resultado.get("listaOrdenes");
      }
      log.info("[Codigo BD <listarOrdenesFechaIngresoRepository> fecha ingreso]: {}", codigobd);
      
    }
    catch ( DataAccessException e ) {
      log.error("[Error al obtener listado de ordenes fecha ingreso desde la BD <listarOrdenesFechaIngresoRepository>]", e);
    }
    finally {
      log.info("[Finalizando listar Ordenes Fecha Ingreso Repository fecha ingreso| Repository]");
    }
    
    // ResultSet
    return lista;
  }
  
  /**
   * Buscar orden servicio | Repository
   *
   * @return
   */
  @Override
  public OrdenesEntity buscarOrdenServicioRepository(OrdenesEntity orden) {
    
    log.info("[Iniciando buscarOrden | Repository]");
    
    try {
      Map<String, Object> resultado =
          inicializador.buscarOrdenCallJdbc(orden);
      
      Integer codigobd =
          (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje =
          (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Respuesta BD: {} | {}]", pamensaje, codigobd);
      
      if ( codigobd != null && codigobd == 0 ) {
        
        List<OrdenesEntity> lista =
            (List<OrdenesEntity>) resultado.get("ordenrecuperada");
        
        if ( lista != null && !lista.isEmpty() ) {
          return lista.get(ConstantesNumericas.CERO);
        }
      }
      
      return null;
      
    }
    catch ( DataAccessException e ) {
      log.error("[Error al buscar orden en BD]", e);
      return null;
    }
    finally {
      log.info("[Finalizando buscarOrden | Repository]");
    }
  }
  
  /**
   * Guardar una nueva orde de servicio | Repository
   *
   * @param orden
   * @return
   */
  @Override
  public ServiceResult<Integer> insertarOrdenRepository(OrdenesEntity orden) {
    
    log.info("[Inicia insertarOrden | Repository]");
    
    ServiceResult<Integer> serviceResult =
        new ServiceResult<>(false, ConstantesBaseDatos.ERROR_INSERT, ConstantesNumericas.CERO, null);
    
    try {
      // Ejecucion
      Map<String, Object> resultado = inicializador.insertarOrden(orden);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get("pa_mensaje");
      log.info("[Mensaje BD: {}]", pamensaje);
      
      if ( codigobd == null ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
        codigobd = 1;
      }
      log.info("[Codigo BD Insertar nueva orden de servicio | Repository]: {}", codigobd);
      
      if ( codigobd == ConstantesNumericas.CERO ) {
        serviceResult.setSuccess(true);
        serviceResult.setMessage(ConstantesOrdenes.OPERACION_EXITOSA);
        serviceResult.setData(codigobd);
      }
      else {
        serviceResult.setSuccess(false);
        serviceResult.setMessage(ConstantesBaseDatos.ERROR_INSERT);
        serviceResult.setData(codigobd);
      }
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al insertar orden en la BD | Repository]: {}", e.getMessage(), e);
      serviceResult.setMessage(ConstantesBaseDatos.ERROR_BD);
    }
    finally {
      log.info("[Finaliza insertar nueva orden de servicio | Repository]");
    }
    
    return serviceResult;
    
  }
  
}

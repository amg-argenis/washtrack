package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
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
    log.info("[Iniciando listarOrdenesRepository <Repository>]");
    
    List<OrdenesEntity> lista = new ArrayList<>();
    Map<String, Object> resultado = null;
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
      log.error("[Error al obtener listado de productos desde la BD <listarOrdenesRepository>]", e);
    }
    finally {
      log.info("[Finalizando listarOrdenesRepository <Repository>]");
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
    
    log.info("[Iniciando buscarOrden <Repository>]");
    
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
      log.info("[Finalizando buscarOrden <Repository>]");
    }
  }
  
}

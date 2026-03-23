package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import com.washtrack.washtrack_api.orden.respository.inicializador.InicializadorSimpleJdbcCall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
  public List<OrdenesEntity> listarOrdenesRepository(String tenantId) {
    log.info("[Inicia listar ordenes de servicio | Repository]");
    
    List<OrdenesEntity> lista = new ArrayList<>();
    Map<String, Object> resultado;
    try {
      // Ejecucion
      resultado = this.inicializador.listarOrdenesCallJdbc(tenantId);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
      }
      else {
        lista = (List<OrdenesEntity>) resultado.get("listaOrdenes");
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener listado de ordenes de servicio desde la BD "
              + "| Repository | Mas detalles: {}]", e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar orden de servicio en la BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza listar ordenes de servicio | Repository]");
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
  public List<OrdenesEntity> listarOrdenesFechaIngresoRepository(String tenantId, LocalDate fechaIngreso) {
    log.info("[Inicia listar ordenes de servicio por fecha ingreso | Repository]");
    
    List<OrdenesEntity> lista = new ArrayList<>();
    try {
      // Ejecucion
      Map<String, Object> resultado = this.inicializador.listarOrdenesFechaIngresoCallJdbc(tenantId, fechaIngreso);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP no devolvio pa_codigobd, se asume error]");
      }
      else {
        lista = (List<OrdenesEntity>) resultado.get("listaOrdenes");
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener listado de ordenes fecha ingreso desde la BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar orden de servicio por fecha ingreso en la BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza listar ordenes de servicio por fecha ingreso | Repository]");
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
    
    log.info("[Inicia buscar orden de servicio | Repository]");
    
    try {
      Map<String, Object> resultado = this.inicializador.buscarOrdenCallJdbc(orden);
      
      Integer codigobd =
          (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje =
          (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<OrdenesEntity> lista =
            (List<OrdenesEntity>) resultado.get("ordenrecuperada");
        
        if ( lista != null && !lista.isEmpty() ) {
          return lista.get(ConstantesNumericas.CERO);
        }
      }
      
      return null;
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar orden de servicio en BD]", e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar orden de servicio en la BD | Repository]: {}", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar orden de servicio | Repository]");
    }
  }
  
  /**
   * Guardar una nueva orde de servicio | Repository
   *
   * @param orden
   * @return
   */
  @Override
  public OrdenesEntity insertarOrdenRepository(OrdenesEntity orden) {
    
    log.info("[Inicia insertar orden servicio | Repository]");
    OrdenesEntity ordenesEntity = null;
    
    try {
      // Ejecucion
      Map<String, Object> resultado = this.inicializador.insertarOrden(orden);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        // Mapear OUT campos insertados a tu entidad
        ordenesEntity = new OrdenesEntity();
        ordenesEntity.setIdOrden((String) resultado.get("po_idorden"));
        ordenesEntity.setClienteId((String) resultado.get("po_clienteid"));
        ordenesEntity.setFolio((String) resultado.get("po_folio"));
        ordenesEntity.setFechaIngreso((String) resultado.get("po_fechaingreso"));
        ordenesEntity.setEstado((String) resultado.get("po_estado"));
        ordenesEntity.setTotalPrendas((Integer) resultado.get("po_totalprendas"));
        ordenesEntity.setObservaciones((String) resultado.get("po_observaciones"));
        ordenesEntity.setCreatedAt((String) resultado.get("po_createdat"));
        ordenesEntity.setTenantId((String) resultado.get("po_tenantid"));
        ordenesEntity.setFechaEntrega((String) resultado.get("po_fechaentrega"));
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al insertar orden de servicio en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar orden de servicio en la BD | Repository]: {}", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar orden servicio | Repository]");
    }
    
    return ordenesEntity;
  }
  
  /**
   * Actualizar una orde de servicio | Repository
   *
   * @param orden
   * @return
   */
  @Override
  public Integer actualizarOrdenRepository(OrdenesEntity orden) {
    
    log.info("[Inicia actualizar orden servicio | Repository]");
    
    Integer codigobd;
    
    try {
      // Ejecucion
      Map<String, Object> resultado = this.inicializador.actualizarOrden(orden);
      
      // OUT parameter seguro
      codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP no devolvio pa_codigobd, se asume error]");
        codigobd = 1;
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al actualizar la orden de servicio en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al actualizar la orden de servicio en la BD | Repository]: {}", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza actualizar orden servicio | Repository]");
    }
    
    return codigobd;
    
  }
  
  /**
   * Eliminar una orde de servicio | Repository
   *
   * @param orden
   * @return
   */
  @Override
  public Integer eliminarOrdenRepository(OrdenesEntity orden) {
    
    log.info("[Inicia eliminar orden servicio | Repository]");
    
    Integer codigobd;
    
    try {
      // Ejecucion
      Map<String, Object> resultado = this.inicializador.eliminarOrden(orden);
      
      codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al eliminar la orden de servicio en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al eliminar orden de servicio en la BD | Repository]: {}", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza eliminar orden servicio | Repository]");
    }
    
    return codigobd;
    
  }
  
}

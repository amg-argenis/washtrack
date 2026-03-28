package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.respository.IOrdenDetalleRepository;
import com.washtrack.washtrack_api.orden.respository.inicializador.InicializadorOrdenDetallaSimpJdbcCall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class OrdenDetalleRepositoryImpl implements IOrdenDetalleRepository {
  
  private final InicializadorOrdenDetallaSimpJdbcCall inicializadorOrdenDetallaSimpJdbcCall;
  
  public OrdenDetalleRepositoryImpl(InicializadorOrdenDetallaSimpJdbcCall inicializadorOrdenDetallaSimpJdbcCall) {
    this.inicializadorOrdenDetallaSimpJdbcCall = inicializadorOrdenDetallaSimpJdbcCall;
  }
  
  @Override
  public DetalleOrdenEntity buscarOrdenDetalleRepository(DetalleOrdenEntity ordenDetalle) {
    
    log.info("[Iniciando buscar detalle orden | Repository]");
    DetalleOrdenEntity detalleOrden = null;
    
    try {
      Map<String, Object> resultado = this.inicializadorOrdenDetallaSimpJdbcCall.buscarOrdenCallJdbc(ordenDetalle);
      
      Integer codigobd =
          (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje =
          (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Respuesta BD: {} | {}]", pamensaje, codigobd);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<DetalleOrdenEntity> lista =
            (List<DetalleOrdenEntity>) resultado.get("detalleordenrecuperada");
        
        if ( lista != null && !lista.isEmpty() ) {
          detalleOrden = lista.get(ConstantesNumericas.CERO);
        }
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar el detalle de orden en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar el detalle de orden en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza busqueda de detalle orden | Repository]");
    }
    return detalleOrden;
  }
  
  @Override
  public DetalleOrdenEntity insertarDetalleOrdenRepository(DetalleOrdenEntity ordenDetalle) {
    
    log.info("[Inicia insertar detalle orden servicio | Repository]");
    DetalleOrdenEntity detalleOrdenEntity = null;
    
    try {
      Map<String, Object> resultado = this.inicializadorOrdenDetallaSimpJdbcCall.insertarDetalleOrden(ordenDetalle);
      
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<DetalleOrdenEntity> lista =
            (List<DetalleOrdenEntity>) resultado.get("detalleordeninsertar");
        
        if ( lista != null && !lista.isEmpty() ) {
          detalleOrdenEntity = lista.get(ConstantesNumericas.CERO);
          log.info("[Detalle Orden Insertado | Detalle: {}]", detalleOrdenEntity);
        }
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un problema al insertar el detalle orden | Repository | Mas detalles: {}]",
            pamensaje);
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al insertar detalle orden | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar detalle orden | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar detalle orden servicio | Repository]");
    }
    
    return detalleOrdenEntity;
  }
  
  @Override
  public Integer actualizarDetalleOrdenRepository(DetalleOrdenEntity ordenDetalle) {
    
    log.info("[Inicia actualizar detalle orden servicio | Repository]");
    Integer codigobd;
    
    try {
      Map<String, Object> resultado = this.inicializadorOrdenDetallaSimpJdbcCall.actualizarDetalleOrden(ordenDetalle);
      
      codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        log.info("[Detalle orden actualizado correctamente | Detalle: {}]", pamensaje);
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.DOS ) {
        log.info("[Detalle orden No actualizado | Detalle: {}]", pamensaje);
      }
      
    }
    catch (
        DataAccessException e ) {
      log.error("[DataAccessException | Error al actualizar el detalle orden | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch (
        Exception e ) {
      log.error("[Exception | Error critico al actualizar el detalle orden | Repository]: {}", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza actualizar detalle orden servicio | Repository]");
    }
    
    return codigobd;
  }
  
  @Override
  public Integer eliminarDetalleOrdenRepository(DetalleOrdenEntity ordenDetalle) {
    
    log.info("[Inicia eliminar detalle orden servicio | Repository]");
    Integer codigobd;
    
    try {
      Map<String, Object> resultado = this.inicializadorOrdenDetallaSimpJdbcCall.eliminarDetalleOrden(ordenDetalle);
      
      codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        log.info("[Detalle orden eliminado correctamente | Detalle: {}]", pamensaje);
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.DOS ) {
        log.info("[Detalle orden No eliminada | Detalle: {}]", pamensaje);
      }
      
    }
    catch (
        DataAccessException e ) {
      log.error("[DataAccessException | Error al eliminar el detalle orden | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch (
        Exception e ) {
      log.error("[Exception | Error critico al eliminar el detalle orden | Repository]: {}", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza eliminar detalle orden servicio | Repository]");
    }
    
    return codigobd;
  }
  
}

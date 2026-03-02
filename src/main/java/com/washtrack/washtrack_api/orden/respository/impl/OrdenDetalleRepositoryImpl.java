package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
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
  public Integer insertarOrdenDetalleRepository(DetalleOrdenEntity ordenDetalle) {
    log.info("[Inicia insertar detalle orden | Repository]");
    Integer codigobd;
    try {
      // Ejecucion
      Map<String, Object> resultado = this.inicializadorOrdenDetallaSimpJdbcCall.insertarDetalleOrden(ordenDetalle);
      
      // OUT parameter seguro
      codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      log.info("[Mensaje BD: {}]", pamensaje);
      
      if ( codigobd == null ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
        codigobd = 1;
      }
      log.info("[Codigo BD Insertar nuevo detalle orden de servicio | Repository]: {}", codigobd);
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar el detalle de orden en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al insertar detalle orden en la BD | Repository]: {}", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar detalle orden | Repository]");
    }
    
    return codigobd;
  }
  
}

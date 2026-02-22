package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
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
    finally {
      log.info("[Finalizando busqueda de detalle orden | Repository]");
    }
    return detalleOrden;
  }
  
}

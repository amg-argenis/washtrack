package com.washtrack.washtrack_api.entregas.repository.impl;

import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import com.washtrack.washtrack_api.entregas.repository.IEntregasRepository;
import com.washtrack.washtrack_api.entregas.repository.inicializador.InicializadorEntregasSimpleJdbcCall;
import com.washtrack.washtrack_api.entregas.response.EntregasResponseRepository;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class EntregasRepositoryImpl implements IEntregasRepository {
  
  private final InicializadorEntregasSimpleJdbcCall inicializadorEntregasSimpleJdbcCall;
  
  public EntregasRepositoryImpl(InicializadorEntregasSimpleJdbcCall inicializadorEntregasSimpleJdbcCall) {
    this.inicializadorEntregasSimpleJdbcCall = inicializadorEntregasSimpleJdbcCall;
  }
  
  @Override
  public EntregasResponseRepository listarEntregasRepository(String tenantId) {
    return null;
  }
  
  @Override
  public EntregasResponseRepository buscarEntregaRepository(String idEntrega, String tenantId) {
    log.info("[Inicia buscar entrega | Repository]");
    
    EntregasResponseRepository responseRepository = new EntregasResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorEntregasSimpleJdbcCall.buscarEntregaJdbcMethod(idEntrega, tenantId);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setEntregasEntity(null);
      responseRepository.setCodigobd(codigobd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP buscar entrega fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<EntregasEntity> usuarioEntity = (List<EntregasEntity>) respuesta.get("entregarecuperada");
        responseRepository.setEntregasEntity(usuarioEntity.get(ConstantesNumericas.CERO));
        responseRepository.setCodigobd(codigobd);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Entrega no encontrada en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al buscar la entrega en BD | Repository"
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar la entrega en BD | Repository "
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar entrega | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public EntregasResponseRepository eliminarEntregaRepository(String idEntrega, String tenantId) {
    log.info("[Inicia eliminar entrega | Repository]");
    
    EntregasResponseRepository responseRepository = new EntregasResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorEntregasSimpleJdbcCall.eliminarEntregaJdbcMethod(idEntrega, tenantId);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setEntregasEntity(null);
      responseRepository.setCodigobd(codigobd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP eliminar entrega fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        log.info("[Entrega eliminada correctamente de la BD | Repository]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Entrega no encontrada en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al eliminar la entrega en BD | Repository"
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al eliminar la entrega en BD | Repository "
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza eliminar entrega | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public EntregasResponseRepository insertarEntregaRepository(EntregasEntity entregasEntity) {
    log.info("[Inicia insertar entrega | Repository]");
    
    EntregasResponseRepository responseRepository = new EntregasResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorEntregasSimpleJdbcCall.insertarEntregaJdbcMethod(entregasEntity);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setEntregasEntity(null);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<EntregasEntity> entregasEntities = (List<EntregasEntity>) respuesta.get("entregarecuperada");
        EntregasEntity entregaEntity = entregasEntities.get(ConstantesNumericas.CERO);
        responseRepository.setEntregasEntity(entregaEntity);
        responseRepository.setCodigobd(codigobd);
        log.info("[Entrega insertada correctamente en la BD]");
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para insertar entrega devolvio -1, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[La entrega a insertar ya existe en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al insertar entrega en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar entrega en BD | Repository | Detalles: {}]", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar entrega | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public EntregasResponseRepository actualizarEntregaRepository(EntregasEntity entregasEntity) {
    log.info("[Inicia actualizar entrega | Repository]");
    
    EntregasResponseRepository responseRepository = new EntregasResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorEntregasSimpleJdbcCall.actualizarEntregaJdbcMethod(entregasEntity);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setEntregasEntity(null);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<EntregasEntity> entregasEntities = (List<EntregasEntity>) respuesta.get("entregaactualizada");
        EntregasEntity entregaEntity = entregasEntities.get(ConstantesNumericas.CERO);
        responseRepository.setEntregasEntity(entregaEntity);
        responseRepository.setCodigobd(codigobd);
        log.info("[Entrega actualizada correctamente en la BD]");
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para actualizar entrega devolvio -1, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[La entrega a actualizar no existe en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al actualizar la entrega en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al actualizar la entrega en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza actualizar entrega | Repository]");
    }
    
    return responseRepository;
  }
  
}

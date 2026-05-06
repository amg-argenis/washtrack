package com.washtrack.washtrack_api.proceso.respository.impl;

import com.washtrack.washtrack_api.proceso.entity.ProcesosEntity;
import com.washtrack.washtrack_api.proceso.response.ProcesosResponseRepository;
import com.washtrack.washtrack_api.proceso.respository.IProcesosRepository;
import com.washtrack.washtrack_api.proceso.respository.inicializador.InicializadorProcesosLavado;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ProcesosRepositoryImpl implements IProcesosRepository {
  
  private final InicializadorProcesosLavado inicializadorProcesosLavado;
  
  public ProcesosRepositoryImpl(InicializadorProcesosLavado inicializadorProcesosLavado) {
    this.inicializadorProcesosLavado = inicializadorProcesosLavado;
  }
  
  @Override
  public ProcesosResponseRepository insertarProcesoRepository(ProcesosEntity proceso) {
    log.info("[Inicia insertar proceso de lavado | Repository]");
    
    ProcesosResponseRepository responseRepository = new ProcesosResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorProcesosLavado.insertarProcesoLavado(proceso);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setProcesosEntity(null);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<ProcesosEntity> procesosEntityList = (List<ProcesosEntity>) respuesta.get("procesoinsertado");
        ProcesosEntity procesosEntity = procesosEntityList.get(ConstantesNumericas.CERO);
        responseRepository.setProcesosEntity(procesosEntity);
        responseRepository.setCodigobd(codigobd);
        log.info("[Proceso de lavado insertado correctamente en la BD]");
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para insertar proceso de lavado devolvio -1, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[El proceso de lavado ya existe en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al insertar proceso de lavado en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar proceso de lavado en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar proceso de lavado | Repository]");
    }
    
    return responseRepository;
  }
}

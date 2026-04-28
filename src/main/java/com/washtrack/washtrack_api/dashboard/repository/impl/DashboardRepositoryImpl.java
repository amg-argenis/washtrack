package com.washtrack.washtrack_api.dashboard.repository.impl;

import com.washtrack.washtrack_api.dashboard.entity.DashboardEntity;
import com.washtrack.washtrack_api.dashboard.repository.IDashboardRepository;
import com.washtrack.washtrack_api.dashboard.repository.inicializador.InicializadorDashboard;
import com.washtrack.washtrack_api.dashboard.response.DashboardResponseRepository;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class DashboardRepositoryImpl implements IDashboardRepository {
  
  private final InicializadorDashboard inicializadorDashboard;
  
  public DashboardRepositoryImpl(InicializadorDashboard inicializadorDashboard) {
    this.inicializadorDashboard = inicializadorDashboard;
  }
  
  @Override
  public DashboardResponseRepository buscarDashboardRepository(String tenantId) {
    log.info("[Inicia obtener dashboard | Repository]");
    
    DashboardResponseRepository responseRepository = new DashboardResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorDashboard.obtenerDashboardMethod(tenantId);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setDashboardEntity(null);
      responseRepository.setCodigobd(codigobd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP buscar entrega fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<DashboardEntity> entityList = (List<DashboardEntity>) respuesta.get("dashboardrecuperado");
        responseRepository.setDashboardEntity(entityList.get(ConstantesNumericas.CERO));
        responseRepository.setCodigobd(codigobd);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Dashboard no recuperado correctamente de la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al obtener el dashboard de la BD | Repository"
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al obtener el dashboard de la BD | Repository "
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza obtener dashboard | Repository]");
    }
    
    return responseRepository;
  }
}

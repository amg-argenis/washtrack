package com.washtrack.washtrack_api.tenant.repository.impl;

import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import com.washtrack.washtrack_api.tenant.repository.ITenantRepository;
import com.washtrack.washtrack_api.tenant.repository.inicializador.InicializadorTenant;
import com.washtrack.washtrack_api.tenant.response.TenantResponseRepository;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class TenantRepositoryImpl implements ITenantRepository {
  
  private final InicializadorTenant inicializadorTenant;
  
  public TenantRepositoryImpl(InicializadorTenant inicializadorTenant) {
    this.inicializadorTenant = inicializadorTenant;
  }
  
  @Override
  public TenantResponseRepository insertarTenantRepository(TenantEntity tenant) {
    log.info("[Inicia insertar tenant | Repository]");
    
    TenantResponseRepository responseRepository = new TenantResponseRepository();
    
    try {
      Map<String, Object> respuesta = this.inicializadorTenant.insertarTenantExe(tenant);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setTenantEntity(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        responseRepository.setEntityList((List<TenantEntity>) respuesta.get("tenantinsertado"));
        if ( !responseRepository.getEntityList().isEmpty() ) {
          responseRepository.setTenantEntity(responseRepository.getEntityList().get(ConstantesNumericas.CERO));
          log.info("[Tenant insertado correctamente en la BD | Repository]");
        }
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para insertar tenant devolvio -1, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Ya existe un tenant con ese nombre en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al insertar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar tenant | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public TenantResponseRepository actualizarTenantRepository(TenantEntity tenant) {
    log.info("[Inicia actualizar tenant | Repository]");
    
    TenantResponseRepository responseRepository = new TenantResponseRepository();
    
    try {
      Map<String, Object> respuesta = this.inicializadorTenant.actualizarTenantExe(tenant);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setTenantEntity(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        responseRepository.setEntityList((List<TenantEntity>) respuesta.get("tenantactualizado"));
        if ( !responseRepository.getEntityList().isEmpty() ) {
          responseRepository.setTenantEntity(responseRepository.getEntityList().get(ConstantesNumericas.CERO));
          log.info("[Tenant actualizado correctamente en la BD | Repository]");
        }
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para actualizar tenant devolvio -1, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Tenant no encontrado en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al actualizar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al actualizar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza actualizar tenant | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public TenantResponseRepository eliminarTenantRepository(String idtenant) {
    log.info("[Inicia eliminar tenant | Repository]");
    
    TenantResponseRepository responseRepository = new TenantResponseRepository();
    
    try {
      Map<String, Object> respuesta = this.inicializadorTenant.eliminarTenantExe(idtenant);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setTenantEntity(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        log.info("[Tenant eliminado correctamente en la BD | Repository]");
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para eliminar tenant devolvio -1, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Tenant no encontrado en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al eliminar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al eliminar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza eliminar tenant | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public TenantResponseRepository buscarTenantRepository(String idtenant) {
    log.info("[Inicia buscar tenant | Repository]");
    
    TenantResponseRepository responseRepository = new TenantResponseRepository();
    
    try {
      Map<String, Object> respuesta = this.inicializadorTenant.buscarTenantExe(idtenant);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setTenantEntity(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        responseRepository.setEntityList((List<TenantEntity>) respuesta.get("tenantrecuperado"));
        if ( !responseRepository.getEntityList().isEmpty() ) {
          responseRepository.setTenantEntity(responseRepository.getEntityList().get(ConstantesNumericas.CERO));
          log.info("[Tenant recuperado correctamente de la BD | Repository]");
        }
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP buscar tenant fallo, se asume error | Repository]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Tenant no encontrado en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al buscar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar tenant en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar tenant | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public TenantResponseRepository listarTenantsRepository() {
    log.info("[Inicia listar tenants | Repository]");
    
    TenantResponseRepository responseRepository = new TenantResponseRepository();
    
    try {
      Map<String, Object> respuesta = this.inicializadorTenant.listarTenantsExe();
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setTenantEntity(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        responseRepository.setEntityList((List<TenantEntity>) respuesta.get("listatenants"));
        if ( !responseRepository.getEntityList().isEmpty() ) {
          log.info("[Listado de tenants recuperado correctamente de la BD | Repository]");
        }
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP listar tenants fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[No hay tenants registrados en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al listar tenants en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al listar tenants en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza listar tenants | Repository]");
    }
    
    return responseRepository;
  }
}
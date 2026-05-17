package com.washtrack.washtrack_api.tenant.service.impl;

import com.washtrack.washtrack_api.tenant.dto.TenantDto;
import com.washtrack.washtrack_api.tenant.dto.TenantInsertRequest;
import com.washtrack.washtrack_api.tenant.dto.TenantUpdateRequest;
import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import com.washtrack.washtrack_api.tenant.repository.ITenantRepository;
import com.washtrack.washtrack_api.tenant.response.TenantResponseRepository;
import com.washtrack.washtrack_api.tenant.service.ITenantService;
import com.washtrack.washtrack_api.tenant.util.MapearObjetosTenant;
import com.washtrack.washtrack_api.util.constantes.ConstantesMensajesGenericos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.MapearRespuestasConsultasUtil;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TenantServiceImpl implements ITenantService {
  
  private final ITenantRepository tenantRepository;
  private final MapearObjetosTenant mapearObjetosTenant;
  private final MapearRespuestasConsultasUtil mapearRespuestasConsultas;
  
  public TenantServiceImpl(ITenantRepository tenantRepository,
      MapearObjetosTenant mapearObjetosTenant,
      MapearRespuestasConsultasUtil mapearRespuestasConsultas) {
    this.tenantRepository = tenantRepository;
    this.mapearObjetosTenant = mapearObjetosTenant;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
  }
  
  @Override
  public ServiceResult<Object> insertarTenantService(TenantInsertRequest request) {
    log.info("[Inicia insertar tenant | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      TenantEntity tenantEntity = this.mapearObjetosTenant.mapearFromInsertRequestToEntity(request);
      tenantEntity.setIdtenant(UUID.randomUUID().toString());
      log.info("[Tenant a insertar: ({}) | Service]", tenantEntity);
      
      TenantResponseRepository resultado = this.tenantRepository.insertarTenantRepository(tenantEntity);
      
      if ( resultado.getTenantEntity() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        TenantDto tenantDto = this.mapearObjetosTenant.mapearFromEntityToDto(resultado.getTenantEntity());
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesMensajesGenericos.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, tenantDto
        );
      }
      
      if ( resultado.getTenantEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Tenant no insertado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_INSERT,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado.getTenantEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[Ya existe un tenant con ese nombre en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_INSERT,
            ApiErrorCode.CONFLICTO_INTEGRIDAD
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al insertar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_BASE_DATOS);
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    finally {
      log.info("[Finaliza insertar tenant | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> actualizarTenantService(TenantUpdateRequest request) {
    log.info("[Inicia actualizar tenant | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      TenantEntity tenantEntity = this.mapearObjetosTenant.mapearFromUpdateRequestToEntity(request);
      log.info("[Tenant a actualizar: ({}) | Service]", tenantEntity);
      
      TenantResponseRepository resultado = this.tenantRepository.actualizarTenantRepository(tenantEntity);
      
      if ( resultado.getTenantEntity() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        TenantDto tenantDto = this.mapearObjetosTenant.mapearFromEntityToDto(resultado.getTenantEntity());
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesMensajesGenericos.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, tenantDto
        );
      }
      
      if ( resultado.getTenantEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Tenant no actualizado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_ACTUALIZAR,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado.getTenantEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[Tenant no encontrado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_ACTUALIZAR,
            ApiErrorCode.RECURSO_NO_ENCONTRADO
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al actualizar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_BASE_DATOS);
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al actualizar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    finally {
      log.info("[Finaliza actualizar tenant | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> eliminarTenantService(String idtenant) {
    log.info("[Inicia eliminar tenant | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      log.info("[Tenant a eliminar: (IdTenant: {}) | Service]", idtenant);
      
      TenantResponseRepository resultado = this.tenantRepository.eliminarTenantRepository(idtenant);
      
      if ( resultado.getCodigobd() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesMensajesGenericos.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, null
        );
      }
      
      if ( resultado.getCodigobd() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Tenant no eliminado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_ELIMINAR,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado.getCodigobd() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[Tenant no encontrado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_ELIMINAR,
            ApiErrorCode.RECURSO_NO_ENCONTRADO
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al eliminar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_BASE_DATOS);
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al eliminar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    finally {
      log.info("[Finaliza eliminar tenant | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> buscarTenantService(String idtenant) {
    log.info("[Inicia buscar tenant | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      log.info("[Tenant a buscar: (IdTenant: {}) | Service]", idtenant);
      
      TenantResponseRepository respuesta = this.tenantRepository.buscarTenantRepository(idtenant);
      
      if ( respuesta == null ) {
        log.info("[Tenant no encontrado en la BD | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      if ( respuesta.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesMensajesGenericos.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, respuesta.getTenantEntity()
        );
      }
      
      if ( respuesta.getCodigobd() != null
          && respuesta.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Error en la BD al buscar el tenant | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_BD,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( respuesta.getCodigobd() != null
          && respuesta.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[Tenant no encontrado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.ERROR_BD,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_BASE_DATOS);
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar tenant | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    finally {
      log.info("[Finaliza buscar tenant | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> listarTenantsService() {
    log.info("[Inicia listar tenants | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      TenantResponseRepository respuesta = this.tenantRepository.listarTenantsRepository();
      
      if ( respuesta == null || respuesta.getEntityList().isEmpty() ) {
        log.info("[No hay tenants registrados en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      else {
        List<TenantDto> tenantDtoList = new ArrayList<>();
        for (TenantEntity tenantEntity : respuesta.getEntityList()) {
          tenantDtoList.add(this.mapearObjetosTenant.mapearFromEntityToDto(tenantEntity));
        }
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesMensajesGenericos.OPERACION_EXITOSA,
            respuesta.getEntityList().size(),
            tenantDtoList
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al listar tenants | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_BASE_DATOS);
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al listar tenants | Service | Detalles: {}]", e.getMessage(), e);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
          ConstantesMensajesGenericos.ERROR_BD, ApiErrorCode.ERROR_INTERNO);
    }
    finally {
      log.info("[Finaliza listar tenants | Service]");
    }
    
    return serviceResult;
  }
}
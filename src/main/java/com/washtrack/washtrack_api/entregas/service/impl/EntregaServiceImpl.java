package com.washtrack.washtrack_api.entregas.service.impl;

import com.washtrack.washtrack_api.entregas.dto.EntregaInsertRequest;
import com.washtrack.washtrack_api.entregas.dto.EntregasDto;
import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import com.washtrack.washtrack_api.entregas.repository.IEntregasRepository;
import com.washtrack.washtrack_api.entregas.response.EntregasResponseRepository;
import com.washtrack.washtrack_api.entregas.service.IEntregaService;
import com.washtrack.washtrack_api.entregas.util.MapearObjetosEntregas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioResponse;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class EntregaServiceImpl implements IEntregaService {
  
  private final IEntregasRepository entregasRepository;
  private final MapearRespuestasConsultas mapearRespuestasConsultas;
  private final MapearObjetosEntregas mapearObjetosEntregas;
  
  public EntregaServiceImpl(IEntregasRepository entregasRepository, MapearRespuestasConsultas mapearRespuestasConsultas,
      MapearObjetosEntregas mapearObjetosEntregas) {
    this.entregasRepository = entregasRepository;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
    this.mapearObjetosEntregas = mapearObjetosEntregas;
  }
  
  @Override
  public ServiceResult<Object> listarEntregasService(String tenantId) {
    return null;
  }
  
  @Override
  public ServiceResult<Object> buscarEntregaService(String idEntrega, String tenantId) {
    log.info("[Inicia buscar entrega | Service]");
    
    log.info("[Buscar entrega request: (Id entrega: {}| Tenant Id: {}) | Service]", idEntrega, tenantId);
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Llamada al Repository
      EntregasResponseRepository respuesta = this.entregasRepository.buscarEntregaRepository(idEntrega, tenantId);
      
      if ( respuesta == null ) {
        log.info("[Entrega no encontrada en la BD | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      if ( respuesta.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        // Mapear Entity → DTO (respuesta)
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, respuesta.getEntregasEntity()
        );
      }
      
      if ( respuesta.getCodigobd() != null && respuesta.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un error en la BD al buscar la entrega solicitada | Service]");
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_BD,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
      
      if ( respuesta.getCodigobd() != null && respuesta.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[No existe la entrega solicitada en la BD | Service]");
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_BD,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al buscar la entrega "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar la entrega | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza buscar la entrega | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> insertarEntregaService(EntregaInsertRequest entregasDto) {
    log.info("[Inicia insertar entrega | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Mapear DTO → Entity (request)
      EntregasEntity entregasEntity = this.mapearObjetosEntregas.entregasEntityFromDto(entregasDto);
      
      // UUID par idEntrega
      UUID uuid = UUID.randomUUID();
      entregasEntity.setIdEntrega(uuid.toString());
      log.info("[Entrega a insertar: ({}) | Service]", entregasEntity);
      
      // Llamada al Repository
      EntregasResponseRepository resultado =
          this.entregasRepository.insertarEntregaRepository(entregasEntity);
      
      // Mapear Entity → DTO (response)
      if ( resultado.getEntregasEntity() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        EntregasDto entrgaResponse =
            this.mapearObjetosEntregas.entregasDtoFromEntity(resultado.getEntregasEntity());
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, entrgaResponse
        );
      }
      
      if ( resultado.getEntregasEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Entrega no insertada en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_INSERT,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado.getEntregasEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[La entrega ya existe en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_INSERT,
            ApiErrorCode.CONFLICTO_INTEGRIDAD
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al insertar la entrega "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al insertar la entrega | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza insertar entrega | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> actualizarEntregaService(EntregasDto entregasDto) {
    return null;
  }
}

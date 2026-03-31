package com.washtrack.washtrack_api.orden.service.detalleordenimpl;

import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenDetalleRepository;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import com.washtrack.washtrack_api.orden.util.MapearObjetosDetalleOrden;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrdenDetalleServiceImpl implements IOrdenDetalleService {
  
  private final IOrdenDetalleRepository detalleRepository;
  private final MapearObjetosDetalleOrden mapearObjetosDetalleOrden;
  private final MapearRespuestasConsultas mapearRespuestasConsultas;
  
  public OrdenDetalleServiceImpl(IOrdenDetalleRepository detalleRepository,
      MapearObjetosDetalleOrden mapearObjetosDetalleOrden, MapearRespuestasConsultas mapearRespuestasConsultas) {
    this.detalleRepository = detalleRepository;
    this.mapearObjetosDetalleOrden = mapearObjetosDetalleOrden;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
  }
  
  @Override
  public ServiceResult<Object> buscarOrdenDetalleService(OrdenDetalleDto detalleDto) {
    log.info("[Iniciando buscar detalle orden <Service>]");
    
    ServiceResult<Object> serviceResult;
    
    // Mapear Request → Entity (solo criterios de busqueda)
    DetalleOrdenEntity detalleOrdenEntity = this.mapearObjetosDetalleOrden.mapearDtoToentityDetalleOrden(detalleDto);
    
    try {
      // Realizar busqueda → (Repository)
      DetalleOrdenEntity resultado = this.detalleRepository.buscarOrdenDetalleRepository(detalleOrdenEntity);
      
      if ( resultado == null ) {
        log.info("[Detalle orden no encontrada | Service]");
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.SIN_REGISTROS,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      else {
        // Mapear Entity → DTO (respuesta)
        OrdenDetalleDto ordenDetalleDtoResp = this.mapearObjetosDetalleOrden.mapearEntityToDtoOrdenDetalle(resultado);
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                ordenDetalleDtoResp
            );
      }
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar el detalle de orden en BD | Service | Mas detalles: {}]",
          e.getMessage(), e);
      
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar el detalle de orden | Service | Mas detalles: {}]",
          e.getMessage(), e);
      
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_SERVER,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza buscar detalle orden <Service>]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> guardarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto) {
    log.info("[Inicia guardar detalle orden <Service>]");
    ServiceResult<Object> serviceResult;
    try {
      
      /**
       * Obtener el Tenant -- "a051a168-fa2a-11f0-aab7-e66133dbb0de" para pruebas
       * Obtener el UUID -- OK
       */
      UUID uuid = UUID.randomUUID();
      ordenDetalleDto.setIdDetalleOrden(uuid.toString());
      log.info("[Insert detalle orden | UUID generado: '{}']", ordenDetalleDto.getIdDetalleOrden());
      
      // Mapear a OrdenesEntity
      DetalleOrdenEntity ordenEntity = this.mapearObjetosDetalleOrden.mapearDtoToentityDetalleOrden(ordenDetalleDto);
      
      DetalleOrdenEntity resp = this.detalleRepository.insertarDetalleOrdenRepository(ordenEntity);
      if ( resp != null ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                resp
            );
      }
      else {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_INSERT,
                null
            );
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al guardar el detalle de orden en BD | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Error critico al guardar nuevo detalle orden de servicio, Exception | Service]: {}", e.getMessage(),
          e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_SERVER,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza guardar detalle orden <Service>]");
    }
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> actualizarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto) {
    log.info("[Inicia actualizar detalle orden <Service>]");
    ServiceResult<Object> serviceResult;
    try {
      // Mapear a OrdenesEntity
      DetalleOrdenEntity ordenEntity = this.mapearObjetosDetalleOrden.mapearDtoToentityDetalleOrden(ordenDetalleDto);
      
      Integer respRepository = this.detalleRepository.actualizarDetalleOrdenRepository(ordenEntity);
      if ( respRepository != null && respRepository.intValue() == ConstantesNumericas.CERO ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                respRepository
            );
      }
      else if ( respRepository != null && respRepository.intValue() == ConstantesNumericas.DOS ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_BD,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      else {
        // Sea null o -1, se asume error en la ejecucion del SP
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ACTUALIZAR,
                null
            );
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al actualizar el detalle de orden en BD | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Error critico al actualizar detalle orden de servicio, Exception | Service]: {}", e.getMessage(),
          e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_SERVER,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza actualizar detalle orden <Service>]");
    }
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> eliminarOrdenDetalleService(OrdenDetalleDto ordenDetalleDto) {
    log.info("[Inicia eliminar detalle orden <Service>]");
    ServiceResult<Object> serviceResult;
    try {
      // Mapear a OrdenesEntity
      DetalleOrdenEntity ordenEntity = this.mapearObjetosDetalleOrden.mapearDtoToentityDetalleOrden(ordenDetalleDto);
      
      Integer respRepository = this.detalleRepository.eliminarDetalleOrdenRepository(ordenEntity);
      if ( respRepository != null && respRepository.intValue() == ConstantesNumericas.CERO ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                respRepository
            );
      }
      else if ( respRepository != null && respRepository.intValue() == ConstantesNumericas.DOS ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_BD,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      else {
        // Sea null o -1, se asume error en la ejecucion del SP
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ELIMINAR,
                null
            );
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al eliminar el detalle de orden en BD | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Error critico al eliminar detalle orden de servicio, Exception | Service]: {}", e.getMessage(),
          e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_SERVER,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza eliminar detalle orden <Service>]");
    }
    return serviceResult;
  }
  
}

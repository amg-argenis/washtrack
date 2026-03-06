package com.washtrack.washtrack_api.orden.service.ordenservicioimpl;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.orden.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.EliminarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenesDto;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import com.washtrack.washtrack_api.orden.util.MapearObjetos;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrdenesServiceImpl implements IOrdenesService {
  
  private final IOrdenesRepository ordenesRepository;
  private final MapearObjetos mapearObjetos;
  private final MapearRespuestasConsultas mapearRespuestasConsultas;
  
  public OrdenesServiceImpl(IOrdenesRepository ordenesRepository, MapearObjetos mapearObjetos,
      MapearRespuestasConsultas mapearRespuestasConsultas) {
    this.ordenesRepository = ordenesRepository;
    this.mapearObjetos = mapearObjetos;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
  }
  
  /**
   * Listar ordenes servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Object> listaOrdenesService() {
    log.info("[Inicia listar ordenes de servicio | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      
      List<OrdenesEntity> resultadoRepository = this.ordenesRepository.listarOrdenesRepository();
      
      if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.SIN_REGISTROS,
                List.of()
            );
      }
      else {
        // Mapear a OrdenesEntity -> OrdenesDto
        List<OrdenesDto> ordenesDtoList = new ArrayList<>();
        for (OrdenesEntity ordenesEntity : resultadoRepository) {
          ordenesDtoList.add(this.mapearObjetos.mapearOrdenAdto(ordenesEntity));
        }
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ordenesDtoList.size(),
                ordenesDtoList
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
          "[DataAccessException | Error al obtener listado de ordenes de servicio "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza listar ordenes de servicio | Service]");
    }
    
    return serviceResult;
  }
  
  /**
   * Listar ordenes servicio por fecha ingreso | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Object> listaOrdenesFechaIngresoService(LocalDate fechaIngreso) {
    log.info("[Inicia listar ordenes de servicio por fecha ingreso | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      List<OrdenesEntity> resultadoRepository =
          this.ordenesRepository.listarOrdenesFechaIngresoRepository(fechaIngreso);
      
      if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.SIN_REGISTROS,
                List.of()
            );
      }
      else {
        // Mapear a OrdenesEntity -> OrdenesDto
        List<OrdenesDto> ordenesDtoList = new ArrayList<>();
        for (OrdenesEntity ordenesEntity : resultadoRepository) {
          ordenesDtoList.add(this.mapearObjetos.mapearOrdenAdto(ordenesEntity));
        }
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ordenesDtoList.size(),
                ordenesDtoList
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
          "[DataAccessException | Error critico al obtener listado de ordenes de servicio por fecha ingreso "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al obtener listado de ordenes de servicio por fecha ingreso"
              + " | Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza listar ordenes de servicio por fecha ingreso | Service]");
    
    return serviceResult;
  }
  
  /**
   * Buscar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Object> buscarOrdenService(BuscarOrdenRequest ordenRequest) {
    log.info("[Inicia buscar orden de servicio | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Mapear Request → Entity (solo criterios de busqueda)
      OrdenesEntity criterioBusqueda = this.mapearObjetos.mapearOrdenRequestAentity(ordenRequest);
      
      OrdenesEntity resultado = ordenesRepository.buscarOrdenServicioRepository(criterioBusqueda);
      
      if ( resultado == null ) {
        log.info("[Orden no encontrada | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.RECURSO_NO_ENCONTRADO
        );
      }
      
      // Mapear Entity → DTO (respuesta)
      OrdenesDto ordenDto = this.mapearObjetos.mapearOrdenAdto(resultado);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, ordenDto
      );
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
          "[DataAccessException | Error al buscar ordene de servicio "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza buscar orden de servicio | Service]");
    return serviceResult;
  }
  
  /**
   * Insertar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Object> guardarOrdenService(InsertarOrdenRequest ordenDto) {
    log.info("[Inicia guardar nueva orden de servicio | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      /**
       * Obtener el Tenant -- "a051a168-fa2a-11f0-aab7-e66133dbb0de" para pruebas
       * Obtener el UUID -- OK
       */
      
      UUID uuid = UUID.randomUUID();
      ordenDto.setIdOrden(uuid.toString());
      ordenDto.setTenantId("a051a168-fa2a-11f0-aab7-e66133dbb0de");
      
      // Mapear a OrdenesEntity
      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDto);
      
      OrdenesEntity ordenesEntity = this.ordenesRepository.insertarOrdenRepository(ordenEntity);
      
      if ( ordenesEntity != null ) {
        OrdenesDto ordenRespDto = this.mapearObjetos.mapearOrdenAdto(ordenesEntity);
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                // Devolver al cliente el propio objeto que se envia a la BD, sin SELECT adicional en la BD
                ordenRespDto
            );
      }
      else {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_INSERT,
                ApiErrorCode.ERROR_BASE_DATOS
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
          "[DataAccessException | Error critico al insertar la orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al guardar la nueva orden de servicio | Service]: {}", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza guardar nueva orden de servicio | Service]");
    }
    return serviceResult;
  }
  
  /**
   * Actualizar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Object> actualizarOrdenService(ActualizarOrdenServicioRequest ordenDto) {
    log.info("[Inicia actualizar orden de servicio | Service]");
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Mapear a OrdenesEntity
      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDto);
      
      Integer resp = this.ordenesRepository.actualizarOrdenRepository(ordenEntity);
      
      if ( resp != null && resp == ConstantesNumericas.CERO ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                // Devolver al cliente el propio objeto que se envia a la BD, sin SELECT adicional en la BD
                ordenDto
            );
      }
      else if ( resp != null && resp == ConstantesNumericas.DOS ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ACTUALIZAR,
                ApiErrorCode.RECURSO_NO_ENCONTRADO
            );
      }
      else {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ACTUALIZAR,
                ApiErrorCode.ERROR_BASE_DATOS
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
          "[DataAccessException | Error critico al actualizar la orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al actualizar la nueva orden de servicio | Service]: {}", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza actualizar orden de servicio | Service]");
    }
    
    return serviceResult;
  }
  
  /**
   * Eliminar una orden servicio | Service
   *
   * @return
   */
  @Override
  public ServiceResult<Object> eliminarOrdenService(EliminarOrdenServicioRequest ordenDto) {
    log.info("[Inicia eliminar orden de servicio | Service]");
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Mapear a OrdenesEntity
      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDto);
      
      Integer resp = this.ordenesRepository.eliminarOrdenRepository(ordenEntity);
      if ( resp != null && resp == ConstantesNumericas.CERO ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                ApiErrorCode.OPERACION_EXITOSA
            );
      }
      else if ( resp != null && resp == ConstantesNumericas.DOS ) {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ELIMINAR,
                ApiErrorCode.RECURSO_NO_ENCONTRADO
            );
      }
      else {
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ELIMINAR,
                ApiErrorCode.ERROR_INTERNO
            );
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al actualizar la orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al actualizar la nueva orden de servicio | Service]: {}", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza eliminar orden de servicio | Service]");
    }
    
    return serviceResult;
  }
  
}

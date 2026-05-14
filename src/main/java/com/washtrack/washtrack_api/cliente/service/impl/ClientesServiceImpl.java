package com.washtrack.washtrack_api.cliente.service.impl;

import com.washtrack.washtrack_api.cliente.dto.ActualizarClienteDto;
import com.washtrack.washtrack_api.cliente.dto.ClienteBuscarEliminarRequest;
import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.response.ResponseClientesRepository;
import com.washtrack.washtrack_api.util.response.MapearRespuestasConsultasUtil;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.util.constantes.ConstantesMensajesGenericos;
import com.washtrack.washtrack_api.cliente.dto.InsertarClienteDto;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.respository.IClientesRepository;
import com.washtrack.washtrack_api.cliente.service.IClientesService;
import com.washtrack.washtrack_api.cliente.util.MapearObjetosCliente;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ClientesServiceImpl implements IClientesService {
  
  private final IClientesRepository clientesRepository;
  private final MapearRespuestasConsultasUtil mapearRespuestasConsultasUtilCliente;
  private final MapearObjetosCliente mapearObjetosCliente;
  
  public ClientesServiceImpl(IClientesRepository clientesRepository,
      MapearRespuestasConsultasUtil mapearRespuestasConsultasUtilCliente,
      MapearObjetosCliente mapearObjetosCliente) {
    this.clientesRepository = clientesRepository;
    this.mapearRespuestasConsultasUtilCliente = mapearRespuestasConsultasUtilCliente;
    this.mapearObjetosCliente = mapearObjetosCliente;
  }
  
  @Override
  public ServiceResult<Object> listarClientesService(String tenantId) {
    log.info("[Inicia listar ordenes de servicio | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      
      ResponseClientesRepository resultadoRepository =
          this.clientesRepository.listarClientesRepository(tenantId);
      
      if ( resultadoRepository != null && resultadoRepository.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
                ConstantesMensajesGenericos.SIN_REGISTROS,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      if ( resultadoRepository != null && resultadoRepository.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        // Mapear
        List<ClienteDto> ordenesDtoList = new ArrayList<>();
        for (ClientesEntity clientesEntity : resultadoRepository.getEntityList()) {
          ordenesDtoList.add(this.mapearObjetosCliente.mapearClienteToDto(clientesEntity));
        }
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultRespuestaOk(
                ConstantesMensajesGenericos.OPERACION_EXITOSA,
                ordenesDtoList.size(),
                ordenesDtoList
            );
      }
      if ( resultadoRepository != null
          && resultadoRepository.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
                ConstantesMensajesGenericos.ERROR_BD,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener listado de ordenes de servicio "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza listar ordenes de servicio | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> buscarClienteService(ClienteBuscarEliminarRequest clienteBuscarEliminarRequest) {
    log.info("[Inicia buscar cliente | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Llamada al Repository
      ResponseClientesRepository resultado =
          this.clientesRepository.buscarClienteRepository(clienteBuscarEliminarRequest.getIdCliente(),
              clienteBuscarEliminarRequest.getTenantId());
      
      if ( resultado != null && resultado.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[Cliente no encontrado | Service]");
        serviceResult = this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      if ( resultado != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        // Mapear Entity → DTO (respuesta)
        ClienteDto ordenDto = this.mapearObjetosCliente.mapearClienteToDto(resultado.getClientes());
        serviceResult = this.mapearRespuestasConsultasUtilCliente.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, ordenDto
        );
      }
      
      if ( resultado != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
                ConstantesMensajesGenericos.ERROR_BD,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al buscar el cliente "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar el cliente | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza buscar cliente | Service]");
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> insertarClienteService(InsertarClienteDto insertarClienteDto) {
    log.info("[Inicia insertar el nuevo cliente | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      UUID uuid = UUID.randomUUID();
      insertarClienteDto.setIdCliente(uuid.toString());
      
      // Mapear a Entity
      ClientesEntity clientesEntity = this.mapearObjetosCliente.mapearClienteDtoToEntity(insertarClienteDto);
      
      ResponseClientesRepository respRepository = this.clientesRepository.insertarClienteRepository(clientesEntity);
      
      if ( respRepository == null && respRepository.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
                ConstantesOrdenes.ERROR_INSERT,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
      
      if ( respRepository.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        ClienteDto ordenRespDto = this.mapearObjetosCliente.mapearClienteToDto(respRepository.getClientes());
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                // Devolver al cliente el propio objeto que se envia a la BD, sin SELECT adicional en la BD
                ordenRespDto
            );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al insertar el nuevo cliente | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al insertar el nuevo cliente | Service]: {}", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza insertar el nuevo cliente | Service]");
    }
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> actualizarClienteService(ActualizarClienteDto actualizarClienteDto) {
    log.info("[Inicia actualizar cliente | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Mapear a OrdenesEntity
      ClientesEntity clientesEntity = this.mapearObjetosCliente.mapearClienteDtoToEntity(actualizarClienteDto);
      
      ResponseClientesRepository respRepository = this.clientesRepository.actualizarClienteRepository(clientesEntity);
      
      if ( respRepository != null && respRepository.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                this.mapearObjetosCliente.mapearClienteToDto(respRepository.getClientes())
            );
      }
      if ( respRepository == null && respRepository.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ACTUALIZAR,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      if ( respRepository == null && respRepository.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        serviceResult =
            this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
                ConstantesOrdenes.ERROR_ACTUALIZAR,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al actualizar el cliente | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al actualizar el cliente | Service]: {}", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza actualizar el cliente | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> eliminarClienteService(ClienteBuscarEliminarRequest clienteDto) {
    log.info("[Inicia eliminar cliente | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Llamada al Repository
      Integer resultado = this.clientesRepository.eliminarClienteRepository(clienteDto);
      
      if ( resultado != null && resultado.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un problema en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
            ConstantesOrdenes.ERROR_ELIMINAR,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado != null && resultado.intValue() == ConstantesNumericas.CERO ) {
        log.info("[Cliente eliminado correctamente de la BD | Service]");
        serviceResult = this.mapearRespuestasConsultasUtilCliente.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.CERO,
            null
        );
      }
      
      if ( resultado != null && resultado.intValue() == ConstantesNumericas.DOS ) {
        log.info("[Cliente No encontrado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al eliminar el cliente "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al eliminar el cliente | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasUtilCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza eliminar cliente | Service]");
    return serviceResult;
  }
}

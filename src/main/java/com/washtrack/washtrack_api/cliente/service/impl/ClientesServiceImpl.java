package com.washtrack.washtrack_api.cliente.service.impl;

import com.washtrack.washtrack_api.cliente.util.MapearRespuestasConsultasCliente;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenesDto;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.util.constantes.ConstantesMensajesGenericos;
import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.respository.IClientesRepository;
import com.washtrack.washtrack_api.cliente.service.IClientesService;
import com.washtrack.washtrack_api.cliente.util.MapearObjetosCliente;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
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
  private final MapearRespuestasConsultasCliente mapearRespuestasConsultasClienteCliente;
  private final MapearObjetosCliente mapearObjetosCliente;
  
  public ClientesServiceImpl(IClientesRepository clientesRepository,
      MapearRespuestasConsultasCliente mapearRespuestasConsultasClienteCliente,
      MapearObjetosCliente mapearObjetosCliente) {
    this.clientesRepository = clientesRepository;
    this.mapearRespuestasConsultasClienteCliente = mapearRespuestasConsultasClienteCliente;
    this.mapearObjetosCliente = mapearObjetosCliente;
  }
  
  @Override
  public ServiceResult<Object> listarClientesService() {
    log.info("[Inicia listar ordenes de servicio | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      
      List<ClientesEntity> resultadoRepository =
          this.clientesRepository.listarClientesRepository("a051a168-fa2a-11f0-aab7-e66133dbb0de");
      
      if ( resultadoRepository == null || resultadoRepository.isEmpty() ) {
        serviceResult =
            this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
                ConstantesMensajesGenericos.SIN_REGISTROS,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      else {
        // Mapear
        List<ClienteDto> ordenesDtoList = new ArrayList<>();
        for (ClientesEntity clientesEntity : resultadoRepository) {
          ordenesDtoList.add(this.mapearObjetosCliente.mapearClienteToDto(clientesEntity));
        }
        serviceResult =
            this.mapearRespuestasConsultasClienteCliente.mapearserviceResultRespuestaOk(
                ConstantesMensajesGenericos.OPERACION_EXITOSA,
                ordenesDtoList.size(),
                ordenesDtoList
            );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener listado de ordenes de servicio "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
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
  public ServiceResult<Object> buscarClienteService(ClienteDto clienteDto) {
    log.info("[Inicia buscar cliente | Service]");
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Mapear Request → Entity (solo criterios de busqueda)
      ClientesEntity criterioBusqueda = this.mapearObjetosCliente.mapearClienteDtoToEntity(clienteDto);
      
      // Llamada al Repository
      ClientesEntity resultado = clientesRepository.buscarClienteRepository(criterioBusqueda);
      
      if ( resultado == null ) {
        log.info("[Cliente no encontrado | Service]");
        return this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      // Mapear Entity → DTO (respuesta)
      ClienteDto ordenDto = this.mapearObjetosCliente.mapearClienteToDto(resultado);
      serviceResult = this.mapearRespuestasConsultasClienteCliente.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, ordenDto
      );
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al buscar el cliente "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar el cliente | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza buscar cliente | Service]");
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> guardarClienteService(ClienteDto clienteDto) {
    log.info("[Inicia insertar el nuevo cliente | Service]");
    
    ServiceResult<Object> serviceResult;
    
    try {
      UUID uuid = UUID.randomUUID();
      clienteDto.setIdCliente(uuid.toString());
      
      // Mapear a Entity
      ClientesEntity ordenEntity = this.mapearObjetosCliente.mapearClienteDtoToEntity(clienteDto);
      
      ClientesEntity ordenesEntity = this.clientesRepository.insertarClienteRepository(ordenEntity);
      
      if ( ordenesEntity != null ) {
        ClienteDto ordenRespDto = this.mapearObjetosCliente.mapearClienteToDto(ordenesEntity);
        serviceResult =
            this.mapearRespuestasConsultasClienteCliente.mapearserviceResultRespuestaOk(
                ConstantesOrdenes.OPERACION_EXITOSA,
                ConstantesNumericas.UNO,
                // Devolver al cliente el propio objeto que se envia a la BD, sin SELECT adicional en la BD
                ordenRespDto
            );
      }
      else {
        serviceResult =
            this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
                ConstantesOrdenes.ERROR_INSERT,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al insertar el nuevo cliente | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al insertar el nuevo cliente | Service]: {}", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasClienteCliente.mapearserviceResultError(
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
  public ServiceResult<Object> actualizarClienteService(ClienteDto clienteDto) {
    return null;
  }
  
  @Override
  public ServiceResult<Object> eliminarClienteService(ClienteDto clienteDto) {
    return null;
  }
}

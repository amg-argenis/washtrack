package com.washtrack.washtrack_api.cliente.service.impl;

import com.washtrack.washtrack_api.util.constantes.ConstantesMensajesGenericos;
import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.respository.IClientesRepository;
import com.washtrack.washtrack_api.cliente.service.IClientesService;
import com.washtrack.washtrack_api.cliente.util.MapearObjetosCliente;
import com.washtrack.washtrack_api.cliente.util.MapearRespuestasConsultasCliente;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClientesServiceImpl implements IClientesService {
  
  private final IClientesRepository clientesRepository;
  private final MapearRespuestasConsultasCliente mapearRespuestasConsultasCliente;
  private final MapearObjetosCliente mapearObjetosCliente;
  
  public ClientesServiceImpl(IClientesRepository clientesRepository,
      MapearRespuestasConsultasCliente mapearRespuestasConsultasCliente, MapearObjetosCliente mapearObjetosCliente) {
    this.clientesRepository = clientesRepository;
    this.mapearRespuestasConsultasCliente = mapearRespuestasConsultasCliente;
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
            this.mapearRespuestasConsultasCliente.mapearserviceResultError(
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
            this.mapearRespuestasConsultasCliente.mapearserviceResultRespuestaOk(
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
          this.mapearRespuestasConsultasCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener listado de ordenes de servicio "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasCliente.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar orden de servicio | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultasCliente.mapearserviceResultError(
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
    return null;
  }
  
  @Override
  public ServiceResult<Object> guardarClienteService(ClienteDto clienteDto) {
    return null;
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

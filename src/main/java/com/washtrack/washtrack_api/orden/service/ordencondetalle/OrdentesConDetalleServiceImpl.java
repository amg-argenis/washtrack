package com.washtrack.washtrack_api.orden.service.ordencondetalle;

import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenServicioMasDetallesDto;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenConDetalleRepository;
import com.washtrack.washtrack_api.orden.service.IOrdentesConDetalleService;
import com.washtrack.washtrack_api.orden.util.MapearObjetos;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrdentesConDetalleServiceImpl implements IOrdentesConDetalleService {
  
  private final IOrdenConDetalleRepository iOrdenConDetalleRepository;
  private final MapearRespuestasConsultas mapearRespuestasConsultas;
  
  public OrdentesConDetalleServiceImpl(IOrdenConDetalleRepository iOrdenConDetalleRepository,
      MapearRespuestasConsultas mapearRespuestasConsultas) {
    this.iOrdenConDetalleRepository = iOrdenConDetalleRepository;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
  }
  
  @Override
  public ServiceResult<Object> obtenerOrdenServicioMasDetallesDto(BuscarOrdenRequest ordenRequest) {
    log.info("[Inicia buscar orden de servicio con detalle | Service]");
    
    log.info("[Request | Id orden: {} | Folio: {} | Tenant Id: {}]", ordenRequest.getIdOrden(), ordenRequest.getFolio(),
        ordenRequest.getTenantId());
    log.info("[TRIM check | idOrden length: {} | folio length: {} | tenantId length: {}]",
        ordenRequest.getIdOrden().length(),
        ordenRequest.getFolio().length(),
        ordenRequest.getTenantId().length());
    
    ServiceResult<Object> serviceResult;
    
    try {
      OrdenServicioMasDetallesEntity resultado =
          this.iOrdenConDetalleRepository.obtenerOrdenServicioMasDetallesRepository(ordenRequest);
      
      if ( resultado == null ) {
        log.info("[Orden con detalle no encontrada | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      OrdenServicioMasDetallesDto servicioMasDetallesDto =
          this.mapearRespuestasConsultas.mapearToOrdenServicioMasDetallesEntity(resultado);
      
      System.out.println("Orden servicio con detalles: [" + servicioMasDetallesDto + "]");
      
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, servicioMasDetallesDto
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
              + "| Repository | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar orden de servicio con detalle | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza buscar orden de servicio con detalle | Service]");
    return serviceResult;
  }
  
}

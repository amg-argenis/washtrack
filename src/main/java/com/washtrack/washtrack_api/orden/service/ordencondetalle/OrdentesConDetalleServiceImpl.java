package com.washtrack.washtrack_api.orden.service.ordencondetalle;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenServicioMasDetallesDto;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenesDto;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
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
  private final MapearObjetos mapearObjetos;
  
  public OrdentesConDetalleServiceImpl(IOrdenConDetalleRepository iOrdenConDetalleRepository,
      MapearRespuestasConsultas mapearRespuestasConsultas, MapearObjetos mapearObjetos) {
    this.iOrdenConDetalleRepository = iOrdenConDetalleRepository;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
    this.mapearObjetos = mapearObjetos;
  }
  
  @Override
  public ServiceResult<Object> obtenerOrdenServicioMasDetallesDto(BuscarOrdenRequest ordenRequest) {
    log.info("[Inicia buscar orden de servicio con detalle | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      OrdenServicioMasDetallesEntity resultado =
          this.iOrdenConDetalleRepository.obtenerOrdenServicioMasDetallesRepository(ordenRequest);
      
      if ( resultado == null ) {
        log.info("[Orden con detalle no encontrada | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.RECURSO_NO_ENCONTRADO
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

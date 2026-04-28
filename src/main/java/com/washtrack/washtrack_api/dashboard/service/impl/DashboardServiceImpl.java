package com.washtrack.washtrack_api.dashboard.service.impl;

import com.washtrack.washtrack_api.dashboard.repository.IDashboardRepository;
import com.washtrack.washtrack_api.dashboard.response.DashboardResponseRepository;
import com.washtrack.washtrack_api.dashboard.service.IDashboardService;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import com.washtrack.washtrack_api.util.constantes.ConstantesMensajesGenericos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DashboardServiceImpl implements IDashboardService {
  
  private final IDashboardRepository dashboardRepository;
  private final MapearRespuestasConsultas mapearRespuestasConsultas;
  
  public DashboardServiceImpl(IDashboardRepository dashboardRepository,
      MapearRespuestasConsultas mapearRespuestasConsultas) {
    this.dashboardRepository = dashboardRepository;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
  }
  
  @Override
  public ServiceResult<Object> obtenerDashboardService(String tenantId) {
    log.info("[Inicia obtener dashboard | Service]");
    
    log.info("[Obtener dashboard request: (Tenant Id: {}) | Service]", tenantId);
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Llamada al Repository
      DashboardResponseRepository respuesta = this.dashboardRepository.buscarDashboardRepository(tenantId);
      
      if ( respuesta == null ) {
        log.info("[Dashboard no obtenido de la BD | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesMensajesGenericos.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      if ( respuesta.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        // Mapear Entity → DTO (respuesta)
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesMensajesGenericos.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, respuesta.getDashboardEntity()
        );
      }
      
      if ( respuesta.getCodigobd() != null && respuesta.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un error en la BD al obtener el dashboard | Service]");
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesMensajesGenericos.ERROR_BD,
                ApiErrorCode.ERROR_BASE_DATOS
            );
      }
      
      if ( respuesta.getCodigobd() != null && respuesta.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[No existe informacion del dashboard en la BD | Service]");
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultError(
                ConstantesMensajesGenericos.ERROR_BD,
                ApiErrorCode.SIN_INFORMACION_EN_BD
            );
      }
      
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener el dashboard "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al obtener el dashboard | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesMensajesGenericos.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza obtener dashboard | Service]");
    }
    
    return serviceResult;
  }
}

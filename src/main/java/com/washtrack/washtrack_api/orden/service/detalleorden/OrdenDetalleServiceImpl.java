package com.washtrack.washtrack_api.orden.service.detalleorden;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.respository.IOrdenDetalleRepository;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import com.washtrack.washtrack_api.orden.util.MapearObjetosDetalleOrden;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public ServiceResult<OrdenDetalleDto> buscarOrdenDetalle(OrdenDetalleDto detalleDto) {
    log.info("[Iniciando buscar orden <Service>]");
    
    ServiceResult<OrdenDetalleDto> serviceResult;
    
    // Mapear Request → Entity (solo criterios de busqueda)
    DetalleOrdenEntity detalleOrdenEntity = this.mapearObjetosDetalleOrden.mapearDtoToentityDetalleOrden(detalleDto);
    
    try {
      // Realizar busqueda → (Repository)
      DetalleOrdenEntity resultado = this.detalleRepository.buscarOrdenDetalleRepository(detalleOrdenEntity);
      
      if ( resultado == null ) {
        log.info("[Orden detalle no encontrada | Service]");
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultBuscarOrdenDetalle(
                false,
                ConstantesOrdenes.SIN_REGISTROS,
                ConstantesNumericas.CERO,
                null
            );
      }
      else {
        // Mapear Entity → DTO (respuesta)
        OrdenDetalleDto ordenDetalleDtoResp = this.mapearObjetosDetalleOrden.mapearEntityTodtoOrdenDetalle(resultado);
        serviceResult =
            this.mapearRespuestasConsultas.mapearserviceResultBuscarOrdenDetalle(
                true,
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
          this.mapearRespuestasConsultas.mapearserviceResultBuscarOrdenDetalle(
              false,
              ConstantesOrdenes.ERROR_BD,
              ConstantesNumericas.CERO,
              null
          );
    }
    finally {
      log.info("[Finaliza buscar detalle orden <Service>]");
    }
    
    return serviceResult;
    
  }
  
  @Override
  public ServiceResult<List<OrdenDetalleDto>> listarOrdenDetalle() {
    return null;
  }
  
  @Override
  public ServiceResult<Integer> guardarOrdenDetalle(OrdenDetalleDto ordenDetalleDto) {
    log.info("[Inicia guardar nueva orden <Service>]");
    //    try {
    //
    //      /**
    //       * Obtener el Tenant -- "a051a168-fa2a-11f0-aab7-e66133dbb0de" para pruebas
    //       * Obtener el UUID -- OK
    //       */
    //
    //      UUID uuid = UUID.randomUUID();
    //      ordenDetalleDto.setIdDetalleOrden(uuid.toString());
    //      ordenDetalleDto.setTenantId("a051a168-fa2a-11f0-aab7-e66133dbb0de");
    //
    //      // Mapear a OrdenesEntity
    //      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDetalleDto);
    //
    //      ServiceResult<Integer> serviceResult = this.ordenesRepository.insertarOrdenRepository(ordenEntity);
    //      return serviceResult;
    //
    //    }
    //    catch ( Exception e ) {
    //      log.error("[Error al guardar nueva orden de servicio, Exception | Service]: {}", e.getMessage(), e);
    //      return new ServiceResult<>(
    //          false,
    //          "Error inesperado en el servicio insertar nueva orden.",
    //          ConstantesNumericas.CERO,
    //          null
    //      );
    //    }
    //    finally {
    //      log.info("[Finaliza guardar nueva orden <Service>]");
    //    }
    return null;
  }
}

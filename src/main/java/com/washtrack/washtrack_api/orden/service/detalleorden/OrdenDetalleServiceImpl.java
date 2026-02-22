package com.washtrack.washtrack_api.orden.service.detalleorden;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrdenDetalleServiceImpl implements IOrdenDetalleService {
  @Override
  public ServiceResult<OrdenDetalleDto> buscarOrdenDetalle(String idOrden) {
    return null;
  }
  
  @Override
  public ServiceResult<List<OrdenDetalleDto>> listarOrdenDetalle() {
    return null;
  }
  
  @Override
  public ServiceResult<Integer> guardarOrdenDetalle(OrdenDetalleDto ordenDetalleDto) {
    log.info("[Inicia guardar nueva orden <Service>]");
    try {
      
      /**
       * Obtener el Tenant -- "a051a168-fa2a-11f0-aab7-e66133dbb0de" para pruebas
       * Obtener el UUID -- OK
       */
      
      UUID uuid = UUID.randomUUID();
      ordenDetalleDto.setIdDetalleOrden(uuid.toString());
      ordenDetalleDto.setTenantId("a051a168-fa2a-11f0-aab7-e66133dbb0de");
      
      // Mapear a OrdenesEntity
      OrdenesEntity ordenEntity = this.mapearObjetos.mapearOrdenAentity(ordenDetalleDto);
      
      ServiceResult<Integer> serviceResult = this.ordenesRepository.insertarOrdenRepository(ordenEntity);
      return serviceResult;
      
    }
    catch ( Exception e ) {
      log.error("[Error al guardar nueva orden de servicio, Exception | Service]: {}", e.getMessage(), e);
      return new ServiceResult<>(
          false,
          "Error inesperado en el servicio insertar nueva orden.",
          ConstantesNumericas.CERO,
          null
      );
    }
    finally {
      log.info("[Finaliza guardar nueva orden <Service>]");
    }
  }
}

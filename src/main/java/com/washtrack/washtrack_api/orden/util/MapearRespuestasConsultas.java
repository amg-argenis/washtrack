package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenServicioMasDetallesDto;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.ActualizarOrdenDetalleDto;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MapearRespuestasConsultas {
  
  public <T> ServiceResult<T> mapearserviceResultRespuestaOk(String mensaje,
      int registros, T datos) {
    // Setear la respuesta por default
    ServiceResult<T> serviceResult = new ServiceResult<>(
        false,
        ConstantesOrdenes.RESPUESTA_GENERICA,
        ConstantesNumericas.CERO,
        null
    );
    
    serviceResult.setSuccess(true);
    serviceResult.setMessage(mensaje);
    serviceResult.setRegistros(registros);
    serviceResult.setData(datos);
    
    return serviceResult;
  }
  
  public <T> ServiceResult<T> mapearserviceResultError(String mensaje, T datos) {
    // Setear la respuesta por default
    ServiceResult<T> serviceResult = new ServiceResult<>(
        false,
        ConstantesOrdenes.RESPUESTA_GENERICA,
        ConstantesNumericas.CERO,
        null
    );
    
    serviceResult.setSuccess(false);
    serviceResult.setMessage(mensaje);
    serviceResult.setRegistros(ConstantesNumericas.CERO);
    serviceResult.setData(datos);
    
    return serviceResult;
  }
  
  public OrdenServicioMasDetallesDto mapearToOrdenServicioMasDetallesEntity(
      OrdenServicioMasDetallesEntity masDetalles) {
    
    List<ActualizarOrdenDetalleDto> detalles = masDetalles.getOrdenesDetalleDto().stream()
        .map(d -> ActualizarOrdenDetalleDto.builder()
            .idDetalleOrden(d.getIdDetalleOrden())
            .ordenId(d.getOrdenId())
            .procesoId(d.getProcesoId())
            .tipoPrenda(d.getTipoPrenda())
            .cantidad(d.getCantidad())
            .colorReferencia(d.getColorReferencia())
            .tenantId(d.getTenantId())
            .build())
        .toList();
    
    return OrdenServicioMasDetallesDto.builder()
        .idOrden(masDetalles.getIdOrden())
        .clienteId(masDetalles.getClienteId())
        .folio(masDetalles.getFolio())
        .fechaIngreso(masDetalles.getFechaIngreso())
        .estado(masDetalles.getEstado())
        .totalPrendas(masDetalles.getTotalPrendas())
        .observaciones(masDetalles.getObservaciones())
        .createdAt(masDetalles.getCreatedAt())
        .tenantId(masDetalles.getTenantId())
        .fechaEntrega(masDetalles.getFechaEntrega())
        .ordenesDetalleDto(detalles)
        .totalDetalle(detalles.size())
        .build();
  }
  
}

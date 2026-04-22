package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import com.washtrack.washtrack_api.orden.respository.IOrdenConDetalleRepository;
import com.washtrack.washtrack_api.orden.respository.inicializador.InicializadorOrdenConDetalleSjdbcCall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class OrdenConDetalleRepository implements IOrdenConDetalleRepository {
  
  private final InicializadorOrdenConDetalleSjdbcCall inicializadorOrdenConDetalleSjdbcCall;
  
  public OrdenConDetalleRepository(InicializadorOrdenConDetalleSjdbcCall inicializadorOrdenConDetalleSjdbcCall) {
    this.inicializadorOrdenConDetalleSjdbcCall = inicializadorOrdenConDetalleSjdbcCall;
  }
  
  @Override
  public OrdenServicioMasDetallesEntity obtenerOrdenServicioMasDetallesRepository(BuscarOrdenRequest ordenRequest) {
    log.info("[Inicia buscar orden de servicio con detalles | Repository]");
    
    OrdenServicioMasDetallesEntity build = OrdenServicioMasDetallesEntity.builder()
        .idOrden(ordenRequest.getIdOrden())
        .folio(ordenRequest.getFolio())
        .tenantId(ordenRequest.getTenantId())
        .build();
    
    OrdenServicioMasDetallesEntity entidadFinal = null;
    try {
      Map<String, Object> resultado =
          this.inicializadorOrdenConDetalleSjdbcCall.buscarOrdenConDetallesCallJdbc(build);
      
      // Add this log
      log.info("[Resultado completo del SP: {}]", resultado.keySet());
      
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<OrdenServicioMasDetallesEntity> lista =
            (List<OrdenServicioMasDetallesEntity>) resultado.get("detalleconorden");
        
        if ( lista != null && !lista.isEmpty() ) {
          
          // Tomar cabecera de la primera fila
          OrdenServicioMasDetallesEntity cabecera = lista.get(ConstantesNumericas.CERO);
          
          // Consolidar TODOS los detalles de las N filas
          List<DetalleOrdenEntity> todosLosDetalles = lista.stream()
              .flatMap(e -> e.getOrdenesDetalleDto().stream())
              .toList();
          
          // Construir entidad final con todos sus detalles
          entidadFinal = OrdenServicioMasDetallesEntity.builder()
              .idOrden(cabecera.getIdOrden())
              .clienteId(cabecera.getClienteId())
              .nombreCliente(cabecera.getNombreCliente())
              .folio(cabecera.getFolio())
              .fechaIngreso(cabecera.getFechaIngreso())
              .estado(cabecera.getEstado())
              .totalPrendas(cabecera.getTotalPrendas())
              .observaciones(cabecera.getObservaciones())
              .createdAt(cabecera.getCreatedAt())
              .tenantId(cabecera.getTenantId())
              .fechaEntrega(cabecera.getFechaEntrega())
              .ordenesDetalleDto(todosLosDetalles)
              .build();
        }
        
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar orden de servicio en BD]", e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar orden de servicio en la BD | Repository]: {}",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar orden de servicio con detalles | Repository]");
    }
    
    return entidadFinal;
  }
  
}

package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class OrdenDetalleController {
  
  private final IOrdenDetalleService ordenDetalleService;
  
  public OrdenDetalleController(IOrdenDetalleService ordenDetalleService) {
    this.ordenDetalleService = ordenDetalleService;
  }
  
  /**
   * Buscar orden detalle de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/detalles/buscar")
  public ResponseEntity<ServiceResult<Object>> ordenDetalle(
      @RequestBody OrdenDetalleDto ordenDetalleDto) {
    
    log.info("[Iniciando busqueda de detalle orden | Controller]");
    
    ServiceResult<Object> resultado =
        this.ordenDetalleService.buscarOrdenDetalleService(ordenDetalleDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    
    // Caso normal (200)
    return ResponseEntity.ok(resultado);
  }
  
  /**
   * Buscar orden detalle de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/detalles/guardar")
  public ResponseEntity<ServiceResult<Object>> guardarDetalleOrden(
      @RequestBody OrdenDetalleDto ordenDetalleDto) {
    
    log.info("[Iniciando insercion de detalle orden | Controller]");
    
    ServiceResult<Object> resultado =
        this.ordenDetalleService.guardarOrdenDetalleService(ordenDetalleDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    
    // Caso normal (200)
    return ResponseEntity.ok(resultado);
  }
  
  /**
   * Actualizar orden detalle de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/detalles/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarDetalleOrden(
      @RequestBody OrdenDetalleDto ordenDetalleDto) {
    
    log.info("[Iniciando actualizar detalle orden | Controller]");
    
    ServiceResult<Object> resultado =
        this.ordenDetalleService.actualizarOrdenDetalleService(ordenDetalleDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    
    // Caso normal (200)
    return ResponseEntity.ok(resultado);
  }
  
}

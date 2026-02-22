package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.orden.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
  
  // POST /ordenes/{ordenId}/detalles
  
  /**
   * Listar ordenes de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/detalles/buscar")
  public ResponseEntity<ServiceResult<Object>> ordenDetalle(
      @RequestBody OrdenDetalleDto ordenDetalleDto) {
    
    log.info("[Iniciando busqueda de orden detalle | Controller]");
    
    ServiceResult<Object> resultado =
        this.ordenDetalleService.buscarOrdenDetalle(ordenDetalleDto);
    
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

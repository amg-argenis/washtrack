package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.EliminarBuscarDetalleOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.InsertDetalleOrden;
import com.washtrack.washtrack_api.orden.dto.ordendetalle.ActualizarOrdenDetalleDto;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
  
  private String obtenerTenantId(HttpServletRequest request) {
    log.info("[Obteniendo tenantid | Controller]");
    return (String) request.getAttribute("tenantId");
  }
  
  @PostMapping("/ordenes/detalles/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarOrdenDetalle(
      @Valid @RequestBody EliminarBuscarDetalleOrdenRequest ordenDetalleDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de detalle orden | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      ordenDetalleDto.setTenantId(tenantId);
      
      resultado = this.ordenDetalleService.buscarOrdenDetalleService(ordenDetalleDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno al buscar el detalle orden servicio",
            ConstantesNumericas.CERO, null);
        response = ResponseEntity.status(ApiErrorCode.ERROR_INTERNO.getHttpStatus()).body(resultado);
      }
      else if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        response = ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      else {
        response = ResponseEntity.ok(resultado);
      }
      
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar el detalle orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/detalles/guardar")
  public ResponseEntity<ServiceResult<Object>> guardarDetalleOrden(
      @Valid @RequestBody InsertDetalleOrden insertDetalleOrden,
      HttpServletRequest request) {
    
    log.info("[Iniciando insercion de detalle orden | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      insertDetalleOrden.setTenantId(tenantId);
      
      resultado = this.ordenDetalleService.guardarOrdenDetalleService(insertDetalleOrden);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar el detalle orden servicio",
            ConstantesNumericas.CERO, null);
        response = ResponseEntity.status(ApiErrorCode.ERROR_INTERNO.getHttpStatus()).body(resultado);
      }
      else if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        response = ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      else {
        response = ResponseEntity.ok(resultado);
      }
      
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar el detalle orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/detalles/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarDetalleOrden(
      @Valid @RequestBody ActualizarOrdenDetalleDto actualizarOrdenDetalleDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizar detalle orden | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      actualizarOrdenDetalleDto.setTenantId(tenantId);
      
      resultado = this.ordenDetalleService.actualizarOrdenDetalleService(actualizarOrdenDetalleDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo actualizar el detalle orden servicio",
            ConstantesNumericas.CERO, null);
        response = ResponseEntity.status(ApiErrorCode.ERROR_INTERNO.getHttpStatus()).body(resultado);
      }
      else if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        response = ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      else {
        response = ResponseEntity.ok(resultado);
      }
      
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al actualizar el detalle orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/detalles/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarDetalleOrdenController(
      @Valid @RequestBody EliminarBuscarDetalleOrdenRequest ordenDetalle,
      HttpServletRequest request) {
    
    log.info("[Inicia eliminar detalle orden de servicio | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      ordenDetalle.setTenantId(tenantId);
      
      resultado = this.ordenDetalleService.eliminarOrdenDetalleService(ordenDetalle);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo eliminar el detalle orden servicio",
            ConstantesNumericas.CERO, null);
        response = ResponseEntity.status(ApiErrorCode.ERROR_INTERNO.getHttpStatus()).body(resultado);
      }
      else if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        response = ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      else {
        response = ResponseEntity.ok(resultado);
      }
      
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al eliminar el detalle orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
}

package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.ordendetalle.OrdenDetalleDto;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenDetalleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
  
  private String obtenerTenantId(HttpServletRequest request) {
    log.info("[Obteniendo tenantid | Controller]");
    return (String) request.getAttribute("tenantId");
  }
  
  @PostMapping("/ordenes/detalles/buscar")
  public ResponseEntity<ServiceResult<Object>> ordenDetalle(
      @RequestBody OrdenDetalleDto ordenDetalleDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de detalle orden | Controller]");
    
    if ( ordenDetalleDto.getIdDetalleOrden().length() > ConstantesNumericas.TREINTAYSEIS ) {
      ApiErrorCode errorCode = ApiErrorCode.DATOS_INVALIDOS;
      
      ServiceResult<Object> resultado =
          new ServiceResult<>(false, "Solicitud mal formada, limite de caracteres superado para el Id",
              ConstantesNumericas.CERO, null);
      return ResponseEntity.status(errorCode.getHttpStatus()).body(resultado);
    }
    
    String tenantId = obtenerTenantId(request);
    ordenDetalleDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenDetalleService.buscarOrdenDetalleService(ordenDetalleDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/detalles/guardar")
  public ResponseEntity<ServiceResult<Object>> guardarDetalleOrden(
      @RequestBody OrdenDetalleDto ordenDetalleDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando insercion de detalle orden | Controller]");
    
    String tenantId = obtenerTenantId(request);
    ordenDetalleDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenDetalleService.guardarOrdenDetalleService(ordenDetalleDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/detalles/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarDetalleOrden(
      @RequestBody OrdenDetalleDto ordenDetalleDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizar detalle orden | Controller]");
    
    if ( ordenDetalleDto.getIdDetalleOrden().length() > ConstantesNumericas.TREINTAYSEIS ) {
      ApiErrorCode errorCode = ApiErrorCode.DATOS_INVALIDOS;
      
      ServiceResult<Object> resultado =
          new ServiceResult<>(false, "Solicitud mal formada, limite de caracteres superado para el Id",
              ConstantesNumericas.CERO, null);
      return ResponseEntity.status(errorCode.getHttpStatus()).body(resultado);
    }
    
    String tenantId = obtenerTenantId(request);
    ordenDetalleDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenDetalleService.actualizarOrdenDetalleService(ordenDetalleDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/detalles/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarDetalleOrdenController(
      @Valid @RequestBody OrdenDetalleDto ordenDetalle,
      HttpServletRequest request) {
    
    log.info("[Inicia eliminar detalle orden de servicio | Controller]");
    
    if ( ordenDetalle.getIdDetalleOrden().length() > ConstantesNumericas.TREINTAYSEIS ) {
      ApiErrorCode errorCode = ApiErrorCode.DATOS_INVALIDOS;
      
      ServiceResult<Object> resultado =
          new ServiceResult<>(false, "Solicitud mal formada, limite de caracteres superado para el Id",
              ConstantesNumericas.CERO, null);
      return ResponseEntity.status(errorCode.getHttpStatus()).body(resultado);
    }
    
    String tenantId = obtenerTenantId(request);
    ordenDetalle.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenDetalleService.eliminarOrdenDetalleService(ordenDetalle);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
}

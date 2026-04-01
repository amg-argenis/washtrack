package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.orden.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.EliminarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.InsertarOrdenRequest;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import com.washtrack.washtrack_api.orden.service.IOrdentesConDetalleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class OrdenController {
  
  private final IOrdenesService ordenesService;
  private final IOrdentesConDetalleService ordentesConDetalleService;
  
  public OrdenController(IOrdenesService ordenesService, IOrdentesConDetalleService ordentesConDetalleService) {
    this.ordenesService = ordenesService;
    this.ordentesConDetalleService = ordentesConDetalleService;
  }
  
  private String obtenerTenantId(HttpServletRequest request) {
    log.info("[Obteniendo tenantid | Controller]");
    return (String) request.getAttribute("tenantId");
  }
  
  @GetMapping("/ordenes/listar")
  public ResponseEntity<ServiceResult<Object>> listarOrdenesController(HttpServletRequest request) {
    
    log.info("[Iniciando obtencion de ordenes servicio | Controller]");
    
    String tenantId = obtenerTenantId(request);
    log.info("[TenantId extraido del token: {}]", tenantId);
    
    ServiceResult<Object> resultado = this.ordenesService.listaOrdenesService(tenantId);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @GetMapping("/ordenes/fechaingreso")
  public ResponseEntity<ServiceResult<Object>> obtenerOrdenesPorFechaIngresoController(
      @RequestParam
      @NotNull(message = "La fecha de ingreso es obligatoria")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate fechaIngreso,
      HttpServletRequest request) {
    
    log.info("[Iniciando obtencion de ordenes servicio por fecha de ingreso | Controller]");
    log.info("[Request | Fecha: {}]", fechaIngreso);
    
    String tenantId = obtenerTenantId(request);
    
    ServiceResult<Object> resultado = this.ordenesService.listaOrdenesFechaIngresoService(fechaIngreso, tenantId);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarOrdenController(
      @Valid @RequestBody BuscarOrdenRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de la orden servicio | Controller]");
    log.info("[Request: Id orden: {} | Folio: {}]", orden.getIdOrden(), orden.getFolio());
    
    String tenantId = obtenerTenantId(request);
    orden.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenesService.buscarOrdenService(orden);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/orden-detalle")
  public ResponseEntity<ServiceResult<Object>> buscarOrdenServicioConDetallesController(
      @Valid @RequestBody BuscarOrdenRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de la orden servicio con ordenes detalle | Controller]");
    log.info("[Request | Id orden: {} | Folio: {}]", orden.getIdOrden(), orden.getFolio());
    
    String tenantId = obtenerTenantId(request);
    orden.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordentesConDetalleService.obtenerOrdenServicioMasDetallesDto(orden);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/crear")
  public ResponseEntity<ServiceResult<Object>> guardarOrdenController(
      @Valid @RequestBody InsertarOrdenRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando insercion de orden servicio | Controller]");
    log.info("[Request | Orden: {}]", orden);
    
    String tenantId = obtenerTenantId(request);
    orden.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenesService.guardarOrdenService(orden);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarOrdenController(
      @Valid @RequestBody ActualizarOrdenServicioRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizacion de orden servicio | Controller]");
    log.info("[Request | Orden: {}]", orden.getIdOrden());
    
    String tenantId = obtenerTenantId(request);
    orden.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenesService.actualizarOrdenService(orden);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/ordenes/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarOrdenController(
      @Valid @RequestBody EliminarOrdenServicioRequest orden,
      HttpServletRequest request) {
    
    log.info("[Inicia eliminar orden de servicio | Controller]");
    log.info("[Request | Orden: {}]", orden.getIdOrden());
    
    String tenantId = obtenerTenantId(request);
    orden.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.ordenesService.eliminarOrdenService(orden);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
}
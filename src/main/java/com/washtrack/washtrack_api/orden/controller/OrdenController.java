package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.orden.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.EliminarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.orden.InsertarOrdenRequest;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import com.washtrack.washtrack_api.orden.service.IOrdentesConDetalleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    log.info("[Recuperar tenantid | Controller]");
    return (String) request.getAttribute("tenantId");
  }
  
  @GetMapping("/ordenes/listar")
  public ResponseEntity<ServiceResult<Object>> listarOrdenesController(HttpServletRequest request) {
    
    log.info("[Iniciando obtencion de ordenes servicio | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      String tenantId = obtenerTenantId(request);
      
      resultado = this.ordenesService.listaOrdenesService(tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo listar ordenes",
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
      log.error("[Error critico al listar ordenes servicio | Controller | Detalles: {}]", e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
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
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      String tenantId = obtenerTenantId(request);
      
      resultado = this.ordenesService.listaOrdenesFechaIngresoService(fechaIngreso, tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo listar ordenes por fecha",
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
      log.error("[Error critico al listar ordenes servicio por fecha | Controller | Detalles: {}]", e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarOrdenController(
      @Valid @RequestBody BuscarOrdenRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de la orden servicio | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      if ( orden.getTenantId() == null || orden.getTenantId().isEmpty() ) {
        String tenantId = obtenerTenantId(request);
        orden.setTenantId(tenantId);
        log.info("[El tenant obtenido es: {}]", tenantId);
      }
      
      resultado = this.ordenesService.buscarOrdenService(orden);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo obtener la orden solicitada",
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
      log.error("[Error critico al obtener la orden solicitada | Controller | Detalles: {}]", e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/orden-detalle")
  public ResponseEntity<ServiceResult<Object>> buscarOrdenServicioConDetallesController(
      @Valid @RequestBody BuscarOrdenRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de la orden servicio con ordenes detalles | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      if ( orden.getTenantId() == null || orden.getTenantId().isEmpty() ) {
        String tenantId = obtenerTenantId(request);
        orden.setTenantId(tenantId);
        log.info("[El tenant obtenido es: {}]", tenantId);
      }
      
      resultado = this.ordentesConDetalleService.obtenerOrdenServicioMasDetallesDto(orden);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo obtener la orden servicio con ordenes detalles",
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
      log.error("[Error critico al obtener la orden servicio con ordenes detalles | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/crear")
  public ResponseEntity<ServiceResult<Object>> guardarOrdenController(
      @Valid @RequestBody InsertarOrdenRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando insercion de orden servicio | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      if ( orden.getTenantId() == null || orden.getTenantId().isEmpty() ) {
        String tenantId = obtenerTenantId(request);
        orden.setTenantId(tenantId);
        log.info("[El tenant obtenido es: {}]", tenantId);
      }
      
      resultado = this.ordenesService.guardarOrdenService(orden);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar la orden servicio",
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
      log.error("[Error critico al insertar la orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarOrdenController(
      @Valid @RequestBody ActualizarOrdenServicioRequest orden,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizacion de orden servicio | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      if ( orden.getTenantId() == null || orden.getTenantId().isEmpty() ) {
        String tenantId = obtenerTenantId(request);
        orden.setTenantId(tenantId);
        log.info("[El tenant obtenido es: {}]", tenantId);
      }
      
      resultado = this.ordenesService.actualizarOrdenService(orden);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo actualizar la orden servicio",
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
      log.error("[Error critico al actualizar la orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  @PostMapping("/ordenes/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarOrdenController(
      @Valid @RequestBody EliminarOrdenServicioRequest orden,
      HttpServletRequest request) {
    
    log.info("[Inicia eliminar orden de servicio | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      if ( orden.getTenantId() == null || orden.getTenantId().isEmpty() ) {
        String tenantId = obtenerTenantId(request);
        orden.setTenantId(tenantId);
        log.info("[El tenant obtenido es: {}]", tenantId);
      }
      
      resultado = this.ordenesService.eliminarOrdenService(orden);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo eliminar la orden servicio",
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
      log.error("[Error critico al eliminar la orden servicio | Controller | Detalles: {}]",
          e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
}
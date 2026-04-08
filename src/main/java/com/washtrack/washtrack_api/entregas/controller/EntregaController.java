package com.washtrack.washtrack_api.entregas.controller;

import com.washtrack.washtrack_api.entregas.dto.EntregaActualizarRequest;
import com.washtrack.washtrack_api.entregas.dto.EntregaInsertRequest;
import com.washtrack.washtrack_api.entregas.service.IEntregaService;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
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

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class EntregaController {
  
  private final IEntregaService entregaService;
  
  public EntregaController(IEntregaService entregaService) {
    this.entregaService = entregaService;
  }
  
  @GetMapping("/entregas/buscar/entregaRequest")
  public ResponseEntity<ServiceResult<Object>> buscarEntregaController(
      @RequestParam
      @NotNull(message = "Debe proporcionar el Id de la entrega")
      @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
      String entregaRequest,
      HttpServletRequest request) {
    
    log.info("[Iniciando buscar entrega | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      
      resultado = this.entregaService.buscarEntregaService(entregaRequest, tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo buscar la entrega solicitada",
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
      log.error("[Exception | Error critico al buscar entrega | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
    
  }
  
  @PostMapping("/entregas/eliminar/entregaRequest")
  public ResponseEntity<ServiceResult<Object>> eliminarEntregaController(
      @RequestParam
      @NotNull(message = "Debe proporcionar el Id de la entrega")
      @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id")
      String entregaRequest,
      HttpServletRequest request) {
    
    log.info("[Iniciando eliminar entrega | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      
      resultado = this.entregaService.eliminarEntregaService(entregaRequest, tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo eliminar la entrega solicitada",
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
      log.error("[Exception | Error critico al eliminar entrega | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
    
  }
  
  @PostMapping("/entregas/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarEntregaController(
      @Valid @RequestBody EntregaInsertRequest entregasDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando insertar entrega | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      String tenantId = obtenerTenantId(request);
      entregasDto.setTenantId(tenantId);
      
      resultado = this.entregaService.insertarEntregaService(entregasDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar la entrega",
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
      log.error("[Exception | Error critico al insertar entrega | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
    
  }
  
  @PostMapping("/entregas/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarEntregaController(
      @Valid @RequestBody EntregaActualizarRequest entregasDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando insertar entrega | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      String tenantId = obtenerTenantId(request);
      entregasDto.setTenantId(tenantId);
      
      resultado = this.entregaService.actualizarEntregaService(entregasDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar la entrega",
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
      log.error("[Exception | Error critico al insertar entrega | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
    
  }
  
  // Recuperar tenantId por token
  private String obtenerTenantId(HttpServletRequest request) {
    return (String) request.getAttribute("tenantId");
  }
  
}

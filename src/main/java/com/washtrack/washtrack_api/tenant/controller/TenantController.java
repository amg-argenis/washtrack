package com.washtrack.washtrack_api.tenant.controller;

import com.washtrack.washtrack_api.tenant.dto.TenantInsertRequest;
import com.washtrack.washtrack_api.tenant.dto.TenantUpdateRequest;
import com.washtrack.washtrack_api.tenant.service.ITenantService;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
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
public class TenantController {
  
  private final ITenantService tenantService;
  
  public TenantController(ITenantService tenantService) {
    this.tenantService = tenantService;
  }
  
  @PostMapping("/tenants/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarTenantController(
      @Valid @RequestBody TenantInsertRequest request) {
    
    log.info("[Iniciando insertar tenant | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      resultado = this.tenantService.insertarTenantService(request);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar el tenant",
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
      log.error("[Exception | Error critico al insertar tenant | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/tenants/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarTenantController(
      @Valid @RequestBody TenantUpdateRequest request) {
    
    log.info("[Iniciando actualizar tenant | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      resultado = this.tenantService.actualizarTenantService(request);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo actualizar el tenant",
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
      log.error("[Exception | Error critico al actualizar tenant | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/tenants/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarTenantController(
      @RequestParam
      @NotNull(message = "Debe proporcionar el Id del tenant")
      @Length(min = 36, max = 36, message = "El Id del tenant debe tener 36 caracteres")
      String tenantRequest) {
    
    log.info("[Iniciando eliminar tenant | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      resultado = this.tenantService.eliminarTenantService(tenantRequest);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo eliminar el tenant",
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
      log.error("[Exception | Error critico al eliminar tenant | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @GetMapping("/tenants/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarTenantController(
      @RequestParam
      @NotNull(message = "Debe proporcionar el Id del tenant")
      @Length(min = 36, max = 36, message = "El Id del tenant debe tener 36 caracteres")
      String tenantRequest) {
    
    log.info("[Iniciando buscar tenant | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      resultado = this.tenantService.buscarTenantService(tenantRequest);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo buscar el tenant",
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
      log.error("[Exception | Error critico al buscar tenant | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @GetMapping("/tenants/listar")
  public ResponseEntity<ServiceResult<Object>> listarTenantsController() {
    
    log.info("[Iniciando listar tenants | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      resultado = this.tenantService.listarTenantsService();
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo listar los tenants",
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
      log.error("[Exception | Error critico al listar tenants | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
}

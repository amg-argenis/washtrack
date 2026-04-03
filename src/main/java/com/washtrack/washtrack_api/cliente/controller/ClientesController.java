package com.washtrack.washtrack_api.cliente.controller;

import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.dto.EliminarClienteRequest;
import com.washtrack.washtrack_api.cliente.service.IClientesService;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class ClientesController {
  
  private final IClientesService clientesService;
  
  public ClientesController(IClientesService clientesService) {
    this.clientesService = clientesService;
  }
  
  private String obtenerTenantId(HttpServletRequest request) {
    log.info("[Obteniendo tenantid | Controller]");
    return (String) request.getAttribute("tenantId");
  }
  
  @GetMapping("/clientes/listar")
  public ResponseEntity<ServiceResult<Object>> listarClientesController(HttpServletRequest request) {
    
    log.info("[Iniciando listar clientes | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      String tenantId = obtenerTenantId(request);
      
      resultado = this.clientesService.listarClientesService(tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo listar los clientes",
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
      log.error("[Error critico al listar clientes | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
    
  }
  
  @PostMapping("/clientes/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarClientesController(
      @Valid @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de clientes | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      clienteDto.setTenantId(tenantId);
      
      resultado = this.clientesService.buscarClienteService(clienteDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo buscar el cliente solicitado",
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
      log.error("[Error critico al buscar el cliente | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/clientes/crear")
  public ResponseEntity<ServiceResult<Object>> insertarClientesController(
      @Valid @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando insercion de nuevo cliente | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      clienteDto.setTenantId(tenantId);
      
      resultado = this.clientesService.guardarClienteService(clienteDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar el cliente solicitado",
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
      log.error("[Error critico al insertar el cliente | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/clientes/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarClientesController(
      @Valid @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizar cliente | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      clienteDto.setTenantId(tenantId);
      
      resultado = this.clientesService.guardarClienteService(clienteDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo actualizar el cliente solicitado",
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
      log.error("[Error critico al actualizar el cliente | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/clientes/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarClientesController(
      @Valid @RequestBody EliminarClienteRequest clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando eliminar clientes | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      clienteDto.setTenantId(tenantId);
      
      resultado = this.clientesService.eliminarClienteService(clienteDto);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo eliminar el cliente solicitado",
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
      log.error("[Error critico al eliminar el cliente | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
}

package com.washtrack.washtrack_api.cliente.controller;

import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.service.IClientesService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
    
    log.info("[Iniciando obtencion de clientes | Controller]");
    
    String tenantId = obtenerTenantId(request);
    
    ServiceResult<Object> resultado = this.clientesService.listarClientesService(tenantId);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/clientes/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarClientesController(
      @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando busqueda de clientes | Controller]");
    
    String tenantId = obtenerTenantId(request);
    clienteDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.clientesService.buscarClienteService(clienteDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/clientes/crear")
  public ResponseEntity<ServiceResult<Object>> insertarClientesController(
      @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando insercion de clientes | Controller]");
    
    String tenantId = obtenerTenantId(request);
    clienteDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.clientesService.guardarClienteService(clienteDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/clientes/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarClientesController(
      @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizar cliente | Controller]");
    
    String tenantId = obtenerTenantId(request);
    clienteDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.clientesService.actualizarClienteService(clienteDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
  
  @PostMapping("/clientes/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarClientesController(
      @RequestBody ClienteDto clienteDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando eliminar clientes | Controller]");
    
    String tenantId = obtenerTenantId(request);
    clienteDto.setTenantId(tenantId);
    
    ServiceResult<Object> resultado = this.clientesService.eliminarClienteService(clienteDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      return ResponseEntity.status(error.getHttpStatus()).body(resultado);
    }
    return ResponseEntity.ok(resultado);
  }
}

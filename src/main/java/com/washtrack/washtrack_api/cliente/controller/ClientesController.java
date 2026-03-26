package com.washtrack.washtrack_api.cliente.controller;

import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.service.IClientesService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
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
  
  /**
   * Listar Clientes | Controller
   *
   * @return
   */
  @GetMapping("/clientes/listar")
  public ResponseEntity<ServiceResult<Object>> listarClientesController() {
    
    log.info("[Iniciando obtencion de clientes | Controller]");
    
    ServiceResult<Object> resultado = this.clientesService.listarClientesService();
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
  /**
   * Listar Clientes | Controller
   *
   * @return
   */
  @PostMapping("/clientes/buscar")
  public ResponseEntity<ServiceResult<Object>> buscarClientesController(
      @RequestBody ClienteDto clienteDto) {
    
    log.info("[Iniciando  de clientes | Controller]");
    
    ServiceResult<Object> resultado = this.clientesService.buscarClienteService(clienteDto);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
}

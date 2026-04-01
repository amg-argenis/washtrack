package com.washtrack.washtrack_api.entregas.controller;

import com.washtrack.washtrack_api.entregas.dto.EntregasDto;
import com.washtrack.washtrack_api.entregas.service.IEntregaService;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import jakarta.servlet.http.HttpServletRequest;
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
public class EntregaController {
  
  private final IEntregaService entregaService;
  
  public EntregaController(IEntregaService entregaService) {
    this.entregaService = entregaService;
  }
  
  @PostMapping("/entregas/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarEntregaController(
      @Validated @RequestBody EntregasDto entregasDto,
      HttpServletRequest request) {
    
    log.info("[Iniciando insertar entrega | Controller]");
    
    try {
      String tenantId = obtenerTenantId(request);
      entregasDto.setTenantId(tenantId);
      
      ServiceResult<Object> resultado = this.entregaService.insertarEntregaService(entregasDto);
      
      if ( resultado == null ) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        return ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      return ResponseEntity.ok(resultado);
    }
    catch ( Exception e ) {
      log.error("[Error critico al insertar entrega | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  // Recuperar tenantId por token
  private String obtenerTenantId(HttpServletRequest request) {
    return (String) request.getAttribute("tenantId");
  }
  
}

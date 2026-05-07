package com.washtrack.washtrack_api.proceso.controller;

import com.washtrack.washtrack_api.entregas.dto.EntregaInsertRequest;
import com.washtrack.washtrack_api.proceso.dto.ProcesosRequest;
import com.washtrack.washtrack_api.proceso.entity.ProcesosEntity;
import com.washtrack.washtrack_api.proceso.service.IProcesosService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class ProcesosController {
  private final IProcesosService procesosService;
  
  public ProcesosController(IProcesosService procesosService) {
    this.procesosService = procesosService;
  }
  
  // Recuperar tenantId por token
  private String obtenerTenantId(HttpServletRequest httpRequest) {
    return (String) httpRequest.getAttribute("tenantId");
  }
  
  @PostMapping("/procesos/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarProcesoController(
      @Valid @RequestBody ProcesosRequest procesosRequest,
      HttpServletRequest httpRequest) {
    
    log.info("[Iniciando insertar el proceso de lavado | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      
      String tenantId = obtenerTenantId(httpRequest);
      procesosRequest.setTenantid(tenantId);
      
      resultado = this.procesosService.insertarProcesoService(procesosRequest);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar el proceso de lavado",
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
      log.error("[Exception | Error critico al insertar el proceso de lavado | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
    
  }
  
}

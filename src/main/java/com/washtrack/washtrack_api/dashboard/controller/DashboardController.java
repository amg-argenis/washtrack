package com.washtrack.washtrack_api.dashboard.controller;

import com.washtrack.washtrack_api.dashboard.service.IDashboardService;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class DashboardController {
  
  private final IDashboardService dashboardService;
  
  public DashboardController(IDashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }
  
  @GetMapping("/busquedas/dashboard")
  public ResponseEntity<?> obtenerDashboard(HttpServletRequest httpRequest) {
    log.info("[Iniciando listar entregas | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(httpRequest);
      
      resultado = this.dashboardService.obtenerDashboardService(tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo obtener el dashboard",
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
      log.error("[Exception | Error critico al obtener el dashboard | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  // Recuperar tenantId por token
  private String obtenerTenantId(HttpServletRequest httpRequest) {
    return (String) httpRequest.getAttribute("tenantId");
  }
}

package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.orden.OrdenesDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class OrdenDetalleController {
  
  // POST /ordenes/{ordenId}/detalles
  /**
   * Listar ordenes de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/{ordenId}/detalles")
  public ResponseEntity<ServiceResult<List<OrdenesDto>>> ordenDetalle() {
    
    log.info("[Iniciando obtencion de ordenes servicio | Controller]");
    return ResponseEntity.ok(this.ordenesService.listaOrdenesService());
    
  }
}

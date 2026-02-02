package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class OrdenesController {
  
  private final IOrdenesService ordenesService;
  
  public OrdenesController(IOrdenesService ordenesService) {
    this.ordenesService = ordenesService;
  }
  
  /**
   * Listar ordenes
   *
   * @return
   */
  @GetMapping("/ordenes")
  public ServiceResult<List<OrdenesEntity>> obtenerOrdenes() {
    
    log.info("[Iniciando obtencion de ordenes | Controller]");
    return this.ordenesService.listaOrdenesService();
    
  }
  
  /**
   * Listar ordenes
   *
   * @return
   */
  @GetMapping("/ordenes/buscar")
  public ServiceResult<OrdenesEntity> buscarOrden(@RequestBody OrdenesEntity orden) {
    
    log.info("[Iniciando busqueda de la orden | Controller]");
    return this.ordenesService.buscarOrdenService(orden);
    
  }
  
}

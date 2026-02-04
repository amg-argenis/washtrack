package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.OrdenesDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
   * Listar ordenes de servicio | Controller
   *
   * @return
   */
  @GetMapping("/ordenes")
  public ServiceResult<List<OrdenesDto>> obtenerOrdenes() {
    
    log.info("[Iniciando obtencion de ordenes | Controller]");
    return this.ordenesService.listaOrdenesService();
    
  }
  
  /**
   * Buscar una orden de servicio | Controller
   *
   * @return
   */
  @GetMapping("/ordenes/buscar")
  public ServiceResult<OrdenesDto> buscarOrden(@RequestBody BuscarOrdenRequest orden) {
    
    log.info("[Iniciando busqueda de la orden | Controller]");
    return this.ordenesService.buscarOrdenService(orden);
    
  }
  
  /**
   * Insertar orden de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/crear")
  public ServiceResult<Integer> guardarOrden(@RequestBody InsertarOrdenRequest orden) {
    
    log.info("[Iniciando insercion de orden | Controller]");
    return this.ordenesService.guardarOrdenService(orden);
    
  }
  
}

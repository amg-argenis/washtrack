package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.request.ActualizarOrdenServicioRequest;
import com.washtrack.washtrack_api.orden.dto.request.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.request.InsertarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.OrdenesDto;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdenesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
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
  @GetMapping("/ordenes/listar")
  public ServiceResult<List<OrdenesDto>> obtenerOrdenes() {
    
    log.info("[Iniciando obtencion de ordenes servicio | Controller]");
    return this.ordenesService.listaOrdenesService();
    
  }
  
  /**
   * Listar ordenes de servicio por fecha de ingreso | Controller
   *
   * @return
   */
  @GetMapping("/ordenes/fechaingreso")
  public ServiceResult<List<OrdenesDto>> obtenerOrdenesPorFehcaIngreso(
      @RequestParam
      @NotNull(message = "La fecha de ingreso es obligatoria")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate fechaIngreso) {
    
    log.info("[Iniciando obtencion de ordenes servicio por fecha de ingreso | Controller]");
    return this.ordenesService.listaOrdenesFechaIngresoService(fechaIngreso);
  }
  
  /**
   * Buscar una orden de servicio | Controller
   *
   * @return
   */
  @GetMapping("/ordenes/buscar")
  public ServiceResult<OrdenesDto> buscarOrden(@Valid @RequestBody BuscarOrdenRequest orden) {
    
    log.info("[Iniciando busqueda de la orden servicio | Controller]");
    return this.ordenesService.buscarOrdenService(orden);
    
  }
  
  /**
   * Insertar orden de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/crear")
  public ServiceResult<Integer> guardarOrden(@Valid @RequestBody InsertarOrdenRequest orden) {
    
    log.info("[Iniciando insercion de orden servicio | Controller]");
    return this.ordenesService.guardarOrdenService(orden);
    
  }
  
  /**
   * Actualizar orden de servicio | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/actualizar")
  public ServiceResult<Integer> actualizarOrden(@Valid @RequestBody ActualizarOrdenServicioRequest orden) {
    
    log.info("[Iniciando actualizacion de orden servicio | Controller]");
    return this.ordenesService.actualizarOrdenService(orden);
    
  }
  
  /**
   * Eliminar orden de servicio (actualiza estado a ELIMINADO) | Controller
   *
   * @return
   */
  @PostMapping("/ordenes/eliminar")
  public ServiceResult<Integer> eliminarOrden(@Valid @RequestBody ActualizarOrdenServicioRequest orden) {
    
    log.info("[Iniciando actualizacion de orden servicio | Controller]");
    return this.ordenesService.actualizarOrdenService(orden);
    
  }
  
}

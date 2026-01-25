package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.orden.dto.ListaOrdenes;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping
@RestController
public class OrdenesController {
  
  @PostMapping("/ordenes")
  public ServiceResult<List<ListaOrdenes>> obtenerOrdenes() {
    
    log.info("[Iniciando obtencion de ordenes | Controller]");
    return new ServiceResult<>(true, "Lista de ordenes obtenida correctamente.", null);
  }
  
}

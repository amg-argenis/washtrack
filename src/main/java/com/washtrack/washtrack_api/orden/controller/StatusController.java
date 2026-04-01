package com.washtrack.washtrack_api.orden.controller;

import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class StatusController {
  
  @GetMapping("/status")
  public ServiceResult<String> estatusServicio() {
    
    log.info("[Solicitud de estatus | StatusController | UP! ]");
    return new ServiceResult<>(true, "Servicio disponible.", ConstantesNumericas.CERO, "UP!");
  }
  
}

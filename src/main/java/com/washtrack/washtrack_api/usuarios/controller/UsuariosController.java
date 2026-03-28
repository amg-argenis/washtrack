package com.washtrack.washtrack_api.usuarios.controller;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.LoginRequest;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class UsuariosController {
  
  private final IUsuarioService usuarioService;
  
  public UsuariosController(IUsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }
  
  /**
   * Listar ordenes de servicio | Controller
   *
   * @return
   */
  @GetMapping("/usuarios/login")
  public ResponseEntity<ServiceResult<Object>> listarOrdenesController(
      @Validated @RequestBody LoginRequest loginRequest) {
    
    log.info("[Iniciando login de usuario | Controller]");
    
    ServiceResult<Object> resultado = this.usuarioService.consultarUsuarioLogInService(loginRequest);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
}

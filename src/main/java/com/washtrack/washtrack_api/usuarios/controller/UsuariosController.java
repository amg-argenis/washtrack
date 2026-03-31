package com.washtrack.washtrack_api.usuarios.controller;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioActualizarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  
  @PostMapping("/usuarios/login")
  public ResponseEntity<ServiceResult<Object>> loginUsuariosController(
      @RequestBody LoginUsuarioRequest loginUsuarioRequest) {
    log.info("[Iniciando login de usuario | Controller]");
    log.info("[Controller | Request login completo: {}]", loginUsuarioRequest);
    log.info("[Controller | Email: {} | Password: {}]", loginUsuarioRequest.getEmail(),
        loginUsuarioRequest.getPassword());
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.consultarUsuarioLogInService(loginUsuarioRequest);
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al logearse con el usuario solicitado | Controller]");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
      if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        return ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      
      return ResponseEntity.ok(resultado);
    }
    catch ( Exception e ) {
      log.error("[Error critico al hacer login | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/usuarios/busquedas/listar-tenantid")
  public ResponseEntity<ServiceResult<Object>> listarUsuariosPorTenantIdController(
      HttpServletRequest request) {
    
    log.info("[Iniciando listar usuarios por tenant Id | Controller]");
    
    try {
      String tenantId = obtenerTenantId(request);
      ServiceResult<Object> resultado =
          this.usuarioService.listarUsuariosPorTenantIdService(tenantId);
      
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
      log.error("[Error critico al listar usuarios por tenantId | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PostMapping("/usuarios/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarUsuariosController(
      @Validated @RequestBody UsuarioInsertDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando insertar usuarios | Controller]");
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      ServiceResult<Object> resultado = this.usuarioService.insertarUsuarioService(usuario);
      
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
      log.error("[Error critico al insertar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PostMapping("/usuarios/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarUsuariosController(
      @Validated @RequestBody UsuarioEliminarReactivarDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando eliminar usuarios | Controller]");
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      ServiceResult<Object> resultado = this.usuarioService.eliminarUsuarioService(usuario);
      
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
      log.error("[Error critico al eliminar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PostMapping("/usuarios/reactivar")
  public ResponseEntity<ServiceResult<Object>> reactivarUsuariosController(
      @Validated @RequestBody UsuarioEliminarReactivarDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando reactivar usuarios | Controller]");
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      ServiceResult<Object> resultado = this.usuarioService.reactivarUsuarioService(usuario);
      
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
      log.error("[Error critico al reactivar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PostMapping("/usuarios/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarUsuariosController(
      @Validated @RequestBody UsuarioActualizarDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizar usuarios | Controller]");
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      ServiceResult<Object> resultado = this.usuarioService.actualizarUsuarioService(usuario);
      
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
      log.error("[Error critico al actualizar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  // Recuperar tenantId por token
  private String obtenerTenantId(HttpServletRequest request) {
    return (String) request.getAttribute("tenantId");
  }
  
}
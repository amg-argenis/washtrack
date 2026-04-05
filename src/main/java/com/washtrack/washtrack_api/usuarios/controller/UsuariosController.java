package com.washtrack.washtrack_api.usuarios.controller;

import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioActualizarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      resultado = this.usuarioService.consultarUsuarioLogInService(loginUsuarioRequest);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, el usuario no puede logearse al sistema",
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
      log.error("[Exception | Error critico al hacer login | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @GetMapping("/usuarios/busquedas/{email}")
  public ResponseEntity<ServiceResult<Object>> buscarUsuariosController(
      @PathVariable
      @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
          message = "Formato de email no valido")
      String email, HttpServletRequest request) {
    log.info("[Iniciando busqueda de usuario por email | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      resultado = this.usuarioService.buscarUsuarioPorEmailService(email, tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, usuario no encontrado con el email proporcionado",
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
      log.error("[Exception | Error critico al buscar usuario por el email | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @GetMapping("/usuarios/busquedas/listar-tenantid")
  public ResponseEntity<ServiceResult<Object>> listarUsuariosPorTenantIdController(
      HttpServletRequest request) {
    
    log.info("[Iniciando listar usuarios por tenant Id | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      resultado = this.usuarioService.listarUsuariosPorTenantIdService(tenantId);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo listar usuarios por tenant Id",
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
      log.error("[Exception | Error critico al listar usuarios por tenantId | Controller | Detalles: {}]",
          e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/usuarios/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarUsuariosController(
      @Valid @RequestBody UsuarioInsertDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando insertar usuarios | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      resultado = this.usuarioService.insertarUsuarioService(usuario);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo insertar el usuario",
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
      log.error("[Exception | Error critico al insertar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/usuarios/eliminar")
  public ResponseEntity<ServiceResult<Object>> eliminarUsuariosController(
      @Valid @RequestBody UsuarioEliminarReactivarDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando eliminar usuarios | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      resultado = this.usuarioService.eliminarUsuarioService(usuario);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo eliminar el usuario",
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
      log.error("[Exception | Error critico al eliminar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/usuarios/reactivar")
  public ResponseEntity<ServiceResult<Object>> reactivarUsuariosController(
      @Valid @RequestBody UsuarioEliminarReactivarDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando reactivar usuarios | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      resultado = this.usuarioService.reactivarUsuarioService(usuario);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo reactivar el usuario",
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
      log.error("[Exception | Error critico al reactivar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return response;
  }
  
  @PostMapping("/usuarios/actualizar")
  public ResponseEntity<ServiceResult<Object>> actualizarUsuariosController(
      @Valid @RequestBody UsuarioActualizarDto usuario,
      HttpServletRequest request) {
    
    log.info("[Iniciando actualizar usuarios | Controller]");
    
    ServiceResult<Object> resultado;
    ResponseEntity<ServiceResult<Object>> response;
    
    try {
      String tenantId = obtenerTenantId(request);
      usuario.setTenantId(tenantId);
      
      resultado = this.usuarioService.actualizarUsuarioService(usuario);
      
      if ( resultado == null ) {
        resultado = new ServiceResult<>(false,
            "Error interno, no se pudo actualizar el usuario",
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
      log.error("[Exception | Error critico al actualizar usuario | Controller | Detalles: {}]", e.getMessage(), e);
      resultado = new ServiceResult<>(false,
          "Error critico interno",
          ConstantesNumericas.CERO, null);
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultado);
    }
    
    return response;
  }
  
  // Recuperar tenantId por token
  private String obtenerTenantId(HttpServletRequest request) {
    return (String) request.getAttribute("tenantId");
  }
  
}
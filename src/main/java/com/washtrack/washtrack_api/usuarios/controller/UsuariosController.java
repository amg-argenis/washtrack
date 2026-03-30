package com.washtrack.washtrack_api.usuarios.controller;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.BuscarUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioActualizarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
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
  
  @GetMapping("/usuarios/login")
  public ResponseEntity<ServiceResult<Object>> loginUsuariosController(
      @Validated @RequestBody LoginUsuarioRequest loginUsuarioRequest) {
    
    log.info("[Iniciando login de usuario | Controller]");
    
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
      BuscarUsuarioRequest buscarUsuarioRequest) {
    
    log.info("[Iniciando listar usuarios por tenant Id | Controller]");
    
    try {
      ServiceResult<Object> resultado =
          this.usuarioService.listarUsuariosPorTenantIdService(buscarUsuarioRequest.getTenantId());
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al obtener el listado de usuarios | Controller]");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
      if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        return ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      
      return ResponseEntity.ok(resultado);
    }
    catch ( Exception e ) {
      log.error("[Error critico al listar usuarios | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/usuarios/listar")
  public ResponseEntity<ServiceResult<Object>> listarUsuariosController() {
    
    log.info("[Iniciando listar usuarios | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.listarUsuariosService();
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al obtener el listado de usuarios | Controller]");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
      if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        return ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      
      return ResponseEntity.ok(resultado);
    }
    catch ( Exception e ) {
      log.error("[Error critico al listar usuarios | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PostMapping("/usuarios/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarUsuariosController(
      @Validated @RequestBody UsuarioInsertDto usuario) {
    
    log.info("[Iniciando insertar usuarios | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.insertarUsuarioService(usuario);
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al insertar el usuario especificado | Controller]");
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
      @Validated @RequestBody UsuarioEliminarReactivarDto usuario) {
    
    log.info("[Iniciando eliminar usuarios | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.eliminarUsuarioService(usuario);
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al eliminar el usuario solicitado | Controller]");
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
      @Validated @RequestBody UsuarioEliminarReactivarDto usuario) {
    
    log.info("[Iniciando reactivar usuarios | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.reactivarUsuarioService(usuario);
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al reactivar el usuario solicitado | Controller]");
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
      @Validated @RequestBody UsuarioActualizarDto usuario) {
    
    log.info("[Iniciando actualizar usuarios | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.actualizarUsuarioService(usuario);
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al actualizar los datos del usuario | Controller]");
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
  
  @GetMapping("/users/private/serach/amg")
  public ResponseEntity<ServiceResult<Object>> busquedaUsuariosPrivateController(
      @Validated @RequestBody BuscarUsuarioRequest idUsuario) {
    
    log.info("[Iniciando busqueda privada de usuarios | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.buscarUsuarioPorIdService(idUsuario.getIdUsuario());
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al buscar usuarios private | Controller]");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
      if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        return ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      
      return ResponseEntity.ok(resultado);
    }
    catch ( Exception e ) {
      log.error("[Error critico en busqueda privada de usuario | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/usuarios/busquedas/email")
  public ResponseEntity<ServiceResult<Object>> busquedaUsuariosEmailController(
      @Validated @RequestBody BuscarUsuarioRequest usuario) {
    
    log.info("[Iniciando busqueda de usuarios por email | Controller]");
    
    try {
      ServiceResult<Object> resultado = this.usuarioService.buscarUsuarioPorEmailService(usuario);
      
      if ( resultado == null ) {
        log.info("[Hubo un problema al buscar usuarios por email | Controller]");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
      if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
        ApiErrorCode error = (ApiErrorCode) resultado.getData();
        return ResponseEntity.status(error.getHttpStatus()).body(resultado);
      }
      
      return ResponseEntity.ok(resultado);
    }
    catch ( Exception e ) {
      log.error("[Error critico al buscar usuarios por email | Controller | Detalles: {}]", e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}